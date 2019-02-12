package org.olaven.modulist.fragments

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
import org.olaven.modulist.getModuleLists

class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val homeFragment = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = homeFragment.findViewById<RecyclerView>(R.id.fragment_home_recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false)
        recyclerView.adapter = // necessary as context is optional
            context?.let { RecyclerAdapter(it, getModuleLists(10)) } //TODO: using mock items, replace with "real" data!

        return homeFragment
    }


    // TODO: lagre initialisering her? Går det?
    override fun onPause() {
        super.onPause()
    }
    // TODO: vise initialisering her?
    override fun onResume() {
        super.onResume()
    }
}
