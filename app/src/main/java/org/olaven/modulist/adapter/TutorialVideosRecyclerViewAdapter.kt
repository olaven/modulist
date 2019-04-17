package org.olaven.modulist.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import org.olaven.modulist.R

import kotlinx.android.synthetic.main.fragment_tutorialvideo.view.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.olaven.modulist.tutorial.TutorialVideo


class TutorialVideosRecyclerViewAdapter(private val tutorialVideos: List<TutorialVideo>) : androidx.recyclerview.widget.RecyclerView.Adapter<TutorialVideosRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_tutorialvideo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val tutorialVideo = tutorialVideos[position]

        holder.title.text = tutorialVideo.title
        holder.player.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {

            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = tutorialVideo.videoId
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }

    override fun getItemCount(): Int = tutorialVideos.size

    inner class ViewHolder(holder: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(holder) {
        val title: TextView = holder.fragment_tutorial_video_title
        val player: YouTubePlayerView = holder.fragment_tutorial_video_player
    }
}
