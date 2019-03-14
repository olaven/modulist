package org.olaven.modulist.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import org.olaven.modulist.database.entity.ListRelation

@Dao
interface ListRelationDAO: CommonDAO<ListRelation> {

    @Query("select * from ListRelation where child = :child")
    fun getByChild(child: Long): LiveData<List<ListRelation>>

    @Query("select * from ListRelation where parent = :parent")
    fun getByParent(parent: Long): LiveData<List<ListRelation>>

    @Query("delete from ListRelation")
    fun deleteAll()
}