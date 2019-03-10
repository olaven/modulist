package org.olaven.modulist.activities

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_module_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.olaven.modulist.R
import org.olaven.modulist.adapters.ItemsRecyclerAdapter
import org.olaven.modulist.database.Models
import org.olaven.modulist.database.entity.Item
import java.lang.StringBuilder

class ModuleListActivity : BaseActivity() {

    private var items = mutableListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module_list)


        val key = getString(R.string.extra_id_key)
        val id = intent.extras[key] as Int
        setupModuleList(id)
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

    private fun setupModuleList(id: Int) {

        val moduleListModel = Models.getModuleListModel(application)
        val itemModel = Models.getItemModel(application)

        val lifecycleOwner = this // "this" is the scope below
        GlobalScope.launch(Dispatchers.IO) {

            val moduleList = moduleListModel.getById(id)

            activity_module_list_name.text = moduleList.name
            val liveItems = itemModel.getByModuleListId(id)

            liveItems.observe(lifecycleOwner, Observer { liveData ->

                liveData?.let { data ->
                    items = data.toMutableList()
                    activity_module_list_recycler_view.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
                    activity_module_list_recycler_view.adapter =
                            ItemsRecyclerAdapter(applicationContext, data)
                }
            })
        }
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

