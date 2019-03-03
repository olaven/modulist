package org.olaven.modulist.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Relation
import org.jetbrains.annotations.NotNull

@Entity
data class Item(
    var name: String,
    var done: Boolean,
    //@Relation()
    //val attachments: List<Any> = emptyList ()
    @field:PrimaryKey(autoGenerate = true)
    var id: Int = -1
)