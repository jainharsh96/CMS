package com.example.cms.ui;

import com.example.cms.Models.Eligibility;
import com.example.cms.Models.MeetingInfo;
import com.example.cms.R;
import com.example.cms.Repositorys.MeetingRepository;
import com.example.cms.database.MeetingDao;
import com.example.cms.database.MeetingDatabase;
import com.example.cms.util.DateConverter;

import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;

public class ScheduleMeetingActivity extends AppCompatActivity {
    private static final String TAG_START_TIME = "start_time";
    private static final String TAG_END_TIME = "ent_time";
    private TextView mSelectDateView;
    private TextView mStartTimeView;
    private TextView mEndTimeView;
    private EditText mDescriptionView;
    private MeetingInfo mMeetingInfo;
    private String mForDate;
    private int mselectedStartHours;
    private int mSelectedStartMin;
    private boolean mIsStartTimeSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_new_meeting);
        mSelectDateView = findViewById(R.id.selectDate);
        mStartTimeView = findViewById(R.id.startTime);
        mStartTimeView.setTag(TAG_START_TIME);
        mEndTimeView = findViewById(R.id.endTime);
        mEndTimeView.setTag(TAG_END_TIME);
        mDescriptionView = findViewById(R.id.description);
        if (getIntent() != null) {
            mForDate = getIntent().getStringExtra(MeetingActivity.KEY_MEETING_FOR_DATE);
        }
        setUpViewModel();
    }

    public void onClickBack(View view) {
        finish();
    }

    public void onClickSubmit(View view) {
        if (isMeetingSchedulable()) {
            Toast.makeText(getApplicationContext(), "Slot Allotted", Toast.LENGTH_LONG).show();
            scheduleMeeting();
            setResult(MeetingActivity.RESULT_MEETING_ADDED);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Slot not available", Toast.LENGTH_LONG).show();
        }
    }

    private void scheduleMeeting() {
        saveData();
        MeetingRepository.getInstance(this).addMeeting(mMeetingInfo);
    }

    private boolean isMeetingSchedulable() {
        if(!isMandatoryFieldFilled()){
            return false;
        }
        MeetingDao dao = MeetingDatabase.getInstance(this).meetingDao();
        List<Eligibility> list = dao.isMeetingSchedulable(mStartTimeView.getText().toString(),
                mEndTimeView.getText().toString(), mForDate);
        if (list == null || list.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isMandatoryFieldFilled() {
        if (mForDate.length() > 0
                && mStartTimeView.getText().toString().length() > 0
                && mEndTimeView.getText().toString().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + month + 1 + "/" + year;
                        if (dayOfMonth < 10) {
                            selectedDate = "0" + dayOfMonth + "/" + month + 1 + "/" + year;
                        }
                        mSelectDateView.setText(selectedDate);
                    }
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker()
                .setMinDate(DateConverter.toTimestamp(DateConverter.stringToDate(mForDate)));
        datePickerDialog.show();
    }

    private void showTimePicker(final TextView textView) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (textView.getTag().equals(TAG_START_TIME)) {
                            mselectedStartHours = hourOfDay;
                            mSelectedStartMin = minute;
                            textView.setText(hourOfDay + ":" + minute);
                            mIsStartTimeSet = true;
                        } else if (textView.getTag().equals(TAG_END_TIME)) {
                            boolean eligible = hourOfDay >= mselectedStartHours;
                            if (eligible || (eligible && minute > mSelectedStartMin)) {
                                textView.setText(hourOfDay + ":" + minute);
                            } else {
                                Toast.makeText(getApplicationContext(), "Please Schedule Meeting "
                                        + "Atleast for 1 Minute", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }

    public void onClickStartTime(View view) {
        showTimePicker((TextView) view);
    }

    public void onClickEndTime(View view) {
        if (mIsStartTimeSet) {
            showTimePicker((TextView) view);
        } else {
            Toast.makeText(getApplicationContext(), "Please Select Start Time First",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onClickSelectDate(View view) {
        showDatePicker();
    }

    private void setUpViewModel() {
        ScheduleMeetingViewModel viewModel =
                ViewModelProviders.of(this).get(ScheduleMeetingViewModel.class);
        mMeetingInfo = viewModel.getData();
        if (mMeetingInfo != null) {
            if (mMeetingInfo.getMeetingDate() != null) {
                mSelectDateView.setText(mMeetingInfo.getMeetingDate());
            } else {
                mSelectDateView.setText(mForDate);
            }
            if (mMeetingInfo.getStartTime() != null) {
                String[] time = mMeetingInfo.getStartTime().split(":");
                mselectedStartHours = Integer.valueOf(time[0]);
                mSelectedStartMin = Integer.valueOf(time[1]);
                mIsStartTimeSet = true;
                mStartTimeView.setText(mMeetingInfo.getStartTime());
            }
            if (mMeetingInfo.getEndTime() != null) {
                mEndTimeView.setText(mMeetingInfo.getEndTime());
            }
            if (mMeetingInfo.getDescription() != null) {
                mDescriptionView.setText(mMeetingInfo.getDescription());
            }
        }
    }

    private void saveData() {
        mMeetingInfo.setMeetingDate(mSelectDateView.getText().toString());
        mMeetingInfo.setStartTime(mStartTimeView.getText().toString());
        mMeetingInfo.setEndTime(mEndTimeView.getText().toString());
        mMeetingInfo.setDescription(mDescriptionView.getText().toString());
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveData();
    }
}
