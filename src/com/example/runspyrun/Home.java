package com.example.runspyrun;

/*
 *	Home.java
 *	@author Crouching Tiger Running Spy
 *
 * This class is used as home page to start the game (after login is succeeded)
 * It links to Settings, Ranking, and Profile
 */

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

/*
 * This class is the appear after user has logged in
 */
public class Home extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_home);
		
		//sets settings button
		Button setButton = (Button) findViewById(R.id.setting_button);
		setButton.setOnClickListener(new OnClickListener() {
		@Override
			public void onClick(View v) {
				startActivity(new Intent(com.example.runspyrun.Home.this,com.example.runspyrun.Setting.class));		
				}
			});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
