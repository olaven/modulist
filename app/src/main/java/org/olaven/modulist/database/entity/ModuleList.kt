package org.olaven.modulist.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Relation

@Entity
data class ModuleList(val name: String, val color: Int ) {

    @field:PrimaryKey(autoGenerate = true)
    var id: Int? = null
}



