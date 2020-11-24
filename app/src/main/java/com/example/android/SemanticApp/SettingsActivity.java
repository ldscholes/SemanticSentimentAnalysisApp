package com.example.android.SemanticApp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * This Activity is based on the Empty Activity template.
 * It displays the fragment for the setting.
 */
public class SettingsActivity extends AppCompatActivity {
    public static final String
            KEY_PREF_EXAMPLE_SWITCH = "example_switch";

    /**
     * Replaces the content with the Fragment to display it.
     *
     * @param savedInstanceState Instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content,
                        new SettingsFragment()).commit();
    }
}
