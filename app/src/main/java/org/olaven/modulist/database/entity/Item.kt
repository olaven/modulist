package org.olaven.modulist.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Item(
    var name: String,
    var done: Boolean,
    var dayDistribution: Int,
    @ForeignKey(entity = ModuleList::class, parentColumns = ["id"], childColumns = ["moduleListId"])
    var moduleListId: Long,
    //@Relation()
    //val attachments: List<Any> = emptyList (),
    @field:PrimaryKey(autoGenerate = true)
    var id: Long? = null
)