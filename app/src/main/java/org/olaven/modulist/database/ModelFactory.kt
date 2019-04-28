package org.olaven.modulist.database

import android.app.Application
import org.olaven.modulist.database.model.ItemModel
import org.olaven.modulist.database.model.ListRelationModel
import org.olaven.modulist.database.model.ModuleListModel

class ModelFactory {

    companion object {

        @Volatile
        private var itemModelInstance: ItemModel? = null
        @Volatile
        private var moduleListInstance: ModuleListModel? = null
        @Volatile
        private var listRelationModel: ListRelationModel? = null

        fun getItemModel(application: Application) =
            if (itemModelInstance != null)
                itemModelInstance!!
            else {
                itemModelInstance = ItemModel(application)
                itemModelInstance!!
            }

        fun getModuleListModel(application: Application) =
            if (moduleListInstance != null)
                moduleListInstance!!
            else {
                moduleListInstance = ModuleListModel(application)
                moduleListInstance!!
            }

        fun getListRelationModel(application: Application) =
            if (listRelationModel != null)
                listRelationModel!!
            else {
                listRelationModel = ListRelationModel(application)
                listRelationModel!!
            }


    }
}