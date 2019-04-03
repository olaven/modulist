package org.olaven.modulist.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_my_lists.*
import org.olaven.modulist.R
import org.olaven.modulist.adapters.ListsFragmentRecyclerAdapter
import org.olaven.modulist.database.Models
import org.olaven.modulist.database.entity.ModuleList
import org.olaven.modulist.dialog.AddModuleListDialog

class MyListsFragment : Fragment() {

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

        val recyclerView = view.findViewById<RecyclerView>(R.id.fragment_my_lists_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        context?.let {

            recyclerView.adapter = ListsFragmentRecyclerAdapter(it, moduleLists!!)
        }
    }

    private fun setupAddModuleListFab() {

        fragment_my_lists_fab_add_module_list.setOnClickListener {

            val alertContext = activity!!
            moduleLists?.let {
                AddModuleListDialog(it, alertContext)
                    .show()
            }
        }
    }

    private fun setupModuleListListener() {

        val liveModuleLists = Models
            .getModuleListModel(activity!!.application)
            .allModuleListsLive

        liveModuleLists.observe(this, Observer {
            it?.let { moduleLists ->

                this.moduleLists = moduleLists
                updateRecyclerView(view!!, layoutInflater, container)
            }
        })
    }

}
