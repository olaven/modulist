package org.olaven.modulist.database.dao

import android.arch.core.executor.testing.CountingTaskExecutorRule
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import android.content.Context
import android.graphics.Color
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import org.assertj.core.api.Assertions
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.olaven.modulist.database.AppDatabase
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ModuleList
import org.olaven.modulist.database.repository.ItemRepository
import org.olaven.modulist.database.repository.ModuleListRepository
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class ItemDaoTest {

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
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
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

        val retrieved = getValue(
            database.itemDAO().getById(id)
        )
        Assertions.assertThat(retrieved)
            .isEqualToIgnoringGivenFields(item, "id")


    }


    // Chandil, Sanchil. 2018. "Room with Unit test in Kotlin." Hentet dato 12.03.2018. https://medium.com/@chandilsachin/room-with-unit-test-in-kotlin-4ad31a39a291.
    @Throws(InterruptedException::class)
    fun <T> getValue(liveData: LiveData<T>): T {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(t: T?) {
                data[0] = t
                latch.countDown()
                liveData.removeObserver(this)//To change body of created functions use File | Settings | File Templates.
            }

        }
        liveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)

        return data[0] as T
    }

}