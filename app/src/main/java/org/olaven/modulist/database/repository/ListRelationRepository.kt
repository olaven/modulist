package org.olaven.modulist.database.repository

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import org.olaven.modulist.database.dao.ListRelationDAO
import org.olaven.modulist.database.entity.ListRelation

class ListRelationRepository(private val listRelationDAO: ListRelationDAO): CommonRepository<ListRelation>(listRelationDAO) {


    @WorkerThread
    suspend fun getByParentId(id: Long): LiveData<List<ListRelation>> =
        listRelationDAO.getByParent(id)

    @WorkerThread
    suspend fun getByChildId(id: Long): LiveData<List<ListRelation>> =
        listRelationDAO.getByChild(id)

    @WorkerThread
    suspend fun deleteAll() =
            listRelationDAO.deleteAll()
}