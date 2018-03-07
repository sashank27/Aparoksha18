package org.aparoksha.app18.ViewModels

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.util.Log
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.aparoksha.app18.models.Event
import org.aparoksha.app18.utils.*
import org.jetbrains.anko.toast

/**
 * Created by akshat on 6/3/18.
 */

class EventsViewModel(application: Application): AndroidViewModel(application) {

    val events: MutableLiveData<List<Event>> = MutableLiveData()

    init {
        events.value = emptyList()
    }

    fun getFromDB(context: Context) {
        launch(UI) {
            val appDb = AppDB.getInstance(context)
            val eventsList = appDb.getAllEvents()
            events.value = emptyList()
            events.value = eventsList
        }
    }

    fun getEvents(context: Context,activity: Activity) {
        val reference = "https://effervescence-iiita.github.io/Effervescence17/data/events.json"
        if (isNetworkConnectionAvailable(activity)) {
            launch(CommonPool) {
                val appDb = AppDB.getInstance(context)
                val eventsList = readEventsAsync(reference)
                launch(UI) {
                    events.value = emptyList()
                    events.value = eventsList
                    appDb.storeEvents(eventsList)
                }
            }
        } else {
            launch(UI) {
                if (events.value!!.isEmpty()) {
                    showAlert(activity)
                }
            }
        }
    }

    companion object {
        fun create(application: Application): EventsViewModel {
            return ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(EventsViewModel::class.java)
        }
    }
}