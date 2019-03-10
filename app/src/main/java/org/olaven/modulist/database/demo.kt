package org.olaven.modulist.database

import android.app.Application
import android.graphics.Color
import android.support.annotation.WorkerThread
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ModuleList

@WorkerThread
fun addDemoData(application: Application) {

    val moduleListModel = Models.getModuleListModel(application)
    val itemModel = Models.getItemModel(application)

    val moduleLists = listOf(
        ModuleList("Holiday", Color.MAGENTA),
        ModuleList("General", Color.RED),
        ModuleList("School", Color.BLUE),
        ModuleList("Work", Color.YELLOW),
        ModuleList("Winter holiday", Color.CYAN),
        ModuleList("Summer holiday", Color.RED)
    )


    moduleLists.forEach {

        Models.getModuleListModel(application)
            .insert(it)
    }

    // NOTE: have to read them back because their ID must be generated
    val moduleListsFromDB = moduleListModel.allModuleListsLive
    moduleListsFromDB.value?.forEach {

        val items = createItemsFor(it)
        items.forEach {item ->
            print("inserting item - ${item.name}")
            itemModel.insert(item)
        }
    }

}

private fun createItemsFor(moduleList: ModuleList): List<Item> {

    val items = mutableListOf<Item>()
    for (i in 0 until 5) {
        items.add(Item("demo item $i", false, moduleList.id!!))
    }
    return items
}
