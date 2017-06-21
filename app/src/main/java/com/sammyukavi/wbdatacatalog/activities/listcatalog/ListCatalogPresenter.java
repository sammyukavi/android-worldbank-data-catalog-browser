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

import com.sammyukavi.wbdatacatalog.activities.BasePresenter;
import com.sammyukavi.wbdatacatalog.data.ListCatalogDataService;
import com.sammyukavi.wbdatacatalog.data.api.PagingInfo;
import com.sammyukavi.wbdatacatalog.models.Catalog;
import com.sammyukavi.wbdatacatalog.utilities.ConsoleLogger;

import android.support.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCatalogPresenter extends BasePresenter implements ListCatalogContract.Presenter {
	
	private final PagingInfo pagingInfo;
	private int resultsPerPage = 5;
	private int startPage = 1;
	private ListCatalogContract.View listCatalogView;
	private ListCatalogDataService listCatalogDataService;
	
	public ListCatalogPresenter(@NonNull ListCatalogContract.View view) {
		this.listCatalogView = view;
		this.listCatalogView.setPresenter(this);
		listCatalogDataService = new ListCatalogDataService();
		pagingInfo = new PagingInfo(startPage, resultsPerPage);
	}
	
	@Override
	public void subscribe() {
		
	}
	
	@Override
	public void fetchCatalog() {
		
		listCatalogView.blockUI();
		
		Callback<Catalog> callback = new Callback<Catalog>() {
			
			@Override
			public void onResponse(Call<Catalog> call, Response<Catalog> response) {
				ConsoleLogger.dump(response.body().getTotal());
			}
			
			@Override
			public void onFailure(Call<Catalog> call, Throwable t) {
				t.printStackTrace();
			}
		};
		listCatalogDataService.getCatalog(pagingInfo, callback);
	}
	
	public int getResultsPerPage() {
		return resultsPerPage;
	}
	
	public void setResultsPerPage(int resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}
	
	public int getStartPage() {
		return startPage;
	}
	
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
}
