package org.olaven.modulist.tutorial

class TutorialVideo(val title: String, val videoId: String) {

    companion object {

        fun getAll() = listOf(
            TutorialVideo("DEMO", "https://www.youtube.com/watch?v=YQHsXMglC9A")
        )
    }
}