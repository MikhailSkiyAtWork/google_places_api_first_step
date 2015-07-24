package com.example.admin.googleplaces.activities;

import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.admin.googleplaces.R;

public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_modes_key)));
        setContentView(R.layout.activity_settings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void bindPreferenceSummaryToValue(Preference preference){
        preference.setOnPreferenceChangeListener(this);

        onPreferenceChange(preference,PreferenceManager
                                    .getDefaultSharedPreferences(preference.getContext())
                                    .getString(preference.getKey(),""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        String stringValue = value.toString();

        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);


                Preference type_preference = findPreference(getResources().getString(R.string.pref_types_key));
                MultiSelectListPreference checkboxes = (MultiSelectListPreference) type_preference;
               // checkboxes.setSummary(checkboxes.getEntries()[]);


                if (stringValue.equals(getResources().getString(R.string.pref_mode_specific_search))) {
                    checkboxes.setEnabled(true);
                } else {
                    checkboxes.setEnabled(false);

                }

            }
        } else {
            preference.setSummary(stringValue);
        }

        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key){
        if (key.equals(R.string.pref_modes_key)){
            final String value = sharedPreferences.getString(key,"");
            Preference preference = findPreference(getResources().getString(R.string.pref_types_key));
            MultiSelectListPreference checkboxes = (MultiSelectListPreference) preference;
            if (value.equals(R.string.pref_mode_specific_search)) {
                checkboxes = (MultiSelectListPreference) preference;
                checkboxes.setEnabled(false);
            } else {
                checkboxes.setEnabled(true);

            }
        }
    }

}
