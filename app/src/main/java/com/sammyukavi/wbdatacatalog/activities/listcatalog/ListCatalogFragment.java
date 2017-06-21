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

import com.sammyukavi.wbdatacatalog.activities.BaseFragment;
import com.sammyukavi.wbdatacatalog.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class ListCatalogFragment extends BaseFragment<ListCatalogContract.Presenter>
		implements ListCatalogContract.View {
	
	private ProgressBar loadingProgressBar;
	private View viewsContainer;
	
	public static ListCatalogFragment newInstance() {
		return new ListCatalogFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View mRootView = inflater.inflate(R.layout.fragment_list_catalog, container, false);
		initializeViews(mRootView);
		mPresenter.fetchCatalog();
		return mRootView;
	}
	
	private void initializeViews(View mRootView) {
		loadingProgressBar = (ProgressBar) mRootView.findViewById(R.id.loadingProgressBar);
		viewsContainer = mRootView.findViewById(R.id.viewsContainer);
	}
	
	private void showProgressBar(boolean showProgressBar) {
		if (showProgressBar) {
			loadingProgressBar.setVisibility(View.VISIBLE);
			viewsContainer.setVisibility(View.GONE);
		} else {
			loadingProgressBar.setVisibility(View.GONE);
			viewsContainer.setVisibility(View.VISIBLE);
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
}
