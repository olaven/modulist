package org.olaven.modulist.database.repository

import android.support.annotation.WorkerThread
import org.olaven.modulist.database.dao.CommonDAO

abstract class CommonRepository<T>(val dao: CommonDAO<T>) {

    // val allElements... ikke observator

    @WorkerThread
    suspend fun insert(element: T) =
        dao.insert(element)

    @WorkerThread
    suspend fun delete(element: T) =
        dao.delete(element)


}
