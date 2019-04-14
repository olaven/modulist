package org.olaven.modulist.activity

import android.Manifest
import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import kotlinx.android.synthetic.main.activity_module_list.*
import org.olaven.modulist.CameraTools
import org.olaven.modulist.R
import org.olaven.modulist.adapter.ItemsRecyclerAdapter
import org.olaven.modulist.database.Models
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ModuleList
import org.olaven.modulist.dialog.add.AddItemDialog
import android.provider.CalendarContract
import android.provider.Settings
import android.support.design.widget.Snackbar
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.LocationBias
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import im.delight.android.location.SimpleLocation
import org.olaven.modulist.App
import org.olaven.modulist.dialog.update.DeleteModuleListDialog
import org.olaven.modulist.dialog.update.UpdateColorDialog
import org.olaven.modulist.dialog.update.UpdateNameDialog
import org.olaven.modulist.dialog.update.UpdateParentsDialog
import org.olaven.modulist.service.GeofenceService
import java.security.Permission


class ModuleListActivity : BaseActivity() {

    private lateinit var cameraTools: CameraTools
    private var items = mutableListOf<Item>()
    private lateinit var moduleList: ModuleList
    private lateinit var adapter: ItemsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module_list)

        cameraTools = CameraTools(this)
        changeProgressText(1)
        setupModuleList()
        setupSeekbar()
        setupAddItemFab()
    }


    private fun setupModuleList() {

        adapter = ItemsRecyclerAdapter(application, this, ModuleList(getString(R.string.unloaded), Color.GRAY), activity_module_list_seekbar_days.progress)

        val key = getString(R.string.extra_modulelist_key)
        val id = intent.extras[key] as Long

        val moduleListModel = Models.getModuleListModel(application)
        val itemModel = Models.getItemModel(application)

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


        activity_module_list_recycler_view.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
        itemModel.getByModuleListIdLive(id).observe(this, Observer { items ->

            adapter.clear()
            activity_module_list_recycler_view.adapter = adapter
            items?.let { items ->

                this.items = items.toMutableList()
                items.forEach {
                    adapter.add(it)
                }
            }
            adapter.notifyDataSetChanged()


        })
    }

    private fun setupSeekbar() {

        activity_module_list_seekbar_days.max = 35
        activity_module_list_seekbar_days.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                changeProgressText(progress)
                adapter.days = progress
                adapter.notifyDataSetChanged()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
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

    fun triggerLocationReminder() {

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {

            Places.initialize(applicationContext, App.API_PLACES_KEY)

            val location = SimpleLocation(this)
            val fields = listOf(Place.Field.NAME, Place.Field.LAT_LNG)
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .setLocationBias(RectangularBounds.newInstance(
                    LatLng(location.latitude, location.longitude),
                    LatLng(location.latitude + 0.1, location.longitude + 0.1)
                ))
                .setTypeFilter(TypeFilter.ADDRESS)
                .build(this)
            startActivityForResult(intent, App.REQUEST_CODE_PLACES)
        } else {

            Snackbar.make(activity_module_list, "Permit location access to use this feature.", Snackbar.LENGTH_INDEFINITE).apply {

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

        when (requestCode) {
            App.REQUEST_CODE_PLACES -> {

                if (resultCode == RESULT_OK) {

                    val place = Autocomplete.getPlaceFromIntent(data!!)
                    val intent = Intent(this, GeofenceService::class.java)

                    intent.putExtra(getString(R.string.add_fence_lat_key), place.latLng?.latitude)
                    intent.putExtra(getString(R.string.add_fence_long_key), place.latLng?.longitude)

                    Snackbar
                        .make(activity_module_list, "Added reminder at: ${place.name}", Snackbar.LENGTH_LONG)
                        .show()

                    startService(intent)
                } else {

                    Snackbar
                        .make(activity_module_list, "Some error occured", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
            App.REQUEST_CODE_CAMERA -> {

                Toast.makeText(this, "NOT IMPLEMENTED", Toast.LENGTH_LONG).show()
                data?.let {
                    val bitmap = cameraTools.getBitMap(it)
                    print(bitmap)
                    //TODO: Do something with bitmap, like storing in db
                }
            }
        }
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
            "Packing for $dayCount dayCount"
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


