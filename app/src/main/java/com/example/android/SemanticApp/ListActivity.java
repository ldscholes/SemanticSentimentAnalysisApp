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

package com.example.android.SemanticApp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.SemanticApp.NewHolidayActivity;
import com.example.android.SemanticApp.Holiday;

import java.util.List;

/**
 * This class displays a list of words in a RecyclerView.
 * The words are saved in a Room database.
 * The layout for this activity also displays a FAB that
 * allows users to start the NewHotelActivity to add new words.
 * Users can delete a word by swiping it away, or delete all words
 * through the Options menu.
 * Whenever a new word is added, deleted, or updated, the RecyclerView
 * showing the list of words automatically updates.
 */
public class ListActivity extends AppCompatActivity {

    public static final int NEW_HOLIDAY_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_HOLIDAY_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA_DATA_UPDATE_HOLIDAY = "extra_holiday_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_NOTE = "extra_holiday_note_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_OPINION = "extra_holiday_opinion_to_be_updated";
    public static final String EXTRA_DATA_ID = "extra_data_id";

    private HolidayViewModel mHolidayViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up the RecyclerView.
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final HolidayListAdapter adapter = new HolidayListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up the HolidayViewModel.
        mHolidayViewModel = ViewModelProviders.of(this).get(HolidayViewModel.class);
        // Get all the words from the database
        // and associate them to the adapter.
        mHolidayViewModel.getAllHolidays().observe(this, new Observer<List<Holiday>>() {
            @Override
            public void onChanged(@Nullable final List<Holiday> holidays) {
                // Update the cached copy of the holidays in the adapter.
                adapter.setHolidays(holidays);
                for (Holiday element : holidays) {
                    System.out.println(element.getId());
                    System.out.println(element.getHoliday());
                    System.out.println(element.getNotes());
                    System.out.println(element.getOpinion());
                }
            }
        });

        // Floating action button setup
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, com.example.android.SemanticApp.NewHolidayActivity.class);
                startActivityForResult(intent, NEW_HOLIDAY_ACTIVITY_REQUEST_CODE);
            }
        });

        // Add the functionality to swipe items in the
        // RecyclerView to delete the swiped item.
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    // We are not implementing onMove() in this app.
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    // When the use swipes a word,
                    // delete that word from the database.
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Holiday myHoliday = adapter.getHolidayAtPosition(position);
                        Toast.makeText(ListActivity.this,
                                getString(R.string.delete_holiday_preamble) + " " +
                                        myHoliday.getHoliday(), Toast.LENGTH_LONG).show();

                        // Delete the word.
                        mHolidayViewModel.deleteHoliday(myHoliday);
                    }
                });
        // Attach the item touch helper to the recycler view.
        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new HolidayListAdapter.ClickListener()  {

            @Override
            public void onItemClick(View v, int position) {
                Holiday holiday = adapter.getHolidayAtPosition(position);
                launchUpdateHolidayActivity(holiday);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_menu_main, menu);
        return true;
    }

    // The options menu has a single item "Clear all data now"
    // that deletes all the entries in the database.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, as long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, R.string.clear_data_toast_text, Toast.LENGTH_LONG).show();

            // Delete the existing data.
            mHolidayViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * When the user enters a new word in the NewHolidayActivity,
     * that activity returns the result to this activity.
     * If the user entered a new word, save it in the database.

     * @param requestCode ID for the request
     * @param resultCode indicates success or failure
     * @param data The Intent sent back from the NeHolidayActivity,
     *             which includes the word that the user entered
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String holiday_data = data.getStringExtra(com.example.android.SemanticApp.NewHolidayActivity.EXTRA_REPLY);
        String holiday_note_data = data.getStringExtra(com.example.android.SemanticApp.NewHolidayActivity.EXTRA_REPLY_NOTE);
        String holiday_opinion_data = data.getStringExtra(NewHolidayActivity.EXTRA_REPLY_OPINION);

        if (requestCode == NEW_HOLIDAY_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Holiday holiday = new Holiday(holiday_data, holiday_note_data, holiday_opinion_data);
            // Save the data.
            mHolidayViewModel.insert(holiday);
        } else if (requestCode == UPDATE_HOLIDAY_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK) {
            int id = data.getIntExtra(com.example.android.SemanticApp.NewHolidayActivity.EXTRA_REPLY_ID, -1);

            if (id != -1) {
                mHolidayViewModel.update(new Holiday(id, holiday_data, holiday_note_data, holiday_opinion_data));
            } else {
                Toast.makeText(this, R.string.unable_to_update,
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(
                    this, R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }

    public void launchUpdateHolidayActivity( Holiday holiday) {
        Intent intent = new Intent(this, com.example.android.SemanticApp.NewHolidayActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_HOLIDAY, holiday.getHoliday());
        intent.putExtra(EXTRA_DATA_UPDATE_NOTE, holiday.getNotes());
        intent.putExtra(EXTRA_DATA_UPDATE_OPINION, holiday.getOpinion());
        intent.putExtra(EXTRA_DATA_ID, holiday.getId());
        startActivityForResult(intent, UPDATE_HOLIDAY_ACTIVITY_REQUEST_CODE);
    }
}