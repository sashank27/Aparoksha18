package org.aparoksha.app18.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProvider
import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import org.aparoksha.app18.models.Event
import org.aparoksha.app18.utils.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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

        val eventsList = appDb.getAllEvents()
        events.value = eventsList
        if (!isNetworkConnected && eventsList.isEmpty()) empty.value = true


        if (isNetworkConnected) {
            fetchEvents(appDb)
        }

    }

    private fun fetchEvents (appDb: AppDB) {
        val reference = "https://effervescence-iiita.github.io/Effervescence17/data/"

        val retrofit = Retrofit.Builder()
                .baseUrl(reference)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        val githubService = retrofit.create(GithubService::class.java)

        val call = githubService.fetchEvents()

        call.enqueue(object : Callback<List<EventPojo>> {

            override fun onFailure(call: Call<List<EventPojo>>?, t: Throwable?) {
                Log.e("error", t.toString())
            }

            override fun onResponse(call: Call<List<EventPojo>>?, response: Response<List<EventPojo>>) {
                if(response.isSuccessful) {
                    val allEvents = response.body()

                    if(allEvents != null) {
                        val eventsList :MutableList<Event> = mutableListOf()
                        for (event in allEvents) {
                            eventsList.add(Event(event.id,event.name,event.description,event.location,event.timestamp,event.imageUrl,event.categories))
                        }

                        events.value = eventsList
                        appDb.storeEvents(eventsList)
                    }

                }
            }

        })
    }

    companion object {
        fun create(application: Application): AppViewModel {
            return ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(AppViewModel::class.java)
        }
    }
}