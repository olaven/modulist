package org.olaven.modulist

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