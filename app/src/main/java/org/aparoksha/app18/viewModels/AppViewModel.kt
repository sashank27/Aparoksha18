package org.aparoksha.app18.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.aparoksha.app18.models.Event
import org.aparoksha.app18.utils.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Collections.emptyList

/**
 * Created by akshat on 6/3/18.
 */

class AppViewModel(application: Application): AndroidViewModel(application) {

    val events: MutableLiveData<List<Event>> = MutableLiveData()
    var dataFetchFailed: MutableLiveData<Boolean> = MutableLiveData()
    val event: MutableLiveData<Event> = MutableLiveData()

    private lateinit var appDb: AppDB
    var dataFetched : MutableLiveData<Boolean> = MutableLiveData()

    init {
        events.value = emptyList()
        dataFetchFailed.value = false
        dataFetched.value = false
    }

    fun initAppDB(context: Context){
        appDb = AppDB.getInstance(context)
        getEvents()
    }

    fun getEventById(id: Long,appDb: AppDB): Event? {
        val eventsList = appDb.getAllEvents()

        for (event in eventsList) {
            if (event.id == id) {
                return event
            }
        }
        return null
    }

    private fun getEvents() {

        val eventsList = appDb.getAllEvents()
        events.value = eventsList

        fetchEvents()
    }

    private fun fetchEvents () {
        val reference = "https://effervescence-iiita.github.io/Effervescence17/data/"

        val retrofit = Retrofit.Builder()
                .baseUrl(reference)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        val githubService = retrofit.create(GithubService::class.java)

        val fetchedEvents = githubService.fetchEvents()
        launch(UI) {
            try {
                events.value = fetchedEvents.await()
                appDb.storeEvents(events.value!!)
            } catch (e: Exception) {
                if(events.value!!.isNotEmpty()) {

                }
                println(e)
            }
        }


    }

    companion object {
        fun create(application: Application): AppViewModel {
            return ViewModelProvider.AndroidViewModelFactory
                    .getInstance(application).create(AppViewModel::class.java)
                    .apply { initAppDB(application) }
        }
    }
}