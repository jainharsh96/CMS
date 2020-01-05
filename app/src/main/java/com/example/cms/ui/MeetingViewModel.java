package com.example.cms.ui;

import com.example.cms.Repositorys.MeetingRepository;
import com.example.cms.Models.MeetingInfo;
import com.example.cms.database.MeetingDao;

import java.util.HashMap;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MeetingViewModel extends ViewModel {
    private MeetingRepository mRepository;
    private HashMap<String, List<MeetingInfo>> mMeetingsList;
    private MutableLiveData<List<MeetingInfo>> currentMeetingInfo;
    private String mForDate;

    public MeetingViewModel(String date, MeetingRepository repository) {
        mForDate = date;
        mRepository = repository;
    }

    public String getForDate() {
        return mForDate;
    }

    public void getMeetingsFromViewModel(String forDate) {
        mForDate = forDate;
        if (mMeetingsList == null || mMeetingsList.get(forDate) == null) {
            currentMeetingInfo.setValue(null);
            getMeetingFromRepository(forDate);
        } else {
        currentMeetingInfo.setValue(mMeetingsList.get(forDate));
        }
    }

    public LiveData<List<MeetingInfo>> getCurrentMeetingInfo(){
        if (currentMeetingInfo == null){
            currentMeetingInfo = new MutableLiveData<>();
            getMeetingsFromViewModel(mForDate);
        }
        return currentMeetingInfo;
    }

    public void getMeetingFromRepository(String forDate) {
        if (mMeetingsList == null) {
            mMeetingsList = new HashMap<>();
        }
        List<MeetingInfo> meetingInfos = mRepository.getMeetingsFromDatabase(forDate);
        if(meetingInfos != null && meetingInfos.size() > 0 ) {
            mMeetingsList.put(forDate, meetingInfos);
            currentMeetingInfo.setValue(mMeetingsList.get(forDate));
        } else {
            mRepository.getMeetingFromServer(forDate, currentMeetingInfo);
        }
    }

}
