/*
 * MIT License
 *
 * Copyright (c) 2017 Sammy Ukavi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.sammyukavi.wbdatacatalog.activities.listcatalog;

import com.sammyukavi.wbdatacatalog.R;
import com.sammyukavi.wbdatacatalog.activities.BaseActivity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

public class ListCatalogActivity extends BaseActivity {
	
	protected ListCatalogContract.Presenter mPresenter;
	protected ListCatalogFragment mListCatalogRecordFragment;
	private Handler mHandler = new Handler();
	private boolean mSearchPerformed = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.app_name);
		getLayoutInflater().inflate(R.layout.activity_list_catalog, mFrameLayout);
		// Create fragment
		mListCatalogRecordFragment =
				(ListCatalogFragment) getSupportFragmentManager().findFragmentById(R.id
						.contentFrame);
		if (mListCatalogRecordFragment == null) {
			mListCatalogRecordFragment = ListCatalogFragment.newInstance();
		}
		if (!mListCatalogRecordFragment.isActive()) {
			addFragmentToActivity(getSupportFragmentManager(), mListCatalogRecordFragment, R.id.contentFrame);
		}
		mPresenter = new ListCatalogPresenter(mListCatalogRecordFragment);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
		MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
			
			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
				return true;
			}
			
			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				if (mSearchPerformed) {
					mListCatalogRecordFragment.reloadCatalog();
				}
				return true;
			}
		});
		SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String searchTerm) {
				if (searchTerm.length() > 0) {
					mSearchPerformed = true;
					mPresenter.setPage(1);
					mPresenter.search(searchTerm);
				}
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(final String searchTerm) {
				mHandler.removeCallbacksAndMessages(null);
				if (searchTerm.length() > 0) {
					mHandler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							mPresenter.setPage(1);
							mPresenter.search(searchTerm);
						}
					}, 1000);
				}
				return true;
			}
		});
		
		return true;
	}
	
}
