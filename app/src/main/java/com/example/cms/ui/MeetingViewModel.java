package com.example.cms.ui;

import com.example.cms.MeetingRepository.MeetingRepository;
import com.example.cms.Models.MeetingInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MeetingViewModel extends ViewModel {
    private MeetingRepository mRepository;
    private HashMap<String, LiveData<List<MeetingInfo>>> mMeetingsList;
    private String mForDate;

    public MeetingViewModel(String date, MeetingRepository repository) {
        mForDate = date;
        mRepository = repository;
    }

    public String getForDate() {
        return mForDate;
    }

    public LiveData<List<MeetingInfo>> getMeetings(String forDate) {

        mForDate = forDate;
        if (mMeetingsList == null || mMeetingsList.get(forDate).getValue() == null) {
            loadMeeting(forDate);
        }
        return mMeetingsList.get(forDate);
    }

    private void loadMeeting(String forDate) {
        if (mMeetingsList == null) {
            mMeetingsList = new HashMap<>();
        }
        mMeetingsList.put(forDate, mRepository.getMeetings(forDate));
    }

}
