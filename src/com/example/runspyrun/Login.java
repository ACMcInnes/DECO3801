package com.example.runspyrun;

/*
 *	Login.java
 *	@author Crouching Tiger Running Spy
 *
 * The android java class that receive a parsed message from login.php
 * with JSONParse.java. So that the user data can be retrieved from database/phpmyadmin (temporarily from device localhost)
 * to be processed on this game
 * 
 * This class automatically checks whether the username and password are correct and match with what have
 * registered on the database
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	 // Progress Dialog
    private ProgressDialog pDialog;
    EditText user,pass;
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    
    //php login script location:
    
    //localhost :  
    //testing on device
    private static final String LOGIN_URL = "http://deco3801-002.uqcloud.net/login.php";
    
    //Fukohotspot ip
    //private static final String LOGIN_URL = "http://192.168.43.127/webservice/login.php";
    
    //JSON element ids from repsonse of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_login);		
		
		user = (EditText) findViewById(R.id.id_log);
		pass = (EditText) findViewById(R.id.pass_log);
		
		//Sets login button
		Button login = (Button) findViewById(R.id.reg_button);
		login.setOnClickListener(new OnClickListener(){
			
			//Listener that opens Menu
			public void onClick(View v) {
				new AttemptLogin().execute();
//				Intent i = new Intent(ctrs.runspyrun.Login.this,ctrs.runspyrun.Home.class);
//            	finish();
//				startActivity(i);
			}
		
		 
		class AttemptLogin extends AsyncTask<String, String, String>
		{

			 /**
	         * Before starting background thread Show Progress Dialog
	         * */
			boolean failure = false;
			
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(Login.this);
	            pDialog.setMessage("Logging in...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	        }
			
			@Override
			protected String doInBackground(String... args) {
				// TODO Auto-generated method stub
				 // Check for success tag
	            int success;
	            String username = user.getText().toString();
	            String password = pass.getText().toString();
	            try {
	                // Building Parameters
	                List<NameValuePair> params = new ArrayList<NameValuePair>();
	                params.add(new BasicNameValuePair("username", username));
	                params.add(new BasicNameValuePair("password", password));
	 
	                Log.d("request!", "starting");
	                // getting product details by making HTTP request
	                JSONObject json = jsonParser.makeHttpRequest(
	                       LOGIN_URL, "POST", params);
	 
	                // check your log for json response
	                Log.d("Login attempt", json.toString());
	 
	                // json success tag
	                success = json.getInt(TAG_SUCCESS);
	                if (success == 1) {
	                	Log.d("Login Successful!", json.toString());
	                	Intent i = new Intent(com.example.runspyrun.Login.this,com.example.runspyrun.Home.class);
	                	finish();
	                	Log.d("try it", "after before activity");
	                	startActivity(i);
	    				Log.d("try it", "after start activity");
	                	return json.getString(TAG_MESSAGE);
	                }else{
	                	Log.d("Login Failure!", json.getString(TAG_MESSAGE));
	                	return json.getString(TAG_MESSAGE);
	                	
	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	 
	            return null;
				
			}
			/**
	         * After completing background task Dismiss the progress dialog
	         * **/
	        protected void onPostExecute(String file_url) {
	            // dismiss the dialog once product deleted
	            pDialog.dismiss();
	            if (file_url != null){
	            	Toast.makeText(Login.this, file_url, Toast.LENGTH_LONG).show();
	            }
	 
	        }
			
		}
		});
		//Creates Text that links back to Registration
		TextView reg = (TextView) findViewById(R.id.register_text);
		reg.setOnClickListener(new OnClickListener() {
		@Override
			public void onClick(View v) {
				startActivity(new Intent(com.example.runspyrun.Login.this,com.example.runspyrun.Registration.class));		
				}
			});
		
		//Creates Text that trigger to send an email
		TextView forgot = (TextView) findViewById(R.id.passwordrecover_text);
		forgot.setOnClickListener(new OnClickListener() {
		@Override
			public void onClick(View v) {
				//change destination class !!
				startActivity(new Intent(com.example.runspyrun.Login.this,com.example.runspyrun.SendMail.class));		
				}
			});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
