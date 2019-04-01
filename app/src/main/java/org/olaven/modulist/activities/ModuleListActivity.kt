package org.olaven.modulist.activities

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import kotlinx.android.synthetic.main.activity_module_list.*
import org.olaven.modulist.CameraTools
import org.olaven.modulist.R
import org.olaven.modulist.adapters.ItemsRecyclerAdapter
import org.olaven.modulist.createCustomDialog
import org.olaven.modulist.database.Models
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ModuleList
import java.lang.Exception

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
        adapter = ItemsRecyclerAdapter(applicationContext, activity_module_list_seekbar_days.progress)

        moduleListModel.getById(id).observe(this, Observer {

            it?.let {

                moduleList = it
                supportActionBar!!.title = it.name
            }
        })


        activity_module_list_recycler_view.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
        itemModel.getByModuleListId(id).observe(this, Observer { packageTypes ->

            packageTypes?.forEach {item ->

                adapter.add(item)
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

            var name = getString(R.string.unloaded)
            var dayDistribution = Integer.MAX_VALUE

            val alertContext = this
            createCustomDialog(alertContext, "Add an item called...") {

                val view = EditText(applicationContext)
                it.setView(view)
                it.setPositiveButton("continue") { _, _ ->

                    name = view.text.toString()

                    createCustomDialog(alertContext, "Pack one for every ... days") {

                        val dayOptions = resources.getStringArray(R.array.day_options)
                        it.setSingleChoiceItems(dayOptions,  -1) { _, item ->

                            try {
                                dayDistribution = dayOptions[item].toInt()
                            } catch (e: Exception) {
                                //Intentionally blank.
                                //Distribution stays the same.
                            }
                        }
                        it.setPositiveButton("continue") {_, _ ->

                            val distributionMessage =
                                if (dayDistribution == Integer.MAX_VALUE)
                                    "Pack just the one."
                                else
                                    dayDistribution.toString()

                            val view = TextView(applicationContext).apply {
                                text = "Name: $name \n Distribution: $distributionMessage"
                            }

                            createCustomDialog(alertContext, "Does this look okay?") {

                                it.setView(view)
                                it.setPositiveButton("Looks good") {_, _ ->

                                    val item = Item(name, false, dayDistribution, moduleList.id!!)
                                    Models
                                        .getItemModel(application)
                                        .insert(item)
                                }
                                it.setNegativeButton("Not what I intended") {_, _ ->}
                            }
                        }
                    }
                }
            }
        }
    }


    fun TESTtakePicture() {
        if (cameraTools.isPresent()) {
            cameraTools.takePicture()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == cameraTools.CAMERA_REQUEST_CODE and Activity.RESULT_OK) {

            data?.let {
                val bitmap = cameraTools.getBitMap(it)
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

    private fun triggerSharing() {

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, toStringRepresentation())
        // TODO: Implement sharing of images intent.extras[Intent.EXTRA_STREAM] = File() //ATAHCMENTS
        startActivity(Intent.createChooser(intent, "Share list using.."))
    }



    private fun toStringRepresentation(): String? {

        val builder = StringBuilder()
        items.forEach {

            val done = if (it.done)
                "[X]"
            else
                "[ ]"

            builder.append("$done ${it.name} \n")
        }
        return builder.toString()
    }


}


