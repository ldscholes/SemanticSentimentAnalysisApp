/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.SemanticApp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * This app demonstrates images used as buttons and a floating action button to
 * use an intent to launch a second activity. The app lets a user tap an image
 * to make a choice. The app displays a Toast showing the user’s choice,
 * and sends the choice as data with an intent to launch the second activity.
 * This version includes options in the options menu, in which some of the
 * options appear as icons in the app bar, and also includes the Up button.
 */
public class MainActivity extends AppCompatActivity {


    private SharedPreferences mPreferences;
    private String sharedPrefFile =
            "com.example.android.HolidayApp";

    // Tag for the intent extra.
    public static final String EXTRA_MESSAGE =
            "com.example.android.HolidayApp.extra.MESSAGE";

    // The order message, displayed in the Toast and sent to the new Activity.
    private String mOrderMessage;

    /**
     * Creates the content view and the toolbar.
     *
     * This method is provided in the Basic Activity template.
     *
     * @param savedInstanceState Saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

    }

    /**
     * Inflates the menu, and adds items to the action bar if it is present.
     *
     * @param menu Menu to inflate.
     * @return Returns true if the menu inflated.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    /**
     * Handles app bar item clicks.
     *
     * @param item Item clicked.
     * @return True if one of the defined items was clicked.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
/*            case R.id.action_order:
                Intent intent = new Intent(MainActivity.this,
                        NewHolidayActivity.class);
                intent.putExtra(EXTRA_MESSAGE, mOrderMessage);
                startActivity(intent);
                return true;*/
            case R.id.action_help:
                Intent intent2 = new Intent(MainActivity.this,
                        HelpActivity.class);
                startActivity(intent2);
                return true;
            case R.id.action_settings:
                Intent intent3 = new Intent(this,
                        SettingsActivity.class);
                startActivity(intent3);
                return true;
            case R.id.action_contact:
                displayToast(getString(R.string.action_contact_message));
                return true;
            default:
                // Do nothing
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Displays a Toast with the message.
     *
     * @param message Message to display.
     */
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Shows a message that the donut image was clicked.
     */
    public void showDonutOrder(View view) {
        mOrderMessage = getString(R.string.donut_order_message);
        displayToast(mOrderMessage);
    }

    /**
     * Launches help and Info activity page.
     */
    public void launchHelp(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    /**
     * Launches the add Holiday activity page.
     */
    public void launchAddHoliday(View view) {
        Intent intent = new Intent(this, NewHolidayActivity.class);
        startActivity(intent);
    }

    /**
     * Launches the list Holiday activity page.
     */
    public void launchListHoliday(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void launchListBB(View view) {
        Intent intent = new Intent(this, ListActivityBB.class);
        startActivity(intent);
    }

    public void launchListWhopper(View view) {
        Intent intent = new Intent(this, ListActivityWhopper.class);
        startActivity(intent);
    }

    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.apply();
    }


}
