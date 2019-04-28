package org.olaven.modulist.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_my_lists.*
import org.olaven.modulist.R
import org.olaven.modulist.adapter.ModulelistsRecyclerAdapter
import org.olaven.modulist.database.Models
import org.olaven.modulist.database.entity.ModuleList
import org.olaven.modulist.dialog.add.AddModuleListDialog
import org.olaven.modulist.setVisibilityOf

class MyListsFragment : androidx.fragment.app.Fragment() {

    var moduleLists: List<ModuleList>? = null
    private lateinit var container: ViewGroup

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // NOTE: kotlin view binding does not appear to work with recyclerviews
        val view = inflater.inflate(R.layout.fragment_my_lists, container, false)
        this.container = container!!

        setupModuleListListener()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupAddModuleListFab()
    }

    private fun updateRecyclerView(view: View, inflater: LayoutInflater, container: ViewGroup?) {

        val recyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.fragment_my_lists_recycler_view)
        recyclerView.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        context?.let {

            recyclerView.adapter = ModulelistsRecyclerAdapter(it, moduleLists!!)
        }
    }

    private fun setupAddModuleListFab() {

        fragment_my_lists_fab_add_module_list.setOnClickListener {

            val alertContext = (activity as AppCompatActivity)
            moduleLists?.let {
                AddModuleListDialog(it, alertContext)
                    .show()
            }
        }
    }

    private fun setupModuleListListener() {

        val liveModuleLists = Models
            .getModuleListModel(activity!!.application)
            .getAllModuleListsLive()

        liveModuleLists.observe(this, Observer {
            it?.let { moduleLists ->

                if (moduleLists.isEmpty()) {

                    setVisibilityOf(fragment_my_lists_text_add_lists, true)
                    this.moduleLists = emptyList()
                } else {

                    setVisibilityOf(fragment_my_lists_text_add_lists, false)
                    this.moduleLists = moduleLists
                    updateRecyclerView(view!!, layoutInflater, container)
                }
            }
        })
    }

}
