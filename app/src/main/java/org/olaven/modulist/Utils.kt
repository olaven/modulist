package org.olaven.modulist

import android.view.View
import java.lang.StringBuilder
import kotlin.random.Random

fun randomString(length: Int): String {

    val alphabet = "abcdefghijklmnopqrstuvwxyz"
    val builder = StringBuilder();

    for (i in 0 until length) {

        val index = Random.nextInt(alphabet.count())
        builder.append(alphabet[index])
    }

    return builder.toString()
}

fun setVisibilityOf(view: View, visible: Boolean) {

    view.visibility = if (visible)
            View.VISIBLE
        else
            View.INVISIBLE
}