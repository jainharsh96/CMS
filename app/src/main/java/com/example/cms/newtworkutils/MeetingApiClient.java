package com.example.cms.newtworkutils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MeetingApiClient {
    private static final String BASE_URL = "https://fathomless-shelf-5846.herokuapp.com/api/";

    public static Retrofit getData() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
