package com.example.android.SemanticApp;

/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */

@Database(entities = {Holiday.class}, version = 7,  exportSchema = false)
public abstract class HolidayRoomDatabase extends RoomDatabase {

    public abstract HolidayDao holidayDao();

    private static HolidayRoomDatabase INSTANCE;

    public static HolidayRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (HolidayRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HolidayRoomDatabase.class, "holiday_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final HolidayDao mDao;
        String [] holidays = {"I recently had the Beyond Whopper. It was good but needed some more smoke flavor if they really want it to taste like a Whopper does. Texture was pretty bang on though.", "It tastes like a whopper, but thats because the whopper isnt that much of a burger to begin with", "Molly-whopper", "That's cute. It's not happened yet. But stay tuned for the final season of the UK, it's gonna be a whopper!","“Tastes like a beef whopper!” “LIES!”", "it tastes exactly like a whopper\n" +
                "\n" +
                "garbage", "I like impossible burgers but the impossible Whopper was garbage", "Their opinion goes against mainstream veganism then, which considers it to be totally fine to get your impossible whopper cooked on the same broiler as meat burgers."};

        PopulateDbAsync(HolidayRoomDatabase db) {
            mDao = db.holidayDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
           mDao.deleteAll();

            for( int i = 0; i <= holidays.length - 1; i++) {
                Holiday holiday = new Holiday(holidays[i]);
                mDao.insert(holiday);
            }
            return null;
        }
    }
}
