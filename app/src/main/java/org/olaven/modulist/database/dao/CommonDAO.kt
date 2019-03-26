package org.olaven.modulist.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy


@Dao
interface  CommonDAO<T> {

    //TODO: Believe I can do more as long as I can figure out how to access calssname from generic
    // @Query(select * from ${T::class.simpleName})


    @Insert
    fun insert(element: T): Long

    @Delete
    fun delete(element: T)

}