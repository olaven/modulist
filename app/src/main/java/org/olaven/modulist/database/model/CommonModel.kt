package org.olaven.modulist.database.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.olaven.modulist.database.repository.CommonRepository


abstract class CommonModel<T>(application: Application): AndroidViewModel(application) {

    abstract val repository: CommonRepository<T>

    private var parentJob = Job()
    private val coroutineContext get() = parentJob + Dispatchers.Main
    protected val scope = CoroutineScope(coroutineContext)


    fun insert(element: T) = scope.launch(Dispatchers.IO) {
        repository.insert(element)
    }

    fun update(element: T) = scope.launch(Dispatchers.IO) {
        repository.update(element)
    }

    /**
     * Inserts and returns ID.
     * Has to be called async, as it does not spawn its own job
     */
    fun insertForId(element: T) =
            repository.insert(element)

    fun delete(element: T) = scope.launch(Dispatchers.IO) {
        repository.delete(element)
    }
}