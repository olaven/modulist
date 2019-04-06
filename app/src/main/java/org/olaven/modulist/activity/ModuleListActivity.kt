package org.olaven.modulist.activity

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.graphics.drawable.ColorDrawable
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
import org.olaven.modulist.dialog.update.DeleteModuleListDialog
import org.olaven.modulist.dialog.update.UpdateColorDialog
import org.olaven.modulist.dialog.update.UpdateNameDialog
import org.olaven.modulist.dialog.update.UpdateParentsDialog


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

        val key = getString(R.string.extra_modulelist_key)
        val id = intent.extras[key] as Long

        val moduleListModel = Models.getModuleListModel(application)
        val itemModel = Models.getItemModel(application)
        adapter = ItemsRecyclerAdapter(application, activity_module_list_seekbar_days.progress)

        moduleListModel.getByIdLive(id).observe(this, Observer {

            it?.let {moduleList ->

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
            items?.let { items ->

                this.items = items.toMutableList()
                items.forEach {
                    adapter.add(it)
                }
            }
            adapter.notifyDataSetChanged()

        })
        adapter.notifyDataSetChanged()
        activity_module_list_recycler_view.adapter = adapter
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


    /**
     * FOR DOING IMAGE STUFF -> not done
     */
    fun TESTtakePicture() {
        if (cameraTools.isPresent()) {
            cameraTools.takePicture()
        }
    }
    /**
     * FOR DOING IMAGE STUFF -> not done
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == cameraTools.CAMERA_REQUEST_CODE and Activity.RESULT_OK) {

            data?.let {
                val bitmap = cameraTools.getBitMap(it)
                print(bitmap)
                //TODO: Do something with bitmap, like storing in db
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_modulist, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when(item?.itemId) {
            R.id.menu_modulist_share -> {
                triggerSharing()
                return true
            }
            R.id.menu_modulist_add_to_calendar -> {
                triggerCalendar()
                return true
            }
            R.id.menu_modulist_update_color -> {
                UpdateColorDialog(moduleList, this).show()
                return true
            }
            R.id.menu_modulist_update_name-> {
                UpdateNameDialog(moduleList, this).show()
                return true
            }
            R.id.menu_modulist_update_parents -> {
                UpdateParentsDialog(emptyList(), moduleList, this).show()
                return true
            }
            R.id.menu_modulist_delete -> {
                DeleteModuleListDialog(moduleList, this).show()
                return true
            }
            else -> {
                Toast.makeText(applicationContext, "NOT IMPLEMENTED", Toast.LENGTH_SHORT)
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun changeProgressText(dayCount: Int) {

        activity_module_list_text_days.text =
                "Packing for $dayCount dayCount"
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

        val content = toString()
        intent.putExtra(Intent.EXTRA_TEXT, content)
        // TODO: Implement sharing of images intent.extras[Intent.EXTRA_STREAM] = File() //ATAHCMENTS
        startActivity(Intent.createChooser(intent, "Share list using.."))
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

