package org.olaven.modulist.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import org.olaven.modulist.database.entity.ModuleList

@Dao
interface ModuleListDao {

    @Insert
    fun insertModuleList(moduleList: ModuleList)

    @Query("select * from ModuleList")
    fun getAllModuleLists(): List<ModuleList>

}