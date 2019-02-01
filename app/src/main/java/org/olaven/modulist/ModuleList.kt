package org.olaven.modulist

import android.graphics.Color
import kotlin.random.Random

data class Item(val name: String, var done: Boolean, val attachments: List<Any> = emptyList())

class ModuleList (val name: String, val items: List<Item> = emptyList(), val superLists: List<ModuleList> = emptyList(), val color: Int = pickRandomColor())


fun pickRandomColor(): Int {
    val colors = arrayOf(
        Color.BLACK,
        Color.CYAN,
        Color.RED,
        Color.MAGENTA,
        Color.WHITE
    )

    val index = Random.nextInt(colors.count())

    return colors[index]
}
