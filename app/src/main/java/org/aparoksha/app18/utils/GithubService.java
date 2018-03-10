package org.aparoksha.app18.utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by akshat on 10/3/18.
 */

public interface GithubService {

    @GET("events.json")
    Call<List<EventPojo>> fetchEvents();

}
