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

    fun getEventById(id: Long): Event? {
        val eventsList = appDb.getAllEvents()

        for (event in eventsList) {
            if (event.id == id) {
                return event
            }
        }
        return null
    }

    fun getEvents() {
        launch(UI) {
            val eventsList = appDb.getAllEvents()
            events.value = eventsList
        }
        fetchEvents()
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
                events.value = fetchedEvents.await()
                appDb.storeEvents(events.value!!)
            } catch (e: Exception) {
                if (events.value!!.isNotEmpty()) {

                }
                println(e)
            }
        }


    }

    fun getTeam() {
        val teamList = appDb.getAllTeamMembers()
        team.value = teamList

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

    fun getSponsors() {
        val sponsorList = appDb.getAllSponsors()
        sponsor.value = sponsorList

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