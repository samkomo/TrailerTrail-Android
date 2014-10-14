package com.karumba.trailertrail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.karumba.controllers.ServiceHandler;

import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends ActionBarActivity  {

	EditText txtUserName, txtPassword;
	Button btnLogin, btnRegister, btnForgotPass;
	
	private ProgressDialog pDialog;
	String name, password, responce, idNo;
	int resp;
	
	//URL to post login details
	private static String url = "http://maginnovate.com/android/login.php";
	
	//JSON node names
	private static final String TAG_SUCCESS = "success";
	public static final String TAG_REGNO = "regno";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView (R.layout.activity_login);
		//link the widgets
		btnRegister = (Button) findViewById(R.id.button2);
		btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Login.this, Register.class);
				startActivity(intent);
				
			}
		});

		btnLogin = (Button) findViewById(R.id.button1);
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//calling async task to check validity of user
				txtUserName = (EditText) findViewById(R.id.editText1);
				name = txtUserName.getText().toString().trim();
				txtPassword = (EditText) findViewById(R.id.editText2);
				password = txtPassword.getText().toString().trim();
				new ValidateUser().execute();
				
				Intent intent = new Intent(Login.this, MovieList.class);
				startActivity(intent);
				
			}
		});	 
	}
	/**
	 * Async task class to get json by mmaking HTTP call
	 */

	private class ValidateUser extends AsyncTask<Void, Void, Void>{

//		private JSONArray students;


		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			
			//showing progress dialog
			pDialog = new ProgressDialog(Login.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();
		}
		
		
		@Override
		protected Void doInBackground(Void... params) {
			
			// TODO create service handler class instance
			ServiceHandler sh = new ServiceHandler();
			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("name", name));
			nameValuePairs.add(new BasicNameValuePair("password", password));
			
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.POST, nameValuePairs);
			
			//show responce from the server
			Log.d("Responce1: ", ">" + jsonStr);
			
			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);
					
					//get te success tag
					responce = jsonObj.getString(TAG_SUCCESS);
					idNo = jsonObj.getString(TAG_REGNO);
					
					//convert responce to string for comparing
					resp = Integer.parseInt(responce);
						
				} catch (JSONException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			} else {
				Toast.makeText(Login.this, "Couldn't get data from the server", Toast.LENGTH_LONG).show();

			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result){
			super.onPostExecute(result);
			
			//dismiss the progress dialog
			if (pDialog.isShowing()) {
				pDialog.dismiss();
				//if user exists then display home else display toast
			
//				Log.d("Student regno", regno);
				
				
				if (resp == 1) {
					Toast.makeText(Login.this, "Welcome " + name, Toast.LENGTH_LONG).show();
					Intent intent = new Intent(Login.this,MovieList.class);
					intent.putExtra(TAG_REGNO, idNo);
					startActivity(intent);
				} else {
					Toast.makeText(Login.this, "Wrong Username/Password combination, Please Try Again", Toast.LENGTH_LONG).show();

				}
			} 
			
					
		}
		
				
	}
	
	
	
}
