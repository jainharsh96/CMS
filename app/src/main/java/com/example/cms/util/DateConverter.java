package com.example.cms.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.room.TypeConverter;

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    public static Date stringToDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat(MeetingUtil.DATE_FORMATE_STRING);
        Date date1 = null;
        try {
            date1 = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static String nextDate(String forDate) {
        SimpleDateFormat format = new SimpleDateFormat(MeetingUtil.DATE_FORMATE_STRING);
        Calendar c = Calendar.getInstance();
        c.setTime(stringToDate(forDate));
        c.add(Calendar.DATE, 1);
        return format.format(c.getTime());
    }

    public static String previousDate(String forDate) {
        SimpleDateFormat format = new SimpleDateFormat(MeetingUtil.DATE_FORMATE_STRING);
        Calendar c = Calendar.getInstance();
        c.setTime(stringToDate(forDate));
        c.add(Calendar.DATE, -1);
        return format.format(c.getTime());
    }
}
