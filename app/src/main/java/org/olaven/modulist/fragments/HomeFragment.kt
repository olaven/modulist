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
import org.olaven.modulist.R
import org.olaven.modulist.adapters.HomeFragmentRecyclerAdapter
import org.olaven.modulist.database.Models

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val homeFragment = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = homeFragment.findViewById<RecyclerView>(R.id.fragment_home_recycler_view)

        setupRecycleViews(recyclerView)

        return homeFragment
    }

    //TODO: Lagre state
    // TODO: sjekk om riktig metode fra slide/notater
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    //TODO: Lagre state
    // TODO: sjekk om riktig metode fra slide/notater
    override fun onResume() {
        super.onResume()
    }

    private fun setupRecycleViews(recyclerView: RecyclerView) {

        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false)

        val moduleListModel = Models.getModuleListModel(activity!!.application)
        moduleListModel?.allModuleListsLive?.observe(this, Observer { liveData ->
            // if data != null and context != null
            liveData?.let { data ->
                context?.let { context ->
                    recyclerView.adapter = HomeFragmentRecyclerAdapter(context, data)
                }
            }
        })
    }



}
