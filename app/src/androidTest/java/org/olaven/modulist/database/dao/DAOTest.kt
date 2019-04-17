package org.olaven.modulist.database.dao

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.olaven.modulist.database.AppDatabase
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


abstract class DAOTest {

    private lateinit var database: AppDatabase

    protected lateinit var moduleListDAO: ModuleListDAO
    protected lateinit var itemDAO: ItemDAO
    protected lateinit var listRelationDAO: ListRelationDAO

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

        moduleListDAO = database.moduleListDAO()
        itemDAO = database.itemDAO()
        listRelationDAO = database.listRelationDAO()
    }

    @After
    fun teardown() {

        database.close()
    }


    // Chandil, Sanchil. 2018. "Room with Unit test in Kotlin." Hentet dato 12.03.2018. https://medium.com/@chandilsachin/room-with-unit-test-in-kotlin-4ad31a39a291.
    @Throws(InterruptedException::class)
    protected fun <T> getValue(liveData: LiveData<T>): T {
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