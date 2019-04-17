package org.olaven.modulist.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import org.olaven.modulist.database.entity.ListRelation

@Dao
interface ListRelationDAO: CommonDAO<ListRelation> {

    @Query("select * from ListRelation where child = :child")
    fun getByChildIdLive(child: Long): LiveData<List<ListRelation>>

    @Query("select * from ListRelation where child = :child")
    fun getByChild(child: Long): List<ListRelation>

    @Query("select * from ListRelation where parent = :parent")
    fun getByParentIdLive(parent: Long): LiveData<List<ListRelation>>

    @Query("select * from ListRelation where parent = :parent")
    fun getByParentId(parent: Long): List<ListRelation>

    @Query("delete from ListRelation")
    fun deleteAll()
}