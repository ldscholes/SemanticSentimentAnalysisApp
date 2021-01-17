package com.example.android.SemanticApp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * This class holds the implementation code for the methods that interact with the database.
 * Using a repository allows us to group the implementation methods together,
 * and allows the HolidayViewModel to be a clean interface between the rest of the app
 * and the database.
 *
 * For insert, update and delete, and longer-running queries,
 * you must run the database interaction methods in the background.
 *
 * Typically, all you need to do to implement a database method
 * is to call it on the data access object (DAO), in the background if applicable.
 */

public class HolidayRepository {

    private HolidayDao mHolidayDao;
    private LiveData<List<Holiday>> mAllHolidays;
    private LiveData<List<Holiday>> mWhopperHolidays;
    private LiveData<List<Holiday>> mBBHolidays;
    private int mLatestHolidayId;

    HolidayRepository(Application application) {
        HolidayRoomDatabase db = HolidayRoomDatabase.getDatabase(application);
        mHolidayDao = db.holidayDao();
        mAllHolidays = mHolidayDao.getAllHolidays();
        mBBHolidays = mHolidayDao.getBBHolidays();
        mWhopperHolidays = mHolidayDao.getWhopperHolidays();

    }

    LiveData<List<Holiday>> getAllHolidays() {
        return mAllHolidays;
    }

    LiveData<List<Holiday>> getBBHolidays() {
        return mBBHolidays;
    }

    LiveData<List<Holiday>> getWhopperHolidays() {
        return mWhopperHolidays;
    }

    public void insert(Holiday holiday) {
        new insertAsyncTask(mHolidayDao).execute(holiday);
    }

    public void update(Holiday holiday)  {
        new updateHolidayAsyncTask(mHolidayDao).execute(holiday);
    }

    public void deleteAll()  {
        new deleteAllHolidaysAsyncTask(mHolidayDao).execute();
    }

    public int getLatestHolidayId()  {
        return mLatestHolidayId;
    }

    // Must run off main thread
    public void deleteHoliday(Holiday holiday) {
        new deleteHolidayAsyncTask(mHolidayDao).execute(holiday);
    }

    // Static inner classes below here to run database interactions in the background.

    /**
     * Inserts a word into the database.
     */
    private static class insertAsyncTask extends AsyncTask<Holiday, Void, Void> {

        private HolidayDao mAsyncTaskDao;

        insertAsyncTask(HolidayDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Holiday... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /**
     * Deletes all words from the database (does not delete the table).
     */
    private static class deleteAllHolidaysAsyncTask extends AsyncTask<Void, Void, Void> {
        private HolidayDao mAsyncTaskDao;

        deleteAllHolidaysAsyncTask(HolidayDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    /**
     * Get latest Holiday ID.
     */
    private class getLatestHolidayIdAsyncTask extends AsyncTask<Void, Void, Void> {
        private HolidayDao mAsyncTaskDao;

        getLatestHolidayIdAsyncTask(HolidayDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.getLatestHolidayId();
            return null;
        }

        protected void onPostExecute(int result) {
            mLatestHolidayId = result;
        }

    }

    /**
     *  Deletes a single word from the database.
     */
    private static class deleteHolidayAsyncTask extends AsyncTask<Holiday, Void, Void> {
        private HolidayDao mAsyncTaskDao;

        deleteHolidayAsyncTask(HolidayDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Holiday... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    /**
     *  Updates a word in the database.
     */
    private static class updateHolidayAsyncTask extends AsyncTask<Holiday, Void, Void> {
        private HolidayDao mAsyncTaskDao;

        updateHolidayAsyncTask(HolidayDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Holiday... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }


}
