package org.olaven.modulist.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update


@Dao
interface  CommonDAO<T> {

    //TODO: Believe I can do more as long as I can figure out how to access calssname from generic
    // @Query(select * from ${T::class.simpleName})


    @Insert
    fun insert(element: T): Long

    @Update
    fun update(element: T)

    @Delete
    fun delete(element: T)

}