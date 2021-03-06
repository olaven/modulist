package org.olaven.modulist.database

import android.app.Application
import android.graphics.Color
import androidx.annotation.WorkerThread
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ListRelation
import org.olaven.modulist.database.entity.ModuleList
import kotlin.random.Random

@WorkerThread
fun addDemoData(application: Application) {

    val moduleListModel = ModelFactory.getModuleListModel(application)
    val itemModel = ModelFactory.getItemModel(application)
    val listRelationModuleList = ModelFactory.getListRelationModel(application)

    val holiday = ModuleList("Holiday", Color.MAGENTA)
    val winterHoliday = ModuleList("Winter holiday", Color.CYAN)
    val summerHoliday = ModuleList("Summer holiday", Color.RED)

    val moduleLists = listOf(
        holiday,
        winterHoliday,
        summerHoliday
    )


    listRelationModuleList.insert(
        ListRelation(winterHoliday.id, holiday.id)
    )
    listRelationModuleList.insert(
        ListRelation(summerHoliday.id, holiday.id)
    )

    moduleLists.forEach {

        ModelFactory.getModuleListModel(application)
            .insert(it)
    }

    // NOTE: have to read them back because their ID must be generated
    val moduleListsFromDB = moduleListModel.getAllModuleListsLive()
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
        val item = Item("item $i in ${moduleList.name}", false, randomIntBelow(4), moduleList.id!!)
        items.add(item)
    }
    return items
}

private fun randomIntBelow(number: Int) =
    Random.nextInt(number)