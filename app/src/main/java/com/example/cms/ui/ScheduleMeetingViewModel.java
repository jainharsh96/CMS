package com.example.cms.ui;

import com.example.cms.Models.MeetingInfo;

import androidx.lifecycle.ViewModel;

public class ScheduleMeetingViewModel extends ViewModel {
    private MeetingInfo mMeetingInfo;

    public MeetingInfo getData(){
        if(mMeetingInfo == null){
            mMeetingInfo = new MeetingInfo();
        }
        return mMeetingInfo;
    }
}
