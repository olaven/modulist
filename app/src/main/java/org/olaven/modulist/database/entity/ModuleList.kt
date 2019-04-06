package org.olaven.modulist.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class ModuleList(
    var name: String,
    var color: Int,
    @field:PrimaryKey(autoGenerate = true)
    var id: Long? = null
)



