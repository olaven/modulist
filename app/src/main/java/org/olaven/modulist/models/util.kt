package org.olaven.modulist.models

import android.graphics.Color
import kotlin.random.Random

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
