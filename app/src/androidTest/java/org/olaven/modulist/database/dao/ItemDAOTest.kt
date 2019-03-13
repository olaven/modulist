package org.olaven.modulist.database.dao

import android.arch.core.executor.testing.CountingTaskExecutorRule
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.Context
import android.graphics.Color
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.olaven.modulist.database.AppDatabase
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ModuleList
import org.junit.Rule



@RunWith(AndroidJUnit4::class)
class ItemDAOTest {

    private lateinit var database: AppDatabase
    private lateinit var itemDao: ItemDAO
    private lateinit var moduleListDao: ModuleListDAO

    private lateinit var moduleList: ModuleList

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {

        database = AppDatabase.getDatabase(ApplicationProvider.getApplicationContext<Context>())
        itemDao = database.itemDAO()

        moduleListDao = database.moduleListDAO()

        val id = database.moduleListDAO().insert(
            ModuleList("modulelist", Color.YELLOW)
        )

        moduleList = database.moduleListDAO().getById(id)

    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun canInsertItem() {

        val item = Item("some item", false, 3, moduleList.id!!)
        val id = itemDao.insert(item)

        val retrieved = itemDao.getById(id)

        assertThat(retrieved)
            .isNotNull
    }


    @Test
    fun canGetAllItemslive() {

        val listIds = listOf(
            ModuleList("school", Color.BLUE),
            ModuleList("work", Color.RED),
            ModuleList("holiday", Color.CYAN)
        ).map {moduleListDao.insert(it)}

        listIds.forEach {
            itemDao.insert(Item("item for $it", false, 2, it))
        }

        val allItems = itemDao.getAllItemsLive()
        val expectedCount = (listIds.count() * 2)

        assertThat(allItems.value?.count())
            .isEqualTo(expectedCount)

    }

    @Test
    fun canGetByListId() {

        val listIds = listOf(
            ModuleList("school", Color.BLUE),
            ModuleList("work", Color.RED),
            ModuleList("holiday", Color.CYAN)
        ).map {moduleListDao.insert(it)}

        listIds.forEach {
            itemDao.insert(Item("item for $it", false, 2, it))
        }

        listIds.forEach { parent ->
            val retrievedLive = itemDao.getByModuleListId(parent)
            val retrieved = retrievedLive.value

            assertThat(retrieved?.count())
                .isEqualTo(listIds.count()) // three for each parent
        }
    }
}