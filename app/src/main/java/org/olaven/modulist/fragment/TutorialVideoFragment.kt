package org.olaven.modulist.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.olaven.modulist.R
import org.olaven.modulist.adapter.TutorialVideosRecyclerViewAdapter
import org.olaven.modulist.tutorial.TutorialContent


class TutorialVideoFragment : androidx.fragment.app.Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tutorialvideo_list, container, false)

        // Set the adapter
        if (view is androidx.recyclerview.widget.RecyclerView) {
            with(view) {
                layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
                adapter = TutorialVideosRecyclerViewAdapter(TutorialContent.tutorialVideos)
            }
        }
        return view
    }

}
