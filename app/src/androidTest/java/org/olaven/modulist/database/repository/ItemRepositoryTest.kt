package org.olaven.modulist.database.repository

import android.arch.lifecycle.Observer
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import kotlinx.coroutines.runBlocking

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.olaven.modulist.database.AppDatabase
import org.olaven.modulist.database.entity.Item

@RunWith(AndroidJUnit4::class)
class ItemRepositoryTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()!!
    private val dao = AppDatabase.getDatabase(context).itemDAO()
    private val itemRepository = ItemRepository(dao)




    @Test
    fun inserting() {

        val item = Item("some item",false, 3, 0)

        //TODO: implement test
        //TODO: make as much as possible of it generic
        runBlocking {
            itemRepository.insert(item)
            assertThat(true).isFalse();
        }

    }
}