package org.aparoksha.app18.viewModels

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.aparoksha.app18.models.Event
import org.aparoksha.app18.models.Person
import org.aparoksha.app18.models.Sponsor
import org.aparoksha.app18.utils.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Collections.emptyList

/**
 * Created by akshat on 6/3/18.
 */

class AppViewModel(application: Application) : AndroidViewModel(application) {

    var dataFetchFailed: MutableLiveData<Boolean> = MutableLiveData()

    val events: MutableLiveData<List<Event>> = MutableLiveData()
    val event: MutableLiveData<Event> = MutableLiveData()
    val team: MutableLiveData<List<Person>> = MutableLiveData()
    val sponsor: MutableLiveData<List<Sponsor>> = MutableLiveData()

    private lateinit var appDb: AppDB
    var dataFetched: MutableLiveData<Boolean> = MutableLiveData()

    init {
        events.value = emptyList()
        team.value = emptyList()
        sponsor.value = emptyList()

        dataFetchFailed.value = false
        dataFetched.value = false
    }

    fun initAppDB(context: Context) {
        appDb = AppDB.getInstance(context)
    }

    fun getEventById(eventID: Long)  = appDb.getEventByID(eventID)

    fun getEvents(activity: Activity,fetch: Boolean) {
        launch(UI) {
            val eventsList = appDb.getAllEvents()
            events.value = eventsList
            if(!isNetworkConnectionAvailable(activity) && eventsList.size == 0) showAlert(activity)
        }
        if (fetch) fetchEvents()
    }

    private fun fetchEvents () {
        val reference = "https://aparoksha18.github.io/Aparoksha-Data/data/"

        val retrofit = Retrofit.Builder()
                .baseUrl(reference)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        val githubService = retrofit.create(GithubService::class.java)

        launch(UI) {
            val fetchedEvents = githubService.fetchEvents()
            try {
                val eventsNew = fetchedEvents.await()
                val finalEvents = eventsNew.map {
                    Event(it.id,it.name,it.description,it.location,it.timestamp + ((330*60)),it.duration,it.imageUrl,it.categories,it.additionalInfo,it.facebookEventLink,it.organizers)
                }
                events.value = finalEvents
                appDb.storeEvents(finalEvents)
            } catch (e: Exception) {
                println(e)
            }
        }


    }

    fun getTeam(activity: Activity) {
        launch(UI) {
            val teamList = appDb.getAllTeamMembers()
            team.value = teamList
            if(!isNetworkConnectionAvailable(activity) && teamList.size == 0) showAlert(activity)
        }

        fetchTeam()
    }

    private fun fetchTeam() {
        val reference = "https://aparoksha18.github.io/Aparoksha-Data/data/"

        val retrofit = Retrofit.Builder()
                .baseUrl(reference)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        val githubService = retrofit.create(GithubService::class.java)

        val fetchedTeam = githubService.fetchTeam()
        launch(UI) {
            try {
                team.value = fetchedTeam.await()
                appDb.storeTeam(team.value!!)
            } catch (e: Exception) {
                if (events.value!!.isNotEmpty()) {

                }
                println(e)
            }
        }


    }

    fun getSponsors(activity: Activity) {
        launch(UI) {
            val sponsorList = appDb.getAllSponsors()
            sponsor.value = sponsorList
            if(!isNetworkConnectionAvailable(activity) && sponsorList.size == 0) showAlert(activity)
        }

        fetchSponsors()
    }

    private fun fetchSponsors() {
        val reference = "https://aparoksha18.github.io/Aparoksha-Data/data/"

        val retrofit = Retrofit.Builder()
                .baseUrl(reference)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        val githubService = retrofit.create(GithubService::class.java)

        val fetchedSponsors = githubService.fetchSponsors()
        launch(UI) {
            try {
                sponsor.value = fetchedSponsors.await()
                appDb.storeSponsors(sponsor.value!!)
            } catch (e: Exception) {
                if (events.value!!.isNotEmpty()) {

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