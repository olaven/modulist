package org.olaven.modulist.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import org.olaven.modulist.database.entity.ModuleList

@Dao
interface ModuleListDAO: CommonDAO<ModuleList> {

    @Insert
    fun insertModuleList(moduleList: ModuleList)

    @Query("select * from ModuleList")
    fun getAllModuleLists(): List<ModuleList>

    @Query("select * From ModuleList")
    fun getAllModuleListsLive(): LiveData<List<ModuleList>>

    @Query("select * from ModuleList where id = :id")
    fun getById(id: Long): ModuleList
}