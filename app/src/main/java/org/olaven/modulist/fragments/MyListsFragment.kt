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

class MyListsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // NOTE: kotlin view binding does not appear to work with recyclerviews
        val view = inflater.inflate(R.layout.fragment_my_lists, container, false)

        setupRecycleViews(view, inflater, container)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupAddModuleListFab()
    }

    private fun setupRecycleViews(view: View, inflater: LayoutInflater, container: ViewGroup?) {

        val recyclerView = view.findViewById<RecyclerView>(R.id.fragment_my_lists_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        val moduleListModel = Models.getModuleListModel(activity!!.application)
        moduleListModel?.allModuleListsLive?.observe(this, Observer { liveData ->

            liveData?.let { data ->
                context?.let { context ->
                    recyclerView.adapter = ListsFragmentRecyclerAdapter(context, data)
                }
            }
        })
    }

    private fun setupAddModuleListFab() {

        fragment_my_lists_fab_add_module_list.setOnClickListener {

            val alertContext = activity!!
            //TODO: create dialog class
        }
    }
}
