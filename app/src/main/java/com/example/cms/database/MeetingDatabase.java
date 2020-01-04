package com.example.cms.database;

import com.example.cms.Models.MeetingInfo;
import com.example.cms.util.ArrayListConverter;
import com.example.cms.util.DateConverter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NavUtils;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {MeetingInfo.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class, ArrayListConverter.class})
public abstract class MeetingDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "MeetingDatabase";
    private static MeetingDatabase mMeetingDatabase;

    public static MeetingDatabase getInstance(Context context) {
        if (mMeetingDatabase == null) {
            synchronized (LOCK) {
                mMeetingDatabase = Room.databaseBuilder(context.getApplicationContext(),
                        MeetingDatabase.class, MeetingDatabase.DATABASE_NAME).build();
            }
        }
        return mMeetingDatabase;
    }

    public abstract MeetingDao meetingDao();
}
