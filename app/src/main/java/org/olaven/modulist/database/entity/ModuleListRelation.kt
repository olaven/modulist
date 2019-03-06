/*


package org.olaven.modulist.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Relation

/*
* Mapping List<ModuleList> on ModuleList-entity gives
* a stack overflow exception. TODO: Check if below is a proper solution
* */

@Entity
data class ModuleListRelation (
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @Relation(parentColumn = "id", entityColumn = "id")
    var superList: ModuleList,
    @Relation(parentColumn = "id", entityColumn = "id")
    var sublist: ModuleList
)

**/