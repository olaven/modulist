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

        val recyclerViewFragment = inflater.inflate(R.layout.fragment_my_lists, container, false)

        // NOTE: kotlin view binding does not appear to work with recyclerviews
        val fragment_my_lists_recycler_view = recyclerViewFragment.findViewById<RecyclerView>(R.id.fragment_my_lists_recycler_view)
        setupRecycleViews(fragment_my_lists_recycler_view)

        return recyclerViewFragment
    }

    private fun setupRecycleViews(recyclerView: RecyclerView) {

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
}
