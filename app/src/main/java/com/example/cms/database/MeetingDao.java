package com.example.cms.database;

import com.example.cms.Models.MeetingInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MeetingDao {
    @Insert
    void scheduleMeeting(MeetingInfo meetingInfo);

    @Delete
    void deleteMeeting(MeetingInfo meetingInfo);

    @Query("SELECT * FROM MEETINGINFO WHERE meetingDate = :forDate ORDER BY startTime ASC")
    LiveData<List<MeetingInfo>> getScheduledMeeting(String forDate);

}
