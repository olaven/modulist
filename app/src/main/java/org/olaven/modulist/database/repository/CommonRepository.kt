package org.olaven.modulist.database.repository

import androidx.annotation.WorkerThread
import org.olaven.modulist.database.dao.CommonDAO

abstract class CommonRepository<T>(val dao: CommonDAO<T>) {

    @WorkerThread
    fun insert(element: T): Long =
        dao.insert(element)

    @WorkerThread
    fun update(element: T) =
        dao.update(element)

    @WorkerThread
    fun delete(element: T) =
        dao.delete(element)



}
