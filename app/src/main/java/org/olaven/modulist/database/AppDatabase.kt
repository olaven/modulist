package org.olaven.modulist.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import org.olaven.modulist.database.dao.ItemDAO
import org.olaven.modulist.database.dao.ListRelationDAO
import org.olaven.modulist.database.dao.ModuleListDAO
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ListRelation
import org.olaven.modulist.database.entity.ModuleList

@Database(entities = [Item::class, ModuleList::class, ListRelation::class], version = 10, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun itemDAO(): ItemDAO
    abstract fun moduleListDAO(): ModuleListDAO
    abstract fun listRelationDAO(): ListRelationDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

            fun getDatabase(context: Context): AppDatabase {

                val tempInstance = INSTANCE
                if (tempInstance != null)
                    return tempInstance

                synchronized(this) {

                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "modulist_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                    return instance
                }

            }

    }
}

