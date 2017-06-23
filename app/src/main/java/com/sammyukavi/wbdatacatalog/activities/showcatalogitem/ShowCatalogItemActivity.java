package com.sammyukavi.wbdatacatalog.activities.showcatalogitem;

import com.sammyukavi.wbdatacatalog.R;
import com.sammyukavi.wbdatacatalog.activities.BaseActivity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

public class ShowCatalogItemActivity extends BaseActivity {
	
	private ShowCatalogItemFragment showCatalogItemFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.app_name);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getLayoutInflater().inflate(R.layout.activity_show_catalog_item, mFrameLayout);
		// Create fragment
		showCatalogItemFragment = (ShowCatalogItemFragment) getSupportFragmentManager().findFragmentById(
				R.id.contentFrame);
		if (showCatalogItemFragment == null) {
			showCatalogItemFragment = ShowCatalogItemFragment.newInstance();
		}
		if (!showCatalogItemFragment.isActive()) {
			addFragmentToActivity(getSupportFragmentManager(), showCatalogItemFragment, R.id.contentFrame);
		}
		new ShowCatalogItemPresenter(showCatalogItemFragment);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
		searchItem.setVisible(false);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			// Respond to the action bar's Up/Home button
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
