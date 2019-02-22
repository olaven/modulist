package org.olaven.modulist.models

import kotlin.random.Random

class ModuleList (
    val name: String,
    val items: List<Item> = emptyList(),
    val superLists: List<ModuleList> = emptyList(),
    val color: Int = pickRandomColor(),
    val id: Int = Random.nextInt()
)


