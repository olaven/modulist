package org.olaven.modulist.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import org.olaven.modulist.R
import org.olaven.modulist.database.dao.ItemDao
import org.olaven.modulist.database.dao.ModuleListDao
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ModuleList

@Database(entities = arrayOf(Item::class, ModuleList::class), version = 2)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getItemDao(): ItemDao
    abstract fun moduleListDao(): ModuleListDao

    companion object {

        private val INSTANCE: AppDatabase? = null
        fun instance(context: Context): AppDatabase {

            return INSTANCE ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                context.getString(R.string.db_name))
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
