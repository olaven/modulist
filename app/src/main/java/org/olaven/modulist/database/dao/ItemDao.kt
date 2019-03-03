package org.olaven.modulist.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import org.olaven.modulist.database.entity.Item

@Dao
interface ItemDao {

    @Insert
    fun insert(item: Item)

    @Query("select * from Item")
    fun getAll(): List<Item>

    @Query("select * from Item where id = :id")
    fun getById(id: Int): Item

}