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
    private MutableLiveData<List<MeetingInfo>> mObservableData;

    public static MeetingRepository getInstance(Context context) {
        if (mRepository == null) {
            MeetingDatabase meetingDatabase = MeetingDatabase.getInstance(context);
            mRepository = new MeetingRepository(meetingDatabase.meetingDao());
        }
        return mRepository;
    }

    public MeetingRepository(MeetingDao meetingDao) {
        mMeetingDao = meetingDao;
    }

    public void addMeeting(final MeetingInfo meetingInfo) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mMeetingDao.scheduleMeeting(meetingInfo);

            }
        });
    }

    private void addMeetings(final List<MeetingInfo> infoList) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mMeetingDao.addMeetings(infoList);
            }
        });
    }

    public List<MeetingInfo> getMeetingsFromDatabase(String forDate) {
        return mMeetingDao.getScheduledMeeting(forDate);
    }

    public void getMeetingFromServer(final String forDate,
            MutableLiveData<List<MeetingInfo>> data) {
        mObservableData = data;
        MeetingApiService service = MeetingApiClient.getData().create(MeetingApiService.class);
        Call<List<MeetingInfo>> call = service.getScheduledMeeting(forDate);
        call.enqueue(new Callback<List<MeetingInfo>>() {
            @Override
            public void onResponse(Call<List<MeetingInfo>> call,
                    Response<List<MeetingInfo>> response) {
                if (response.body() != null) {
                    mObservableData.setValue(response.body());
                    MeetingUtil.addDateInMeetingList(response.body(), forDate);
                    addMeetings(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<MeetingInfo>> call, Throwable t) {
                //meetingInfo.setValue(null);
            }
        });
    }

    public void updateMeetingDataBaseFromServer() {
        // update database in some situation with date or without date
    }

}
