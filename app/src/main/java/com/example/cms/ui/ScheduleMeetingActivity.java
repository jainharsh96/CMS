package com.example.cms.ui;

import com.example.cms.Models.MeetingInfo;
import com.example.cms.R;
import com.example.cms.Repositorys.MeetingRepository;
import com.example.cms.database.MeetingDatabase;

import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;

public class ScheduleMeetingActivity extends AppCompatActivity {
    private TextView mSelectDateView;
    private TextView mStartTimeView;
    private TextView mEndTimeView;
    private EditText mDescriptionView;
    private MeetingInfo mMeetingInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_new_meeting);
        mSelectDateView = findViewById(R.id.selectDate);
        mStartTimeView = findViewById(R.id.startTime);
        mEndTimeView = findViewById(R.id.endTime);
        mDescriptionView = findViewById(R.id.description);
        setUpViewModel();
    }

    public void onClickBack(View view) {
        finish();
    }

    public void onClickSubmit(View view) {
        if(isMeetingSchedulable()) {
            scheduleMeeting();
            setResult(RESULT_OK);
        }
        finish();
    }

    private void scheduleMeeting() {
        saveData();
        MeetingRepository.getInstance(this).addMeeting(mMeetingInfo);
    }

    private boolean isMeetingSchedulable(){
       return true;
    }



    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + month+1 + "/" + year;
                        mSelectDateView.setText(selectedDate);
                    }
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
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
                        textView.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }

    public void onClickStartTime(View view) {
        showTimePicker((TextView) view);
    }

    public void onClickEndTime(View view) {
        showTimePicker((TextView) view);
    }

    public void onClickSelectDate(View view) {
        showDatePicker();
    }

    private void setUpViewModel() {
        ScheduleMeetingViewModel viewModel =
                ViewModelProviders.of(this).get(ScheduleMeetingViewModel.class);
        mMeetingInfo = viewModel.getData();
        if(mMeetingInfo != null){
            if(mMeetingInfo.getMeetingDate() != null){
                mSelectDateView.setText(mMeetingInfo.getMeetingDate());
            }
            if(mMeetingInfo.getStartTime() != null){
                mStartTimeView.setText(mMeetingInfo.getStartTime());
            }
            if(mMeetingInfo.getEndTime() != null){
                mEndTimeView.setText(mMeetingInfo.getEndTime());
            }
            if(mMeetingInfo.getDescription() != null){
                mDescriptionView.setText(mMeetingInfo.getDescription());
            }
        }
    }

    private void saveData(){
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
