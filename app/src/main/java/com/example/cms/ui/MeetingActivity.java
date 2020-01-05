package com.example.cms.ui;

import com.example.cms.Repositorys.MeetingRepository;
import com.example.cms.Models.MeetingInfo;
import com.example.cms.R;
import com.example.cms.ui.adapters.MeetingAdapter;
import com.example.cms.util.DateConverter;
import com.example.cms.util.MeetingUtil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MeetingActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_SCHEDULE_MEETING_ACTIVITY = 100;
    public static final int RESULT_OK = 101;

    private MeetingAdapter mMeetingAdapter;
    private MeetingViewModel mMeetingViewModel;
    private String mForDate;
    private TextView mForDateView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview);
        mForDateView = findViewById(R.id.date);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMeetingAdapter = new MeetingAdapter(this);
        mRecyclerView.setAdapter(mMeetingAdapter);
        setUpViewModel();
    }

    private void setUpViewModel() {
        MeetingRepository repository = MeetingRepository.getInstance(this);
        MeetingViewModelFactory factory = new MeetingViewModelFactory(getTodayDate(), repository);
        mMeetingViewModel =
                ViewModelProviders.of(this, factory).get(MeetingViewModel.class);
        mForDate = mMeetingViewModel.getForDate();
        mForDateView.setText(mForDate);
        mMeetingViewModel.getCurrentMeetingInfo().observe(this, new Observer<List<MeetingInfo>>() {
            @Override
            public void onChanged(List<MeetingInfo> meetingInfos) {
                mMeetingAdapter.setMeetings(meetingInfos);
            }
        });
    }

    private String getTodayDate() {
        return new SimpleDateFormat(MeetingUtil.DATE_FORMATE_STRING, Locale.getDefault())
                .format(new Date());
    }

    public void onClickPrevious(View view) {
        mForDate = DateConverter.previousDate(mForDate);
        mMeetingViewModel.getMeetings(mForDate);
        mForDateView.setText(mForDate);
    }

    public void onClickNext(View view) {
        mForDate = DateConverter.nextDate(mForDate);
        mMeetingViewModel.getMeetings(mForDate);
        mForDateView.setText(mForDate);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("error", "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("error", "onDestroy: ");
    }

    public void onClickScheduleMeeting(View view) {
        Intent intent = new Intent(this, ScheduleMeetingActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCHEDULE_MEETING_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCHEDULE_MEETING_ACTIVITY && resultCode == RESULT_OK) {
            mMeetingViewModel.getMeetings(mForDate);
        }
    }
}
