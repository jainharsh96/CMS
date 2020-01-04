package com.example.cms.newtworkutils;

import com.example.cms.Models.MeetingInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MeetingApiService {

    @GET("schedule")
    Call<List<MeetingInfo>> getScheduledMeeting(@Query("date") String forDate);
}
