package org.aparoksha.app18.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProvider
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.aparoksha.app18.models.Event
import org.aparoksha.app18.utils.*
import java.util.Collections.emptyList

/**
 * Created by akshat on 6/3/18.
 */

class AppViewModel(application: Application): AndroidViewModel(application) {

    val events: MutableLiveData<List<Event>> = MutableLiveData()
    var empty: MutableLiveData<Boolean> = MutableLiveData()
    init {
        events.value = emptyList()
        empty.value = false
    }

    fun getEvents(appDb: AppDB,isNetworkConnected: Boolean) {
        val reference = "https://effervescence-iiita.github.io/Effervescence17/data/events.json"

        launch(UI) {
            val eventsList = appDb.getAllEvents()
            events.value = emptyList()
            events.value = eventsList
            if(!isNetworkConnected && eventsList.isEmpty()) empty.value = true
        }

        if (isNetworkConnected) {
            launch(CommonPool) {
                val eventsList = readEventsAsync(reference)

                eventsList?.let {
                    launch(UI) {
                        events.value = emptyList()
                        events.value = it
                        appDb.storeEvents(it)
                    }
                }
            }
        }
    }

    companion object {
        fun create(application: Application): AppViewModel {
            return ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(AppViewModel::class.java)
        }
    }
}