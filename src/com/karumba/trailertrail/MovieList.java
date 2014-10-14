package com.karumba.trailertrail;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


//import com.example.schoolapp.R;









import com.karumba.controllers.GlobalVars;
import com.karumba.controllers.NavAdapter;
import com.karumba.controllers.OffersAdapter;
import com.karumba.controllers.ServiceHandler;
import com.karumba.controllers.ShopsAdapter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.MailTo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MovieList extends ActionBarActivity {

	// json array sstring from server
	public static String jsonStr;

	// offer listview
	ListView lstOffers;
	public static ShopsAdapter jsonOffersAdapter;
	JSONArray jsonOffersArray;
	JSONObject jsonObject;
	ProgressDialog pd;

	// for drawer
	public static NavAdapter jsonNavAdapter;
	JSONArray jsonNavArray;
	JSONObject jsonObjectD;

	Button btnResults, btnFee, btnNews, btnProfile;
	int mPosition = -1;
	// Array of strings storing country names
	String[] drawer_options;

	// Array of integers points to images stored in /res/drawable-ldpi/
//	int[] mIcons = new int[] { R.drawable.envelope, R.drawable.help,
//			R.drawable.logout, };

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private LinearLayout mDrawer;
	private List<HashMap<String, String>> mList;
	private SimpleAdapter mAdapter;
	final private String Drawer_menu = "drawer_menu";
	final private String Drawer_icon = "drawer_icon";
	private ActionBarDrawerToggle mDrawerToggle;
	@SuppressWarnings("unused")
	private Menu optionsMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_list);

		// initialize the listview
		lstOffers = (ListView) findViewById(R.id.listOffers);
		
		lstOffers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            	Intent intent = new Intent(MovieList.this, Register.class);

				try {
					
					GlobalVars.SELECTED_SHOP = jsonOffersAdapter.getItem(position).getString(
							"shop_id");
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				startActivity(intent);
				
				
//            	JSONObject shop = jsonAdapter.getItem(position);
//            	try {
//					String shop_id = shop.getString("shop_id");
//					Intent intent = new Intent(Shops.this, Items.class);
//					intent.putExtra("shop_id", shop_id);
//					startActivity(intent);	
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
            	
            }
        });

		// Create Drawer
		createDrawer();

		// Enabling Up navigation
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		android.app.ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		// Force show overflow menu, if this is left as the default then
		// the overflow menu items will be attached to the hardware button for
		// menu

		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			// field is of type java.lang.reflect.Field
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception ex) {
			// you can choose to display the exception
		}
	}

	private void createDrawer() {
		// Getting an array of drawer options
		drawer_options = getResources().getStringArray(R.array.drawer_options);

		// Getting a reference to the drawer listview
		mDrawerList = (ListView) findViewById(R.id.drawer_list);

		// Getting a reference to the sidebar drawer ( Title + ListView )
		mDrawer = (LinearLayout) findViewById(R.id.drawer);

		// Each row in the list stores country name, count and flag
		mList = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < drawer_options.length; i++) {
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put(Drawer_menu, drawer_options[i]);
//			hm.put(Drawer_icon, Integer.toString(mIcons[i]));
			mList.add(hm);
		}

		// Keys used in Hashmap
		String[] from = { Drawer_icon, Drawer_menu };

		// Ids of views in listview_layout
		int[] to = { R.id.drawer_icon, R.id.drawer_title };

		// Instantiating an adapter to store each items
		// R.layout.drawer_layout defines the layout of each item
		mAdapter = new SimpleAdapter(this, mList, R.layout.drawer_layout, from,
				to);

		// Getting reference to DrawerLayout
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// Creating a ToggleButton for NavigationDrawer with drawer event
		// listener
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_action_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			/** Called when drawer is closed */
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle("Malls Shopping App");
				highlightSelectedItem();
				supportInvalidateOptionsMenu();
			}

			/** Called when a drawer is opened */
			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle("Select Mall");
				supportInvalidateOptionsMenu();
			}
		};

		// Setting event listener for the drawer
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		// ItemClick event handler for the drawer items
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				

					Intent intent = new Intent(MovieList.this, Register.class);
					try {
						GlobalVars.SELECTED_MALL = jsonNavAdapter.getItem(position).getString(
								"mall_id");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					startActivity(intent);
					// finish();
					
			}
		});

		new threadGetOffers().execute();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	/*
	 * method to temporarily hold data in the form so that on screen rotation
	 * data is not lost
	 */
	public void onSaveInstanceState(Bundle state) {
		super.onSaveInstanceState(state);
	}

	@Override
	/*
	 * method to restore data back to the form after change in screen rotation
	 */
	public void onRestoreInstanceState(Bundle state) {
		super.onRestoreInstanceState(state);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		setContentView(R.layout.activity_movie_list);

		// Create Drawer
		createDrawer();

		// Enabling Up navigation
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		this.optionsMenu = menu;
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_actionbar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case android.R.id.home: 
			Toast.makeText(getApplicationContext(), "Go Home",
					Toast.LENGTH_LONG).show();
			break;
		
	//	case R.id.action_search:
			// search action
		//	break;
		case R.id.action_search:
			// location found
			Intent intent = new Intent(MovieList.this, Register.class);
			Bundle bundle = new Bundle();
			bundle.putString("jsonstring", jsonStr);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
	//	case R.id.action_refresh:
			// refresh
		//	break;
	//	case R.id.action_help:
			// help
		//	break;
	//	case R.id.action_update:
			// updatea
	//		break;
		default:
			mDrawerLayout.closeDrawer(mDrawer);
		}
		return super.onOptionsItemSelected(item);
	}

	// Highlight the selected Item : 0 to 3
	public void highlightSelectedItem() {
		int selectedItem = mDrawerList.getCheckedItemPosition();

		if (selectedItem > 2)
			mDrawerList.setItemChecked(mPosition, true);
		else
			mPosition = selectedItem;
	}

	public class threadGetOffers extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(MovieList.this);
			pd.setMessage("Please wait");
			pd.setTitle("Loading...");

			pd.show();

		}

		@Override
		protected void onPostExecute(String result) {

			lstOffers.setAdapter(jsonOffersAdapter);

			// Setting the adapter to the listView
			mDrawerList.setAdapter(jsonNavAdapter);

			pd.dismiss();

		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected String doInBackground(String... params) {
			ServiceHandler sh = new ServiceHandler();

			jsonStr = sh.makeServiceCall("http://dukalangu.co.ke/mallsfinal/home.php",
					ServiceHandler.POST);
			// String
			// jsonStrD=sh.makeServiceCall(GlobalVars.URL_ROOT+"malls.php",
			// ServiceHandler.POST);

			Log.d("Reponse: ", "->" + jsonStr);

			if (jsonStr != null) {
				try {
					jsonObject = new JSONObject(jsonStr);
					// jsonObjectD =new JSONObject(jsonStrD);

					jsonOffersArray = jsonObject.getJSONArray("offers");
					jsonNavArray = jsonObject.getJSONArray("malls");

					Log.d("Tupid stupid: ", "->" + jsonNavArray.toString());

					jsonOffersAdapter = new ShopsAdapter(MovieList.this,
							jsonOffersArray);
					jsonNavAdapter = new NavAdapter(MovieList.this,
							jsonNavArray);

				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "no data from server");
			}

			return null;

		}
	}
}
