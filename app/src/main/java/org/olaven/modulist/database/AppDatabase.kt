package org.olaven.modulist.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import org.olaven.modulist.database.dao.ItemDAO
import org.olaven.modulist.database.dao.ModuleListDao
import org.olaven.modulist.database.entity.Item

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun itemDAO(): ItemDAO

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
                    ).build()

                    INSTANCE = instance
                    return instance
                }

            }
    }
}

