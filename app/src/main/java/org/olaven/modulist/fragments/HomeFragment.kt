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
import org.olaven.modulist.adapters.RecyclerAdapter
import org.olaven.modulist.database.AppDatabase
import org.olaven.modulist.database.Models
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ModuleList
import org.olaven.modulist.database.entity.pickRandomColor
import org.olaven.modulist.database.model.ModuleListModel
import kotlin.concurrent.thread

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val homeFragment = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = homeFragment.findViewById<RecyclerView>(R.id.fragment_home_recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false)

        populateRecycleViews(recyclerView)

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

    private fun populateRecycleViews(recyclerView: RecyclerView) {
        val moduleListModel = Models.getModuleListModel(activity!!.application) ;
        moduleListModel?.allModuleListsLive?.observe(this, Observer { liveData ->
            // if data != null and context != null
            liveData?.let { data ->
                context?.let { context ->
                    recyclerView.adapter = RecyclerAdapter(context, data)
                }
            }
        })
    }



}
