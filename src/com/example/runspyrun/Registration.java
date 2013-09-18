package com.example.runspyrun;

/*
 *	Registration.java
 *	@author Crouching Tiger Running Spy
 *
 * The android java class that receive a parsed message from register.php
 * with JSONParse.java. So that the user data can be retrieved from database/phpmyadmin (temporarily from device localhost)
 * to be processed on this game
 * 
 * This class asks user to fill identification(username), email, password, and the same password like before to be confirmed
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class Registration extends Activity {
	// Progress Dialog
    private ProgressDialog pDialog;
 
    private EditText user, useremail, confreg, pass;
    JSONParser jsonParser = new JSONParser();
 
   //Fukohotspot ip
   // private static final String REG_URL = "http://192.168.43.127/webservice/register.php";
    //server
    private static final String REG_URL = "http://deco3801-002.uqcloud.net/register.php";
  
    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_registration);
		
		user = (EditText) findViewById(R.id.id_reg);
		useremail = (EditText) findViewById(R.id.email_reg);
		pass = (EditText) findViewById(R.id.pass_reg);
		confreg = (EditText) findViewById(R.id.conf_reg);
		//Sets send button
		Button send = (Button) findViewById(R.id.reg_button);
		send.setOnClickListener(new OnClickListener(){
		//Listener that opens Menu
		public void onClick(View v) {						
			// creating new product in background thread
            new CreateUser().execute();
		}
		});
		
	}
	
	  /**
     * Background Async Task to Create new product
     * */
    class CreateUser extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
    	boolean failure = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Registration.this);
            pDialog.setMessage("Registeration on progress..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
        	int success;
        	//user registration
			String username = user.getText().toString();
			String password = pass.getText().toString();
			String confpass = confreg.getText().toString();
			String email = useremail.getText().toString();
			
			Log.d("ijul", "ijulbegin");
			//check user inputs, if invalid, prompt user
			if(username.isEmpty() || password.length()==0 || confpass.length()==0 || email.isEmpty()){
				Toast.makeText(getApplicationContext(), "Fill all the fields", Toast.LENGTH_LONG).show();
			}else if(username.length()<5){
				Toast.makeText(getApplicationContext(), "Identification is too short", Toast.LENGTH_LONG).show();
			}else if(password.length()<8){
				Toast.makeText(getApplicationContext(), "Password is too short", Toast.LENGTH_LONG).show();
			}else if(!password.equals(confpass)){
				Toast.makeText(getApplicationContext(), "Password mismatch", Toast.LENGTH_LONG).show();
			}else if(email.length()<5 || email.startsWith("!@#$%^") || !(email.contains("@"))){
				Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_LONG).show();
			}else{
				//if all fields are valid, then submit to database		
				
				//startActivity(new Intent(ctrs.runspyrun.Registration.this,));	
			}
			Log.d("request!", "startingbegin");
           
 
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));
            Log.d("request!", "starting");
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(
                    REG_URL, "POST", params);
 
            // check log cat fro response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);
 
                    // closing this screen
                    finish();
                } else {
                    // failed to create user
                	Log.d("Registration Fail!", json.getString(TAG_MESSAGE));
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
            // dismiss the dialog once done
        	  pDialog.dismiss();
              if (file_url != null){
              	Toast.makeText(Registration.this, file_url, Toast.LENGTH_LONG).show();
              }
        }
 
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}

}
