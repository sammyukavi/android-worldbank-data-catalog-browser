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
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.MessageCodes.NO_RESULTS;

import com.sammyukavi.wbdatacatalog.activities.BasePresenter;
import com.sammyukavi.wbdatacatalog.data.CatalogDataService;
import com.sammyukavi.wbdatacatalog.data.api.PagingInfo;
import com.sammyukavi.wbdatacatalog.models.Catalog;

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
	
	public ListCatalogPresenter(@NonNull ListCatalogContract.View view) {
		this.mListCatalogView = view;
		this.mListCatalogView.setPresenter(this);
		mCatalogDataService = new CatalogDataService();
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
				mListCatalogView.showSourceInHeader(false);
				if (response.isSuccessful()) {
					mListCatalogView.updateCatalogList(response.body());
				} else {
					mListCatalogView.showMessage(ERROR_OCCURED);
				}
			}
			
			@Override
			public void onFailure(Call<Catalog> call, Throwable t) {
				mListCatalogView.unBlockUI();
				mListCatalogView.showSourceInHeader(false);
				mListCatalogView.updateCatalogList(new Catalog());
				mListCatalogView.showMessage(NO_RESULTS);
				t.printStackTrace();
			}
		};
		mCatalogDataService.getSources(mPagingInfo, callback);
	}
	
	public int getResultsPerPage() {
		return mResultsPerPage;
	}
	
	public void setResultsPerPage(int resultsPerPage) {
		this.mResultsPerPage = resultsPerPage;
	}
	
	@Override
	public void search(String searchTerm) {
		mListCatalogView.blockUI();
		mPagingInfo = new PagingInfo(mPage, mResultsPerPage);
		Callback<Catalog> callback = new Callback<Catalog>() {
			
			@Override
			public void onResponse(Call<Catalog> call, Response<Catalog> response) {
				mListCatalogView.unBlockUI();
				mListCatalogView.showSourceInHeader(false);
				if (response.isSuccessful()) {
					mListCatalogView.updateCatalogList(response.body());
				} else {
					mListCatalogView.showMessage(ERROR_OCCURED);
				}
			}
			
			@Override
			public void onFailure(Call<Catalog> call, Throwable t) {
				mListCatalogView.unBlockUI();
				mListCatalogView.showSourceInHeader(false);
				mListCatalogView.updateCatalogList(new Catalog());
				mListCatalogView.showMessage(NO_RESULTS);
				t.printStackTrace();
			}
		};
		mCatalogDataService.searchCatalog(searchTerm, mPagingInfo, callback);
	}
	
	public int getPage() {
		return mPage;
	}
	
	public void setPage(int page) {
		this.mPage = page;
	}
}
