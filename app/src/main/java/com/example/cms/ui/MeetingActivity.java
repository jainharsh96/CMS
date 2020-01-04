package com.example.cms.ui;

import com.example.cms.MeetingRepository.MeetingRepository;
import com.example.cms.Models.MeetingInfo;
import com.example.cms.R;
import com.example.cms.ui.adapters.MeetingAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MeetingActivity extends AppCompatActivity {

    private MeetingAdapter mMeetingAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMeetingAdapter = new MeetingAdapter(this);
        mRecyclerView.setAdapter(mMeetingAdapter);
        setUpViewModel();
    }

    private void setUpViewModel(){
        MeetingRepository repository = MeetingRepository.getInstance(this);
        MeetingViewModelFactory factory = new MeetingViewModelFactory(getTodayDate(), repository);
        MeetingViewModel meetingViewModel =
                ViewModelProviders.of(this, factory).get(MeetingViewModel.class);
        String forDate = meetingViewModel.getForDate();
        if(forDate == null){
            forDate = getTodayDate();
        }
        meetingViewModel.getMeetings(forDate).observe(this, new Observer<List<MeetingInfo>>() {
            @Override
            public void onChanged(List<MeetingInfo> meetingInfos) {
                mMeetingAdapter.setMeetings(meetingInfos);
            }
        });
    }

    private String getTodayDate(){
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
    }


    public void onClickPrevious(View view) {

    }

    public void onClickNext(View view) {

    }
}
