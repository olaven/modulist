package org.olaven.modulist.database

import android.app.Activity
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
        ModuleList("holiday", Color.MAGENTA),
        ModuleList("Getting home", Color.RED),
        ModuleList("Shopping", Color.BLUE),
        ModuleList("winter holiday", Color.CYAN),
        ModuleList("summer holiday", Color.RED)
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
            itemModel.insert(item)
        }
    }

}

private fun createItemsFor(moduleList: ModuleList): List<Item> {

    val items = mutableListOf<Item>()
    for (i in 0 until 5) {
        items.add(Item("demo item $i in ${moduleList.name}", false, moduleList.id!!))
    }
    return items
}
