package com.example.cms.database;

import com.example.cms.Models.Eligibility;
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
import androidx.room.RoomWarnings;

@Dao
public interface MeetingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void scheduleMeeting(MeetingInfo meetingInfo);

    @Insert
    void addMeetings(List<MeetingInfo> infoList);

    @Delete
    void deleteMeeting(MeetingInfo meetingInfo);

    @Query("SELECT (endTime < :scheduledStartTime OR startTime > :scheduledEndTime ) as "
            + "eligible FROM meetingInfo WHERE meetingDate = :forDate AND eligible = 0")
    List<Eligibility> isMeetingSchedulable(String scheduledStartTime, String scheduledEndTime,
            String forDate);

    @Query
            ("SELECT * FROM meetingInfo WHERE "
                    + "meetingDate = "
                    + ":forDate ORDER BY "
                    + "cast ( startTime as INTEGER)")
    List<MeetingInfo> getScheduledMeeting(String forDate);

}
