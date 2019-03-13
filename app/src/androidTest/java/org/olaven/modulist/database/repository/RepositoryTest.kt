package org.olaven.modulist.database.repository

import android.arch.core.executor.testing.CountingTaskExecutorRule
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.content.Context
import android.graphics.Color
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.olaven.modulist.database.AppDatabase
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ModuleList
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RepositoryTest {

    lateinit var database: AppDatabase

    lateinit var moduleListRepository: ModuleListRepository
    lateinit var itemRepository: ItemRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var countingTaskExecutorRule = CountingTaskExecutorRule()

    @Before
    fun init() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        // database = AppDatabase.getDatabase(context)
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        moduleListRepository = ModuleListRepository(database.moduleListDAO())
        itemRepository = ItemRepository(database.itemDAO())
    }

    @After
    fun teardown() {

        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoTest() {

        val moduleListId = database.moduleListDAO().insert(
            ModuleList("parent list", Color.BLUE)
        )

        val item = Item("some item", false, 3, moduleListId)
        val id = database.itemDAO().insert(item)

        val retrieved = database.itemDAO().getById(id).value
        assertThat(retrieved)
            .isEqualTo(item)

    }

    @Test
    @Throws(IOException::class)
    fun insertAndRetrieve() {

        GlobalScope.launch {
            val moduleListId = moduleListRepository.insert(
                ModuleList("parent list", Color.BLUE)
            )
            val item = Item("some item", false, 3, moduleListId)

            val id = itemRepository.insert(item)

            val retrieved = itemRepository.getByid(id)

            assertThat(retrieved)
                .isEqualTo(item)

            //NOTE: Testen går gjennom, selv om dette false
            assertThat(true)
                .isFalse()
        }
    }

}