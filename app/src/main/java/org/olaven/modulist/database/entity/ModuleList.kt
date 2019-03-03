package org.olaven.modulist.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Relation

@Entity
data class ModuleList (
    @field:PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String//,
    //@Relation(parentColumn = "id", entityColumn = "id")
    //var items: List<Item>
    //@Relation(parentColumn = "id", entityColumn = "superLists") // referencing itself.
    //val superLists: List<ModuleList> = emptyList(),
    //val color: Int = pickRandomColor()
)



