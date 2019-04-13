package org.olaven.modulist.database.model

import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.olaven.modulist.database.AppDatabase
import org.olaven.modulist.database.entity.ListRelation
import org.olaven.modulist.database.repository.ListRelationRepository

class ListRelationModel(application: Application): CommonModel<ListRelation>(application) {

    override val repository = ListRelationRepository(
        AppDatabase.getDatabase(application.applicationContext).listRelationDAO()
    )

    fun getByParentIdLive(id: Long) =
            repository.getByParentIdLive(id)

    fun getByParentId(id: Long) =
            repository.getByParentId(id)

    fun getByChildIdLive(id: Long) =
            repository.getByChildIdLive(id)

    fun getByChildId(id: Long) =
            repository.getByChildId(id)

    fun deleteAll() = scope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }
}