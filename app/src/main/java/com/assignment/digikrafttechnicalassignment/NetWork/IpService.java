package com.assignment.digikrafttechnicalassignment.NetWork;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IpService {

    @GET("map_service.html?")
    Call<JsonObject> getAllData(@Query("mtype") String mtype, @Query("co") String co);


}
