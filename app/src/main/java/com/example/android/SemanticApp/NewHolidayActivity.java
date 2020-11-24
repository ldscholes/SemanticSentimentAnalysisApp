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

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static com.example.android.SemanticApp.ListActivity.EXTRA_DATA_ID;
import static com.example.android.SemanticApp.ListActivity.EXTRA_DATA_UPDATE_HOLIDAY;
import static com.example.android.SemanticApp.ListActivity.EXTRA_DATA_UPDATE_OPINION;
import static com.example.android.SemanticApp.ListActivity.EXTRA_DATA_UPDATE_NOTE;

/**
 * This class displays a screen where the user enters a new word.
 * The NewHolidayActivity returns the entered word to the calling activity
 * (MainActivity), which then stores the new word and updates the list of
 * displayed words.
 */
public class NewHolidayActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.HolidayApp.REPLY";
    public static final String EXTRA_REPLY_NOTE = "com.example.android.HolidayApp.REPLY_NOTE";
    public static final String EXTRA_REPLY_OPINION = "com.example.android.HolidayApp.REPLY_OPINION";
    public static final String EXTRA_REPLY_ID = "com.android.example.HolidayApp.REPLY_ID";

    private EditText mEditHolidayView;
    private EditText mEditNoteView;
    private TextView mOpinionView;
    private RadioGroup mSetOpinionView;
    private RadioButton mSetOpinionViewButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_holiday);

        mEditHolidayView = findViewById(R.id.edit_holiday_name);
        mEditNoteView = findViewById(R.id.edit_holiday_note);
        mOpinionView = findViewById(R.id.view_opinion);
        mSetOpinionView = findViewById(R.id.opinionGroup);



        final int id = -1;

        final Bundle extras = getIntent().getExtras();
        //mEditHolidayView.requestFocus();
        //mEditHolidayView.setSelection(0);

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            String holiday = extras.getString(EXTRA_DATA_UPDATE_HOLIDAY, "");
            String note = extras.getString(EXTRA_DATA_UPDATE_NOTE, "");
            String opinion = extras.getString(EXTRA_DATA_UPDATE_OPINION, "");

            if (!holiday.isEmpty()) {
                mEditHolidayView.setText(holiday);
                mEditNoteView.setText(note);
                mOpinionView.setText(opinion);

            }
        } // Otherwise, start with empty fields.


        final Button button = findViewById(R.id.button_save);
        final Button shareButton = findViewById(R.id.button_share);

        // When the user presses the Save button, create a new Intent for the reply.
        // The reply Intent will be sent back to the calling activity.
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Create a new Intent for the reply.
                Intent replyIntent = new Intent();

                int radioId = mSetOpinionView.getCheckedRadioButtonId();
                mSetOpinionViewButton = findViewById(radioId);
                String opinion = mSetOpinionViewButton.getText().toString();

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, opinion, duration);
                toast.show();


                if (TextUtils.isEmpty(mEditHolidayView.getText())) {
                    // No Holiday name was entered, set the result accordingly.
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    // Get the new Holiday info that the user entered.
                    String holiday = mEditHolidayView.getText().toString();
                    String note = mEditNoteView.getText().toString();

                    // Put the new word in the extras for the reply Intent.
                    replyIntent.putExtra(EXTRA_REPLY, holiday);
                    replyIntent.putExtra(EXTRA_REPLY_NOTE, note);
                    replyIntent.putExtra(EXTRA_REPLY_OPINION, opinion);

                    if (extras != null && extras.containsKey(EXTRA_DATA_ID)) {
                        int id = extras.getInt(EXTRA_DATA_ID, -1);
                        if (id != -1) {
                            replyIntent.putExtra(EXTRA_REPLY_ID, id);
                        }
                    }
                    // Set the result status to indicate success.
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String holiday = mEditHolidayView.getText().toString();
                if (holiday.isEmpty()) {
                    Context context = getApplicationContext();
                    CharSequence text = "Not shared, Holiday empty";
                    int duration = Toast.LENGTH_SHORT;

                    Toast shareToast = Toast.makeText(context, text, duration);
                    shareToast.show();
                } else {
                    // Create a new Intent for the reply.
                    Intent shareIntent = new Intent();
                    shareIntent.setType("text/plain*");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "New Holiday proposal");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Do you want to go to");
                    startActivity(Intent.createChooser(shareIntent, "Share via"));

                }
                }
            });
    }



    /**
     * Process the date picker result into strings that can be displayed in
     * a Toast.
     *
     * @param year Chosen year
     * @param month Chosen month
     * @param day Chosen day
     */
    public void processStartDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (month_string +
                "/" + day_string +
                "/" + year_string);
        Log.d("Dateset:", dateMessage);
/*        Toast.makeText(this, getString(R.string.date) + dateMessage,
                Toast.LENGTH_SHORT).show();*/
    }

    public void processEndDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (month_string +
                "/" + day_string +
                "/" + year_string);
        Log.d("Dateset:", dateMessage);
/*        Toast.makeText(this, getString(R.string.date) + dateMessage,
                Toast.LENGTH_SHORT).show();*/
    }
}
