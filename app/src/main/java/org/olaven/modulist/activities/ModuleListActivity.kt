package org.olaven.modulist.activities

import android.arch.lifecycle.Observer
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_module_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.olaven.modulist.R
import org.olaven.modulist.adapters.ItemsRecyclerAdapter
import org.olaven.modulist.database.Models

class ModuleListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module_list)


        val key = getString(R.string.extra_id_key)
        val id = intent.extras[key] as Int
        setupModuleList(id)
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
                    activity_module_list_recycler_view.adapter =
                            ItemsRecyclerAdapter(applicationContext, data)
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

