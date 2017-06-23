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

import java.util.ArrayList;
import java.util.List;

import com.sammyukavi.wbdatacatalog.R;
import com.sammyukavi.wbdatacatalog.activities.BaseFragment;
import com.sammyukavi.wbdatacatalog.models.Catalog;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class ListCatalogFragment extends BaseFragment<ListCatalogContract.Presenter>
		implements ListCatalogContract.View {
	
	private CatalogAdapter mCatalogAdapter;
	private ProgressBar mLoadingProgressBar;
	private View mViewsContainer, mRootView, mHeader, mNoInternetMessage;
	private TextView mTotalResults;
	private Spinner mPagesSpinner;
	private ArrayAdapter<String> mPagesAdapter;
	private List<String> mPagesList = new ArrayList<>();
	private int mCurrentPage = 1, mMainCatalogPage = 1;
	
	public static ListCatalogFragment newInstance() {
		return new ListCatalogFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_list_catalog, container, false);
		initializeViews();
		mPagesList.add(getString(R.string.page, 1));
		mPresenter.setPage(mCurrentPage);
		mPresenter.setResultsPerPage(20);
		mPresenter.fetchCatalog();
		return mRootView;
	}
	
	private void initializeViews() {
		mLoadingProgressBar = (ProgressBar) mRootView.findViewById(R.id.loadingProgressBar);
		mViewsContainer = mRootView.findViewById(R.id.viewsContainer);
		mHeader = mRootView.findViewById(R.id.header);
		mNoInternetMessage = mRootView.findViewById(R.id.noInternetMessage);
		mTotalResults = (TextView) mRootView.findViewById(R.id.mTotalResults);
		mPagesSpinner = (Spinner) mRootView.findViewById(R.id.pagesSpinner);
		mPagesAdapter = new ArrayAdapter<>(getContext(), R.layout.simple_spinner_item, mPagesList);
		mPagesSpinner.setAdapter(mPagesAdapter);
		mPagesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int arg2, long arg3) {
				int index = (adapterView.getSelectedItemPosition()) + 1;
				if (mCurrentPage != index) {
					mMainCatalogPage = mCurrentPage = index;
					mPresenter.setPage(index);
					mPresenter.fetchCatalog();
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		mPagesSpinner.setSelection(mCurrentPage - 1);
	}
	
	private void showProgressBar(boolean showProgressBar) {
		if (showProgressBar) {
			mLoadingProgressBar.setVisibility(View.VISIBLE);
			mViewsContainer.setVisibility(View.GONE);
		} else {
			mLoadingProgressBar.setVisibility(View.GONE);
			mViewsContainer.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public void blockUI() {
		showProgressBar(true);
	}
	
	@Override
	public void unBlockUI() {
		showProgressBar(false);
	}
	
	@Override
	public void showMessage(String message) {
		super.showMessage(message);
	}
	
	@Override
	public void showMessage(int messageCode) {
		super.showMessage(messageCode);
	}
	
	@Override
	public void showAlert(int messageCode) {
		AlertDialog.Builder builder;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			builder = new AlertDialog.Builder(getContext());
		} else {
			builder = new AlertDialog.Builder(getContext());
		}
		builder.setTitle(getString(R.string.info))
				.setMessage(getMessageFromCode(messageCode))
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						reloadCatalog();
					}
				})
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.show();
	}
	
	@Override
	public void updateCatalogList(Catalog catalog) {
		//update results count
		mTotalResults.setText(getString(R.string.total_results, catalog.getTotal()));
		//create pagination
		//paging starts with 1 anything below that is an error, don't update current page
		if (catalog.getPage() > 0) {
			mCurrentPage = catalog.getPage();
		}
		mPagesList.clear();
		for (int i = 0; i < catalog.getPages(); i++) {
			mPagesList.add(getString(R.string.page, (i + 1)));
		}
		mPagesAdapter.notifyDataSetChanged();
		mPagesSpinner.setSelection(mCurrentPage - 1);
		//find view by id and attach adapter for the recyclerView
		RecyclerView catalogList = (RecyclerView) mRootView.findViewById(R.id.catalogList);
		catalogList.setLayoutManager(new LinearLayoutManager(getContext()));
		mCatalogAdapter = new CatalogAdapter(catalog.getDatacatalog(), getActivity());
		catalogList.setAdapter(mCatalogAdapter);
	}
	
	@Override
	public void showHeader(boolean show) {
		mHeader.setVisibility(show ? View.VISIBLE : View.GONE);
	}
	
	@Override
	public void showInternetRequired(boolean show) {
		mNoInternetMessage.setVisibility(show ? View.VISIBLE : View.GONE);
	}
	
	public void reloadCatalog() {
		mPresenter.setPage(mMainCatalogPage);
		mPresenter.fetchCatalog();
	}
	
}
