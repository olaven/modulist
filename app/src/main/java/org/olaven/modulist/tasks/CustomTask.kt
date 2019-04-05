package org.olaven.modulist.tasks

import android.app.Application
import android.os.AsyncTask
import org.olaven.modulist.database.Models
import org.olaven.modulist.database.model.ItemModel
import org.olaven.modulist.database.model.ListRelationModel
import org.olaven.modulist.database.model.ModuleListModel

abstract class CustomTask<Params, Progress, Result>(val application: Application): AsyncTask<Params, Progress, Result>() {

    protected lateinit var itemModel: ItemModel
    protected lateinit var moduleListModel: ModuleListModel
    protected lateinit var listRelationModel: ListRelationModel

    // used for transferring data to the task
    abstract class DTO

    init {
        itemModel = Models.getItemModel(application)
        moduleListModel = Models.getModuleListModel(application)
        listRelationModel = Models.getListRelationModel(application)
    }
}