package org.olaven.modulist.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class ModuleList(
    val name: String,
    val color: Int,
    @field:PrimaryKey(autoGenerate = true)
    var id: Int? = null
)



