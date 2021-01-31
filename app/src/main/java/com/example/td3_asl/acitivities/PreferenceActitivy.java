package com.example.td3_asl.acitivities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.example.td3_asl.R;

public class PreferenceActitivy extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);

        } catch (Exception e){
            e.getMessage();
        }
    }
}