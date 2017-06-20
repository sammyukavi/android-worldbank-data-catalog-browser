package com.sammyukavi.wbdatacatalog;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	
	protected FragmentManager mFragmentManager;
	protected DrawerLayout drawer;
	protected FrameLayout frameLayout;
	private Toolbar toolbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acbase);
		mFragmentManager = getSupportFragmentManager();
		frameLayout = (FrameLayout) findViewById(R.id.content_frame);
		intitializeToolbar();
		intitializeNavigationDrawer();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		supportInvalidateOptionsMenu();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.basic_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public boolean onPrepareOptionsMenu(final Menu menu) {
		return true;
	}
	
	public void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
	                                  @NonNull Fragment fragment, int frameId) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.add(frameId, fragment);
		transaction.commit();
	}
	
	private void intitializeToolbar() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}
	}
	
	private void intitializeNavigationDrawer() {
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();
		
		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
	}
	
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		int selectedId = item.getItemId();
		drawer.closeDrawer(GravityCompat.START);
		openActivity(selectedId);
		return true;
	}
	
	private void openActivity(int selectedId) {
		drawer.closeDrawer(GravityCompat.START);
		/*switch (selectedId) {
			case R.id.nav_gallery:
				
				
				break;
			default:
				break;
		}*/
	}
	
	public void createSnackbar(String message) {
		
		int colorWhite = ContextCompat.getColor(getApplicationContext(), R.color.color_white);
		
		// create instance
		Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);
		
		// set action button color
		snackbar.setActionTextColor(colorWhite);
		
		// get snackbar view
		View snackbarView = snackbar.getView();
		
		// change snackbar text color
		int snackbarTextId = android.support.design.R.id.snackbar_text;
		TextView textView = (TextView) snackbarView.findViewById(snackbarTextId);
		textView.setTextColor(colorWhite);
		
		// change snackbar background
		//snackbarView.setBackgroundColor(Color.MAGENTA);
		
		//change button text
		snackbar.setActionTextColor(Color.YELLOW);
		
		snackbar.setAction(R.string.label_dismiss, new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//snackbar.dismiss();
			}
		});
		
		snackbar.show();
		
	}
	
	public void createToast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
	}
	
	public static void hideSoftKeyboard(Activity activity) {
		InputMethodManager inputMethodManager =
				(InputMethodManager) activity.getSystemService(
						Activity.INPUT_METHOD_SERVICE);
		View windowToken = activity.getCurrentFocus();
		
		if (windowToken != null) {
			inputMethodManager.hideSoftInputFromWindow(
					windowToken.getWindowToken(), 0);
		}
	}
}