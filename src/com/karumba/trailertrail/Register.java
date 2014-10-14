package com.karumba.trailertrail;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.karumba.controllers.ServiceHandler;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class Register extends ActionBarActivity {

	EditText txtUserName, txtRegNo, txtPhone, txtAddress, txtPassword, txtConfirmPass;
	Button btnRegister;
	
	private ProgressDialog pDialog;
	String name, regno, phone, address, confirmpass, password, responce;
	int resp;
	
	//URL to post login details
	private static String url = "http://maginnovate.com/android/registration.php";
	
	//JSON node names
	private static final String TAG_SUCCESS = "success";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		//get action bar
//		actionBar.setDisplayHomeAsUpEnabled(true);

		btnRegister = (Button) findViewById(R.id.button1);
		btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				txtUserName = (EditText) findViewById(R.id.editText1);
//	android.app.ActionBar actionBar = getActionBar();
//				
//		//enable up/back navigation
//					name = txtUserName.getText().toString().trim();
				txtRegNo = (EditText) findViewById(R.id.editText2);
				regno = txtRegNo.getText().toString().trim();
				txtPhone = (EditText) findViewById(R.id.editText3);
				phone = txtPhone.getText().toString().trim();
				txtAddress = (EditText) findViewById(R.id.editText4);
				address = txtAddress.getText().toString().trim();
				txtPassword = (EditText) findViewById(R.id.editText5);
				password = txtPassword.getText().toString().trim();
				txtConfirmPass = (EditText) findViewById(R.id.editText6);
				confirmpass = txtConfirmPass.getText().toString().trim();
				
				Log.d("Variables: ", name +", " + regno + ", "+address+", "+phone+", "+password+", "+confirmpass); 
				
				//validate entry
				if (name != "" && regno != "" && phone !="" && address != "" && password !="" && confirmpass !="") {
					//check if passwords match
					if (password.equals(confirmpass)) {
						//submit registration data
						new RegisterUser().execute();
					} else {
						Toast.makeText(Register.this, "Passwords do not match. Please check...", Toast.LENGTH_LONG).show();

					}
				} else {
					Toast.makeText(Register.this, "Please fill all entry fields...", Toast.LENGTH_LONG).show();
				}
				
				
			}
		});	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Async task class to get json by mmaking HTTP call
	 */

	private class RegisterUser extends AsyncTask<Void, Void, Void>{

//		private JSONArray students;


		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			
			//showing progress dialog
			pDialog = new ProgressDialog(Register.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();
		}
		
		
		@Override
		protected Void doInBackground(Void... params) {
			
			// TODO create service handler class instance
			ServiceHandler sh = new ServiceHandler();
			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
			nameValuePairs = new ArrayList<NameValuePair>(5);
			nameValuePairs.add(new BasicNameValuePair("name", name));
			nameValuePairs.add(new BasicNameValuePair("regno", regno));
			nameValuePairs.add(new BasicNameValuePair("phone", phone));
			nameValuePairs.add(new BasicNameValuePair("address", address));
			nameValuePairs.add(new BasicNameValuePair("password", password));
			
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.POST, nameValuePairs);
			
			//show responce from the server
			Log.d("Responce returns: ", ">" + jsonStr);
			
			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);
					
					//get te success tag
					responce = jsonObj.getString(TAG_SUCCESS);
					
					//convert responce to string for comparing
					resp = Integer.parseInt(responce);
						
				} catch (JSONException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			} else {
				Toast.makeText(Register.this, "Couldn't connect to the server", Toast.LENGTH_LONG).show();

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
			
				if (resp == 1) {
					Toast.makeText(Register.this, "Registration successful, Welcome:  " + name, Toast.LENGTH_LONG).show();
					Intent intent = new Intent(Register.this,MovieList.class);
					intent.putExtra("regno", regno);
					startActivity(intent);
				} else {
					Toast.makeText(Register.this, "Registration Failed! Please try Again...", Toast.LENGTH_LONG).show();

				}
			} 
			
					
		}
		
				
	}

}
