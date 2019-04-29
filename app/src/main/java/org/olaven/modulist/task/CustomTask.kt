package org.olaven.modulist.task

import android.app.Application
import android.os.AsyncTask
import org.olaven.modulist.database.ModelFactory
import org.olaven.modulist.database.model.ItemModel
import org.olaven.modulist.database.model.ListRelationModel
import org.olaven.modulist.database.model.ModuleListModel

abstract class CustomTask<Params, Progress, Result>(val application: Application): AsyncTask<Params, Progress, Result>() {

    protected var itemModel: ItemModel
    protected var moduleListModel: ModuleListModel
    protected var listRelationModel: ListRelationModel

    // used for transferring data to the task
    abstract class DTO

    init {
        itemModel = ModelFactory.getItemModel(application)
        moduleListModel = ModelFactory.getModuleListModel(application)
        listRelationModel = ModelFactory.getListRelationModel(application)
    }
}