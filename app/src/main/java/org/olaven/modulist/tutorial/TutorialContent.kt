package org.olaven.modulist.tutorial

import java.util.ArrayList

object TutorialContent {

    val tutorialVideos: MutableList<TutorialVideo> = ArrayList()

    private const val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(
                createDummyItem(
                    i
                )
            )
        }
    }

    private fun addItem(tutorialVideo: TutorialVideo) {
        tutorialVideos.add(tutorialVideo)
    }

    private fun createDummyItem(position: Int): TutorialVideo {
        return TutorialVideo(
            "SOme demo video asdf",
            "2CeurT8vjU0"
        )
    }
}
