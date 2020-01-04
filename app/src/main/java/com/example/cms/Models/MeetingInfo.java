package com.example.cms.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "meetingInfo")
public class MeetingInfo {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("start_time")
    @ColumnInfo(name = "startTime")
    private String startTime;
    @SerializedName("end_time")
    private String endTime;
    @SerializedName("description")
    private String description;
    @SerializedName("participants")
    private ArrayList<String> participants = null;
    @ColumnInfo(name = "meetingDate")
    private Date meetingDate;

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }
}
