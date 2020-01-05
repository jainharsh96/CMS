package com.example.cms.database;

import com.example.cms.Models.MeetingInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface MeetingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void scheduleMeeting(MeetingInfo meetingInfo);

    @Insert
    void addMeetings(List<MeetingInfo> infoList);

    @Delete
    void deleteMeeting(MeetingInfo meetingInfo);

    @Query("SELECT COUNT(*) FROM meetingInfo")
    int getRow();

    @Query("SELECT * FROM meetingInfo WHERE meetingDate = :forDate ORDER BY startTime ASC")
    LiveData<List<MeetingInfo>> getScheduledMeeting(String  forDate);

}
