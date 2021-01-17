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

@Database(entities = {Holiday.class}, version = 8,  exportSchema = false)
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
        String [] Whopperholidays = {"I recently had the Beyond Whopper. It was good but needed some more smoke flavor if they really want it to taste like a Whopper does. Texture was pretty bang on though.", "It tastes like a whopper, but thats because the whopper isnt that much of a burger to begin with", "Molly-whopper", "That's cute. It's not happened yet. But stay tuned for the final season of the UK, it's gonna be a whopper!","“Tastes like a beef whopper!” “LIES!”", "it tastes exactly like a whopper\n" +
                "\n" +
                "garbage", "I like impossible burgers but the impossible Whopper was garbage", "Their opinion goes against mainstream veganism then, which considers it to be totally fine to get your impossible whopper cooked on the same broiler as meat burgers."};


        String [] BBholidays = {"You'd be better off getting it from somewhere like Patrolbase which, ironically, has it cheaper than BBGuns4Less. Just my unsolicited 2 pence, but I'd also suggest avoiding anything that refers to airsoft guns as BB guns (BBGuns4Less, JustBBGuns, etc.) as a lot of the stuff they sell is really not good unless you just want to run around in your back garden with some mates. For serious airsofting, you'll want something that can stand up to some wear and tear.",
                "JustBBGuns, BBGuns4less and OnlyBBGuns (Who I swear are just an alternative IP for BBGuns4less) aren't best recommended.",
                "landwarrior, fire-support, actionhobbies, redwolfairsoft uk,airsoft world, extreme airsoft, justbbguns, bbguns4less & onlybbguns just a few in the UK",
                "That's a standard price, nothing special. The JG, Specna arms and Umarex guns are all that same price, I'd pick up one of those over the cyma.That's a standard price, nothing special. The JG, Specna arms and Umarex guns are all that same price, I'd pick up one of those over the cyma. https://www.patrolbase.co.uk/airsoft-ar36-variants bbguns4less isn't the best of sites either, do not believe a single review or opinion they post.",
                "Don't buy off bbguns4less, they're just a cheap tack website most of the time.",
                "if your based in the uk one of the places i use as a base for a bb gun is BBguns4less they have a breakers yard i get the odd few guns and turn them into other ones for cosplays :) half time they dont fire or anything so its great for what we use them for",
                "I've just ordered a mystery box from bbguns4less, hoping it has something decent in apparently they are pretty good value, couldnt make my mind up on one so i figured this would be a fun way to go.",
                "avoid JustBBguns avoid BBguns4less you'll be fine",
                "Shops to check out: Patrol Base, ProAirsoftSupplies, ZeroOneAirsoft and Airsoft World Shops to avoid: JustBBGuns, BBGuns4Less, OnlyBBGuns and those faggots at WolfArmouries",
                "BBGUNS4LESS do it",
                "Avoid those ''onlybbguns'' bbguns4less'' ''justbbguns'' type sites.Find something better on actionhobbies,geniestuff or airsoftworld."};

        String [] BBclass = {"Negative","Negative","Neutral","Negative","Negative","Positive","Positive", "Negative", "Negative", "Neutral", "Negative"};


                PopulateDbAsync(HolidayRoomDatabase db) {
            mDao = db.holidayDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
           mDao.deleteAll();

            for( int i = 0; i <= Whopperholidays.length - 1; i++) {
                Holiday holiday = new Holiday(Whopperholidays[i], "Whopper");
                mDao.insert(holiday);
            }

            for( int i = 0; i <= BBholidays.length - 1; i++) {
                Holiday holiday = new Holiday(BBholidays[i], BBclass[i],"BBGUNS4LESS");
                mDao.insert(holiday);
            }


            return null;
        }
    }
}
