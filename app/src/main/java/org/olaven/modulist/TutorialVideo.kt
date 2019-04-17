package org.olaven.modulist

class TutorialVideo(val title: String, val videoId: String) {

    companion object {

        fun getAll() = listOf(
            TutorialVideo("Adding list", "nTZ56f7I6h4"),
            TutorialVideo("Managing multiple lists", "VZuX8Bfeabk"),
            TutorialVideo("Adding items", "Fn-eoBG4j0c"),
            TutorialVideo("Editing a list", "szYmgL6grYE"),
            TutorialVideo("Adding location reminders", "RTNrtyvhJ2I"),
            TutorialVideo("Add calendar-event", "oJleCHBCMd8"),
            TutorialVideo("Sharing your list", "https://youtu.be/mUtPIyYzHSg"),
            TutorialVideo("Using Weather Planner", "mUtPIyYzHSg")
        )
    }
}