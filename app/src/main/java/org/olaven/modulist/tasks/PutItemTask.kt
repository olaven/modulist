package org.olaven.modulist.tasks

import android.app.Application
import android.os.AsyncTask
import org.olaven.modulist.database.Models
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.model.ItemModel
import org.olaven.modulist.database.model.ListRelationModel
import org.olaven.modulist.database.model.ModuleListModel

/**
 * Inserts an item to a list and its child-lists.
 * If the item has an ID, it will replace
 * //TODO: reuse this when updating items
 */
class PutItemTask: AsyncTask<PutItemTask.DTO, Unit, Unit>() {

    private lateinit var itemModel: ItemModel
    private lateinit var moduleListModel: ModuleListModel
    private lateinit var listRelationModel: ListRelationModel


    class DTO(val item: Item, val application: Application)

    override fun doInBackground(vararg DTOs: DTO?) {

        DTOs.forEach {

            it?.let {

                initialzeModels(it.application)
                val item = it.item

                putItem(item)
            }
        }
    }

    private fun putItem(item: Item) {

        itemModel.insert(item)

        val childLists = listRelationModel.getByParentId(item.moduleListId)
        childLists.forEach {

            //NOTE: reassigning this is OK, as it has already been persisted a first time
            item.moduleListId = it.child!!
            itemModel.insert(item)
        }
    }

    private fun initialzeModels(application: Application) {

        itemModel = Models.getItemModel(application)
        moduleListModel = Models.getModuleListModel(application)
        listRelationModel = Models.getListRelationModel(application)

    }
}