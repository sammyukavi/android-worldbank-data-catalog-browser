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

import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.OPERATION_BROWSE;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.OPERATION_SEARCH;

import com.sammyukavi.wbdatacatalog.R;
import com.sammyukavi.wbdatacatalog.activities.BaseActivity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

/**
 * The main activity class. Initializes the presenter and calls the fragment to fetch and list
 * the catalog items
 */
public class ListCatalogActivity extends BaseActivity {
	
	protected ListCatalogContract.Presenter mPresenter;
	protected ListCatalogFragment mListCatalogRecordFragment;
	private Handler mHandler = new Handler();
	private boolean mSearchPerformed = false;
	private SearchView mSearchView;
	private MenuItem mSearchMenuItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.app_title);
		getLayoutInflater().inflate(R.layout.activity_list_catalog, mFrameLayout);
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
		mSearchMenuItem = menu.findItem(R.id.action_search);
		MenuItemCompat.setOnActionExpandListener(mSearchMenuItem, new MenuItemCompat.OnActionExpandListener() {
			
			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
				return true;
			}
			
			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				if (mSearchPerformed) {
					hideSoftKeyboard();
					mPresenter.setOperation(OPERATION_BROWSE);
					mListCatalogRecordFragment.reloadCatalog();
				}
				return true;
			}
		});
		mSearchView = (SearchView) MenuItemCompat.getActionView(mSearchMenuItem);
		mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String searchTerm) {
				if (searchTerm.trim().length() > 0) {
					mPresenter.setPage(1);
					performSearch(searchTerm);
				}
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(final String searchTerm) {
				mHandler.removeCallbacksAndMessages(null);
				if (searchTerm.trim().length() > 0) {
					mHandler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							mPresenter.setPage(1);
							performSearch(searchTerm);
						}
					}, 1000);
				}
				return true;
			}
		});
		return true;
	}
	
	public void performSearch() {
		performSearch(mSearchView.getQuery().toString());
	}
	
	public void performSearch(String searchTerm) {
		hideSoftKeyboard();
		mSearchPerformed = true;
		mPresenter.setOperation(OPERATION_SEARCH);
		mPresenter.search(searchTerm);
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		if (mPresenter.getOperation().equalsIgnoreCase(OPERATION_SEARCH)) {
			mPresenter.setOperation(OPERATION_BROWSE);
			mListCatalogRecordFragment.reloadCatalog();
		}
	}
}
