package org.olaven.modulist.activities

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_module_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.olaven.modulist.R
import org.olaven.modulist.adapters.ModuleListRecyclerAdapter
import org.olaven.modulist.database.Models

class ModuleListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module_list)

        val id: Long = (intent.extras["id"] as Int).toLong()
        setupModuleList(id)
    }

    private fun setupModuleList(id: Long) {

        val moduleListModel = Models.getModuleListModel(application)
        val itemModel = Models.getItemModel(application)

        val lifecycleOwner = this // "this" is th scope
        GlobalScope.launch(Dispatchers.IO) {

            val moduleList = moduleListModel.getById(id)
            var adapter = activity_module_list_recycler_view.adapter

            activity_module_list_name.text = moduleList.name
            adapter = ModuleListRecyclerAdapter(applicationContext)

            val items = itemModel.getByModuleListId(id)
            items.observe(lifecycleOwner, Observer { packageTypes ->
                adapter.clear()
                packageTypes?.forEach { item ->
                    adapter.addItem(item)
                }
            })
        }

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }


}

