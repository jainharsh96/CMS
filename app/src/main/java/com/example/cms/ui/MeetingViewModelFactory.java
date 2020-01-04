package com.example.cms.ui;

import com.example.cms.MeetingRepository.MeetingRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MeetingViewModelFactory implements ViewModelProvider.Factory {

    private String mForDate;
    private MeetingRepository mRepository;

    public MeetingViewModelFactory(String forDate, MeetingRepository repository) {
        mForDate = forDate;
        mRepository = repository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return modelClass.getConstructor(String.class, MeetingRepository.class).newInstance(mForDate, mRepository);
        } catch (Exception exception) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, exception);
        }
    }
}
