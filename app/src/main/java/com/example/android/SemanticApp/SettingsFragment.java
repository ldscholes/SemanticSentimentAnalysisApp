package com.example.android.SemanticApp;


import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

/**
 * A subclass of PreferenceFragmentCompat to supply preferences in a
 * Fragment for the SettingsActivity to display.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    /**
     * Called during onCreate(Bundle) to supply the preferences for this
     * fragment. This calls setPreferenceFromResource to get the preferences
     * from the XML file.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     * @param rootKey            If non-null, this preference fragment
     *                           should be rooted with this key.
     */
    @Override
    public void onCreatePreferences(Bundle savedInstanceState,
                                    String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

}
