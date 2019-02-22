package org.olaven.modulist

import org.olaven.modulist.models.Item
import org.olaven.modulist.models.ModuleList
import kotlin.random.Random





private class Mocker {

    companion object {
        var id: Int = 0
        var nameIndex = 0
    }
}

fun getModuleLists(count: Int): List<ModuleList>{

    val list = mutableListOf<ModuleList>()
    for (i in 0 until count) {
        list.add(getModuleList())
    }

    return list
}

fun getModuleList(): ModuleList {
    val names = listOf("Pakkeliste", "Sommerferie", "Vinterferie", "Lekse", "Jobb")
    val name = names[Mocker.nameIndex % 5];
    Mocker.nameIndex++

    val items = listOf(
        Item("$name - one undone item", false),
        Item("$name - another undone item", false),
        Item("$name - one done item", true),
        Item("$name - another done item", true),
        Item("$name - should do this..", false),
        Item("$name - glad I did this", true)
    )

    return ModuleList(name, items, id= ++Mocker.id)
}
