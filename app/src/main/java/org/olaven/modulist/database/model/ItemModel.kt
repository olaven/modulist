package org.olaven.modulist.database.model

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.olaven.modulist.database.AppDatabase
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.repository.ItemRepository

class ItemModel(application: Application): AndroidViewModel(application) {

    private val itemDAO = AppDatabase
        .getDatabase(application.applicationContext)
        .itemDAO()
    private val repository: ItemRepository
    val allItems: LiveData<List<Item>>

    //coroutine
    private var parentJob = Job()
    private val coroutineContext get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    init {

        repository = ItemRepository(itemDAO)
        allItems = repository.allItemsLive
    }

    fun insert(item: Item) = scope.launch(Dispatchers.IO) {
        repository.insert(item)
    }

    fun deleteAll() = scope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }
}