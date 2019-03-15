package org.olaven.modulist.activities

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_module_list.*
import org.olaven.modulist.R
import org.olaven.modulist.adapters.ItemsRecyclerAdapter
import org.olaven.modulist.database.Models
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ModuleList

class ModuleListActivity : BaseActivity() {

    private var items = mutableListOf<Item>()
    private lateinit var moduleList: ModuleList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module_list)

        setupModuleList()
        setupAddItemFab()
    }

    private fun setupAddItemFab() {

        activity_module_list_fab_add_item.setOnClickListener {

            val alert = AlertDialog.Builder(this);
            alert.apply {
                setTitle("Adding item")
            }
            alert.show()
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

    private fun triggerSharing() {

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, toStringRepresentation())
        // TODO: Implement sharing of images intent.extras[Intent.EXTRA_STREAM] = File() //ATAHCMENTS
        startActivity(Intent.createChooser(intent, "Share list using.."))
    }

    private fun setupModuleList() {

        val key = getString(R.string.extra_modulelist_key)
        val id = intent.extras[key] as Long

        val moduleListModel = Models.getModuleListModel(application)
        val itemModel = Models.getItemModel(application)

        moduleListModel.getById(id).observe(this, Observer {

            it?.let {

                moduleList = it
                supportActionBar!!.title = it.name
            }
        })


        //TODO: REplace with demo
        itemModel.insert(Item("Ths is added from activity1 to $id", false, 2, id))
        itemModel.insert(Item("Ths is added from activity2 to $id", false, 2, id))
        itemModel.insert(Item("Ths is added from activity3 to $id", false, 2, id))
        itemModel.insert(Item("Ths is added from activity4 to $id", false, 2, id))

        activity_module_list_recycler_view.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
        val adapter = ItemsRecyclerAdapter(applicationContext)
        itemModel.getByModuleListId(id).observe(this, Observer { packageTypes ->

            packageTypes?.forEach {item ->

                adapter.add(item)
            }

        })
        adapter.notifyDataSetChanged()
        activity_module_list_recycler_view.adapter = adapter
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


/* //NOTE: Fungerer!
         itemModel.allItemsLive.observe(lifecycleOwner, Observer {items ->

             items?.let {

                 activity_module_list_recycler_view.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
                 activity_module_list_recycler_view.adapter = ItemsRecyclerAdapter(applicationContext, items)
             }
         }) */


