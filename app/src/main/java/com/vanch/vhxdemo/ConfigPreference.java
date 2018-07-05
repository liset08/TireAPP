package com.vanch.vhxdemo;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.vhxdemo.R;

public class ConfigPreference extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.config);
	}
}
