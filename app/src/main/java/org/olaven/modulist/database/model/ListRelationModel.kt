package org.olaven.modulist.database.model

import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.olaven.modulist.database.AppDatabase
import org.olaven.modulist.database.entity.ListRelation
import org.olaven.modulist.database.repository.CommonRepository
import org.olaven.modulist.database.repository.ListRelationRepository
import org.olaven.modulist.database.repository.ModuleListRepository

class ListRelationModel(application: Application): CommonModel<ListRelation>(application) {

    override val repository = ListRelationRepository(
        AppDatabase.getDatabase(application.applicationContext).listRelationDAO()
    )

    fun deleteAll() = scope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }
}