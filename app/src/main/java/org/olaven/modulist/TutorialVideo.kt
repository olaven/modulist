package org.olaven.modulist

class TutorialVideo(val title: String, val videoId: String) {

    companion object {

        fun getAll() = listOf(
            TutorialVideo("DEMO", "YQHsXMglC9A"),
            TutorialVideo("Kurzy", "p_8yK2kmxoo"),
            TutorialVideo("Hellohello", "EkRRo5DN9lI")
        )
    }
}