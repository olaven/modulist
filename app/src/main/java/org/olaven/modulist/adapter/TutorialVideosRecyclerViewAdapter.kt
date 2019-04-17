package org.olaven.modulist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.fragment_tutorialvideo.view.*
import org.olaven.modulist.R
import org.olaven.modulist.TutorialVideo


class TutorialVideosRecyclerViewAdapter(private val tutorialVideos: List<TutorialVideo>) : androidx.recyclerview.widget.RecyclerView.Adapter<TutorialVideosRecyclerViewAdapter.ViewHolder>() {

    val playerViews = mutableListOf<YouTubePlayerView>()
    fun releasePlayerViews() {

        playerViews.forEach {
            it.release()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_tutorialvideo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val tutorialVideo = tutorialVideos[position]

        holder.title.text = tutorialVideo.title
        holder.player.apply {
            addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {

                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val videoId = tutorialVideo.videoId
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            })
            playerViews.add(this)
        }
    }

    override fun getItemCount(): Int = tutorialVideos.size

    inner class ViewHolder(holder: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(holder) {
        val title: TextView = holder.fragment_tutorial_video_title
        val player: YouTubePlayerView = holder.fragment_tutorial_video_player
    }
}
