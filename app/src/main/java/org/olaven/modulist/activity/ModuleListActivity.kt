package org.olaven.modulist.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_module_list.*
import org.olaven.modulist.App
import org.olaven.modulist.PlacesInput
import org.olaven.modulist.R
import org.olaven.modulist.adapter.ItemsRecyclerAdapter
import org.olaven.modulist.database.ModelFactory
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ModuleList
import org.olaven.modulist.dialog.add.AddItemDialog
import org.olaven.modulist.dialog.update.DeleteModuleListDialog
import org.olaven.modulist.dialog.update.UpdateColorDialog
import org.olaven.modulist.dialog.update.UpdateNameDialog
import org.olaven.modulist.dialog.update.UpdateParentsDialog
import org.olaven.modulist.geofence.CustomGeofence
import org.olaven.modulist.sensor.SensorConfig
import org.olaven.modulist.setVisibilityOf


class ModuleListActivity : BaseActivity() {

    private var items = mutableListOf<Item>()
    private lateinit var moduleList: ModuleList
    private lateinit var adapter: ItemsRecyclerAdapter
    private lateinit var placesInput: PlacesInput
    private lateinit var sensorConfig: SensorConfig
    private var atticMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module_list)

        placesInput = PlacesInput(
            this,
            listOf(Place.Field.NAME, Place.Field.LAT_LNG),
            TypeFilter.ADDRESS,
            App.REQUEST_CODE_PLACES_ADRESS
        )
        sensorConfig = SensorConfig(this, activity_module_list)
        updateAtticModeText()
        changeProgressText(1)
        setupModuleList()
        setupSeekbar()
        setupAddItemFab()
    }

    private fun updateAtticModeText() {

        activity_module_list_text_attic_mode.text =
                if (atticMode)
                    "Atticmode is enabled"
                else
                    ""
    }


    private fun setupModuleList() {

        adapter = ItemsRecyclerAdapter(application, this, ModuleList(getString(R.string.unloaded), Color.GRAY), activity_module_list_seekbar_days.progress)

        val key = getString(R.string.extra_modulelist_key)
        val id = intent.extras[key] as Long

        val moduleListModel = ModelFactory.getModuleListModel(application)
        val itemModel = ModelFactory.getItemModel(application)

        moduleListModel.getByIdLive(id).observe(this, Observer {

            it?.let {moduleList ->

                adapter.moduleList = moduleList
                this.moduleList = moduleList

                supportActionBar?.apply {
                    title = moduleList.name
                    setBackgroundDrawable(ColorDrawable(moduleList.color))
                }
            }
        })


        activity_module_list_recycler_view.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        itemModel.getByModuleListIdLive(id).observe(this, Observer {

            adapter.clear()
            activity_module_list_recycler_view.adapter = adapter
            it?.let { items ->

                if (items.isEmpty()) {
                    setVisibilityOf(activity_module_list_text_add_items, true)
                } else {
                    setVisibilityOf(activity_module_list_text_add_items, false)
                    this.items = items.toMutableList()
                    items.forEach {
                        adapter.add(it)
                    }
                }
            }
            adapter.notifyDataSetChanged()


        })
    }

    private fun setupSeekbar() {

        activity_module_list_seekbar_days.apply {

            setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{

                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                    changeProgressText(progress)
                    adapter.days = progress
                    adapter.notifyDataSetChanged()
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
            max = 55
            progress = 7
        }
    }



    private fun setupAddItemFab() {

        activity_module_list_fab_add_item.setOnClickListener {

            AddItemDialog(moduleList, this)
                .show()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_modulist, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when(item?.itemId) {
            R.id.menu_modulist_toggle_attic_mode -> {
                toggleAtticMode()
                return true
            }
            R.id.menu_modulist_add_location_reminder -> {
                triggerLocationReminder()
                return true
            }
            R.id.menu_modulist_share -> {
                triggerSharing()
                return true
            }
            R.id.menu_modulist_add_to_calendar -> {
                triggerCalendar()
                return true
            }
            R.id.menu_modulist_update_name-> {
                UpdateNameDialog(moduleList, this).show()
                return true
            }
            R.id.menu_modulist_update_parents -> {

                UpdateParentsDialog(moduleList, this).show()
                return true
            }
            R.id.menu_modulist_update_color -> {
                UpdateColorDialog(moduleList, this).show()
                return true
            }
            R.id.menu_modulist_delete -> {
                DeleteModuleListDialog(moduleList, this).show()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun triggerLocationReminder() {

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {

            val intent = placesInput.getLaunchIntent()
            startActivityForResult(intent, App.REQUEST_CODE_PLACES_ADRESS)
        } else {

            Snackbar.make(activity_module_list, "Allow location permissions.", Snackbar.LENGTH_INDEFINITE).apply {

                setAction("Ok") {

                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"))
                        .apply {
                            addCategory(Intent.CATEGORY_DEFAULT)
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }.also {
                            startActivity(it)
                        }
                }
            }.show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        placesInput.getPlace(requestCode, resultCode, data, activity_module_list)?.let { place ->

            val customGeofence = CustomGeofence(this)
            customGeofence.addFence(moduleList, place.name!!, place.latLng!!.latitude, place.latLng!!.longitude)
            Snackbar
                .make(activity_module_list, "Added reminder at: ${place.name}", Snackbar.LENGTH_LONG)
                .show()

        }
    }

    private fun toggleAtticMode() {

        atticMode = !atticMode
        updateAtticModeText()

        if (atticMode)
            sensorConfig.startAll()
        else
            sensorConfig.stopAll()
    }


    private fun triggerCalendar() {

        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = "vnd.android.cursor.item/event"
        intent.putExtra(CalendarContract.Events.TITLE, "Pack for ${moduleList.name}")
        startActivity(intent)
    }

    private fun triggerSharing() {

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"

        val listAsString = toString()
        intent.putExtra(Intent.EXTRA_TEXT, listAsString)
        // TODO: Implement sharing of images intent.extras[Intent.EXTRA_STREAM] = File() //ATAHCMENTS
        startActivity(Intent.createChooser(intent, "Share list using.."))
    }

    private fun changeProgressText(dayCount: Int) {

        activity_module_list_text_days.text =
            "Packing for $dayCount days"
    }


    override fun toString(): String {

        val builder = StringBuilder()
        builder.append("${moduleList.name}: \n")
        items.forEach {

            val done = if (it.done)
                "[X]"
            else
                "[ ]"

            builder.append("\n$done ${it.name} \n")
        }
        return builder.toString()
    }
}


