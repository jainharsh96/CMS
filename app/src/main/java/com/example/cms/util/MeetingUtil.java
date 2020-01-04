package com.example.cms.util;

import com.example.cms.Models.MeetingInfo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MeetingUtil {
    public static final String DATE_FORMATE_STRING = "dd/MM/yyyy";

    public static void addDateInMeetingList(List<MeetingInfo> infoList, String date) {
        for (MeetingInfo info : infoList) {
            info.setMeetingDate(DateConverter.stringToDate(date));
        }
    }


}
