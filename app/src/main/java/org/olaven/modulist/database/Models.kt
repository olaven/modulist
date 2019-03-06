package org.olaven.modulist.database

import android.app.Application
import org.olaven.modulist.database.entity.ModuleList
import org.olaven.modulist.database.model.ItemModel
import org.olaven.modulist.database.model.ModuleListModel

class Models {

    companion object {
        @Volatile
        private var itemModelInstance: ItemModel? = null

        @Volatile
        private var moduleListInstance: ModuleListModel? = null

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

    }
}