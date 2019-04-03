package org.olaven.modulist.database.repository

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import org.olaven.modulist.database.dao.ListRelationDAO
import org.olaven.modulist.database.entity.ListRelation

class ListRelationRepository(private val listRelationDAO: ListRelationDAO): CommonRepository<ListRelation>(listRelationDAO) {


    @WorkerThread
    fun getByParentIdLive(id: Long): LiveData<List<ListRelation>> =
        listRelationDAO.getByParentIdLive(id)

    @WorkerThread
    fun getByParentId(id: Long) =
        listRelationDAO.getByParentId(id)

    @WorkerThread
    fun getByChildIdLive(id: Long): LiveData<List<ListRelation>> =
        listRelationDAO.getByChildIdLive(id)

    @WorkerThread
    fun deleteAll() =
            listRelationDAO.deleteAll()
}