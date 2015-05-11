package com.example.metal.smshelper;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;


public class MainActivity extends Activity {
	public static final String PREFERENCES_NAME = "RedirectSmsHelper";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Restore preferences
		final SharedPreferences settings = getSharedPreferences(PREFERENCES_NAME, 0);
		boolean enabledSetting = settings.getBoolean("enabled", false);
		CheckBox enabled = (CheckBox) findViewById(R.id.enabled);
		enabled.setChecked(enabledSetting);
		enabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				Button save = (Button) findViewById(R.id.save);
				save.setText("Save");
			}
		});

		String urlSetting = settings.getString("url", "");
		EditText url = (EditText) findViewById(R.id.url);
		url.setText(urlSetting);
		url.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View view, int i, KeyEvent keyEvent) {
				Button save = (Button) findViewById(R.id.save);
				save.setText("Save");
				return false;
			}
		});

		Button save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SharedPreferences.Editor editor = settings.edit();

				CheckBox enabled = (CheckBox) findViewById(R.id.enabled);
				editor.putBoolean("enabled", enabled.isChecked());

				EditText url = (EditText) findViewById(R.id.url);
				editor.putString("url", url.getText().toString());

				editor.apply();

				Button save = (Button) findViewById(R.id.save);
				save.setText("Saved");
			}
		});
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
}
