package org.olaven.modulist.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModuleList(
    var name: String,
    var color: Int,
    @field:PrimaryKey(autoGenerate = true)
    var id: Long? = null
)



