package org.olaven.modulist

import org.olaven.modulist.models.Item
import org.olaven.modulist.models.ModuleList
import kotlin.random.Random

fun getModuleLists(count: Int): List<ModuleList>{

    val list = mutableListOf<ModuleList>()
    for (i in 0 until count) {
        list.add(getModuleList())
    }

    return list
}

fun getModuleList(): ModuleList {
    val names = listOf("Pakkeliste", "Sommerferie", "Vinterferie", "Lekse", "Jobb")
    val name = getRandomFrom(names)

    val items = listOf(
        Item("$name - one undone item", false),
        Item("$name - another undone item", false),
        Item("$name - one done item", true),
        Item("$name - another done item", true),
        Item("$name - should do this..", false),
        Item("$name - glad I did this", true)
    )

    return ModuleList(name, items)
}

fun<T> getRandomFrom(list: List<T>): T {
    val index = Random.nextInt(list.count())
    return list[index]
}
