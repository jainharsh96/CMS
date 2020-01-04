package com.example.cms.ui;

import com.example.cms.MeetingRepository.MeetingRepository;
import com.example.cms.Models.MeetingInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MeetingViewModel extends ViewModel {
    private MeetingRepository mRepository;
    private HashMap<String, LiveData<List<MeetingInfo>>> mMeetingsList;
    private MutableLiveData<List<MeetingInfo>> currentMeetingInfo;
    private String mForDate;

    public MeetingViewModel(String date, MeetingRepository repository) {
        mForDate = date;
        mRepository = repository;
    }

    public String getForDate() {
        return mForDate;
    }

    public void getMeetings(String forDate) {
        mForDate = forDate;
        if (mMeetingsList == null || mMeetingsList.get(forDate) == null) {
            loadMeeting(forDate);
        }
        currentMeetingInfo.setValue(mMeetingsList.get(forDate).getValue());
    }

    public LiveData<List<MeetingInfo>> getCurrentMeetingInfo(){
        if (currentMeetingInfo == null){
            currentMeetingInfo = new MutableLiveData<>();
            getMeetings(mForDate);
        }
        return currentMeetingInfo;
    }

    private void loadMeeting(String forDate) {
        if (mMeetingsList == null) {
            mMeetingsList = new HashMap<>();
        }
        mMeetingsList.put(forDate, mRepository.getMeetings(forDate));
    }

}
