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

import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.MessageCodes.ERROR_OCCURED;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.MessageCodes.NO_INTERNET;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.MessageCodes.NO_RESULTS;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.OPERATION_BROWSE;

import com.sammyukavi.wbdatacatalog.activities.BasePresenter;
import com.sammyukavi.wbdatacatalog.data.CatalogDataService;
import com.sammyukavi.wbdatacatalog.data.api.PagingInfo;
import com.sammyukavi.wbdatacatalog.models.Catalog;
import com.sammyukavi.wbdatacatalog.utilities.NetworkUtils;

import android.support.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCatalogPresenter extends BasePresenter implements ListCatalogContract.Presenter {
	
	private PagingInfo mPagingInfo;
	private int mResultsPerPage = 10;
	private int mPage = 1;
	private ListCatalogContract.View mListCatalogView;
	private CatalogDataService mCatalogDataService;
	private String operation = OPERATION_BROWSE;
	
	public ListCatalogPresenter(@NonNull ListCatalogContract.View view) {
		this.mListCatalogView = view;
		this.mListCatalogView.setPresenter(this);
		mCatalogDataService = new CatalogDataService();
	}
	
	public int getResultsPerPage() {
		return mResultsPerPage;
	}
	
	public void setResultsPerPage(int resultsPerPage) {
		this.mResultsPerPage = resultsPerPage;
	}
	
	public int getPage() {
		return mPage;
	}
	
	public void setPage(int page) {
		this.mPage = page;
	}
	
	@Override
	public void subscribe() {
		//left intentionally blank
	}
	
	@Override
	public void fetchCatalog() {
		mListCatalogView.blockUI();
		mPagingInfo = new PagingInfo(mPage, mResultsPerPage);
		Callback<Catalog> callback = new Callback<Catalog>() {
			
			@Override
			public void onResponse(Call<Catalog> call, Response<Catalog> response) {
				mListCatalogView.unBlockUI();
				if (response.isSuccessful()) {
					mListCatalogView.updateCatalogList(response.body());
				} else {
					mListCatalogView.showMessage(ERROR_OCCURED);
				}
			}
			
			@Override
			public void onFailure(Call<Catalog> call, Throwable t) {
				mListCatalogView.unBlockUI();
				mListCatalogView.updateCatalogList(new Catalog());
				mListCatalogView.showMessage(NO_RESULTS);
				t.printStackTrace();
			}
		};
		if (NetworkUtils.isOnline(mListCatalogView.getContext())) {
			mListCatalogView.showHeader(true);
			mListCatalogView.showInternetRequired(false);
			mCatalogDataService.getSources(mPagingInfo, callback);
		} else {
			mListCatalogView.unBlockUI();
			mListCatalogView.showHeader(false);
			mListCatalogView.showInternetRequired(true);
			mListCatalogView.showMessage(NO_INTERNET);
		}
	}
	
	@Override
	public void search(String searchTerm) {
		mListCatalogView.blockUI();
		mPagingInfo = new PagingInfo(mPage, mResultsPerPage);
		Callback<Catalog> callback = new Callback<Catalog>() {
			
			@Override
			public void onResponse(Call<Catalog> call, Response<Catalog> response) {
				mListCatalogView.unBlockUI();
				if (response.isSuccessful()) {
					mListCatalogView.updateCatalogList(response.body());
				} else {
					mListCatalogView.showMessage(ERROR_OCCURED);
				}
			}
			
			@Override
			public void onFailure(Call<Catalog> call, Throwable t) {
				mListCatalogView.unBlockUI();
				mListCatalogView.updateCatalogList(new Catalog());
				mListCatalogView.showMessage(NO_RESULTS);
				t.printStackTrace();
			}
		};
		
		if (NetworkUtils.isOnline(mListCatalogView.getContext())) {
			mListCatalogView.showHeader(true);
			mListCatalogView.showInternetRequired(false);
			mCatalogDataService.searchCatalog(searchTerm, mPagingInfo, callback);
		} else {
			mListCatalogView.unBlockUI();
			mListCatalogView.showHeader(false);
			mListCatalogView.showInternetRequired(true);
			mListCatalogView.showMessage(NO_INTERNET);
		}
		
	}
	
	@Override
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	@Override
	public String getOperation() {
		return operation;
	}
}
