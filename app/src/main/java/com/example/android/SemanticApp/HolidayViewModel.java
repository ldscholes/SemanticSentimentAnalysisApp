package com.example.android.SemanticApp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

/**
 * The HolidayViewModel provides the interface between the UI and the data layer of the app,
 * represented by the Repository
 */

public class HolidayViewModel extends AndroidViewModel {

    private HolidayRepository mRepository;

    private LiveData<List<Holiday>> mAllHolidays;

    public HolidayViewModel(Application application) {
        super(application);
        mRepository = new HolidayRepository(application);
        mAllHolidays = mRepository.getAllHolidays();
    }

    LiveData<List<Holiday>> getAllHolidays() {
        return mAllHolidays;
    }

    public int getLatestHolidayId() { return mRepository.getLatestHolidayId(); }

    public void insert(Holiday holiday) {
        mRepository.insert(holiday);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void deleteHoliday(Holiday holiday) {
        mRepository.deleteHoliday(holiday);
    }

    public void update(Holiday holiday) {
        mRepository.update(holiday);
    }
}
