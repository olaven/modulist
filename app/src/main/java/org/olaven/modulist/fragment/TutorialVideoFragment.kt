package org.olaven.modulist.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.olaven.modulist.R
import org.olaven.modulist.adapter.TutorialVideosRecyclerViewAdapter

import org.olaven.modulist.tutorial.TutorialContent
import org.olaven.modulist.tutorial.TutorialContent.DummyItem


class TutorialVideoFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tutorialvideo_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager =LinearLayoutManager(context)
                adapter = TutorialVideosRecyclerViewAdapter(TutorialContent.tutorialVideos)
            }
        }
        return view
    }

}
