package org.olaven.modulist.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class ListRelation (
    @field:ForeignKey(entity = ListRelation::class, parentColumns = ["id"], childColumns = ["child"])
    var child: Long? = null,
    @field:ForeignKey(entity = ListRelation::class, parentColumns = ["id"], childColumns = ["parent"])
    var parent: Long? = null,
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
)
