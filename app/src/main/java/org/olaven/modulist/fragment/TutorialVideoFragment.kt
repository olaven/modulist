package org.olaven.modulist.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.olaven.modulist.R
import org.olaven.modulist.adapter.TutorialVideosRecyclerViewAdapter
import org.olaven.modulist.TutorialVideo


class TutorialVideoFragment : androidx.fragment.app.Fragment() {


    private val adapter = TutorialVideosRecyclerViewAdapter(TutorialVideo.getAll())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_tutorialvideo_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
                adapter = TutorialVideosRecyclerViewAdapter(TutorialVideo.getAll())
            }
        }
        return view
    }

    override fun onPause() {

        super.onPause()
        adapter.releasePlayerViews() //important for performance
    }

}
