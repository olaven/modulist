package org.olaven.modulist.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity
data class Item(
    var name: String,
    var done: Boolean,
    @ForeignKey(entity = ModuleList::class, parentColumns = ["id"], childColumns = ["moduleListId"])
    var moduleListId: Int,
    //@Relation()
    //val attachments: List<Any> = emptyList (),
    @field:PrimaryKey(autoGenerate = true)
    var id: Int? = null
)