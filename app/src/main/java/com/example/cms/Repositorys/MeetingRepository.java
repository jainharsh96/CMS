package com.example.cms.Repositorys;

import com.example.cms.Models.MeetingInfo;
import com.example.cms.database.MeetingDao;
import com.example.cms.database.MeetingDatabase;
import com.example.cms.newtworkutils.MeetingApiClient;
import com.example.cms.newtworkutils.MeetingApiService;
import com.example.cms.util.MeetingUtil;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeetingRepository {
    private static MeetingRepository mRepository;
    private MeetingDao mMeetingDao;

    public static MeetingRepository getInstance(Context context){
        if(mRepository == null){
            MeetingDatabase meetingDatabase = MeetingDatabase.getInstance(context);
            mRepository = new MeetingRepository(meetingDatabase.meetingDao());
        }
        return mRepository;
    }

    public MeetingRepository(MeetingDao meetingDao) {
        mMeetingDao = meetingDao;
    }

    public LiveData<List<MeetingInfo>> getMeetings(String forDate) {
        LiveData<List<MeetingInfo>> meetingInfo =
                mMeetingDao.getScheduledMeeting(forDate);
       // int x = mMeetingDao.getRow();
        if(meetingInfo.getValue() == null){
            meetingInfo = getMeetingFromApi(forDate);
        }
        return meetingInfo;
    }

    public void addMeeting(final MeetingInfo meetingInfo) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mMeetingDao.scheduleMeeting(meetingInfo);
            }
        });
    }

    private LiveData<List<MeetingInfo>> getMeetingFromApi(final String forDate){
        final MutableLiveData<List<MeetingInfo>> meetingInfo = new MutableLiveData<>();
        MeetingApiService service = MeetingApiClient.getData().create(MeetingApiService.class);
        Call<List<MeetingInfo>> call = service.getScheduledMeeting(forDate);
        call.enqueue(new Callback<List<MeetingInfo>>() {
            @Override
            public void onResponse(Call<List<MeetingInfo>> call,
                    Response<List<MeetingInfo>> response) {
                if (response.body() != null) {
                    meetingInfo.setValue(response.body());
                    MeetingUtil.addDateInMeetingList(meetingInfo.getValue(), forDate);
                    insertData(meetingInfo.getValue());
                }
            }

            @Override
            public void onFailure(Call<List<MeetingInfo>> call, Throwable t) {
                meetingInfo.setValue(null);
            }
        });
        return meetingInfo;
    }

    private void insertData(final List<MeetingInfo> infoList){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mMeetingDao.addMeetings(infoList);
            }
        });
    }

}