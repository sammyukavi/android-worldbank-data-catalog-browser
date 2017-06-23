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

package com.sammyukavi.wbdatacatalog.data;

import com.sammyukavi.wbdatacatalog.data.api.PagingInfo;
import com.sammyukavi.wbdatacatalog.data.api.RestApi;
import com.sammyukavi.wbdatacatalog.data.api.RestServiceBuilder;
import com.sammyukavi.wbdatacatalog.models.Catalog;

import retrofit2.Call;
import retrofit2.Callback;

public class CatalogDataService {
	
	private final RestApi restApi;
	
	public CatalogDataService() {
		restApi = RestServiceBuilder.createService(RestApi.class);
	}
	
	public void getSources(PagingInfo pagingInfo, Callback<Catalog> callback) {
		Call<Catalog> call = restApi.getFullCatalog(pagingInfo.getPage(), pagingInfo.getResults_per_page());
		call.enqueue(callback);
	}
	
	public void searchCatalog(String searchTerm, PagingInfo pagingInfo, Callback<Catalog> callback) {
		Call<Catalog> call = restApi.searchCatalog(searchTerm, pagingInfo.getPage(), pagingInfo.getResults_per_page());
		call.enqueue(callback);
	}
	
	public void getCatalogById(int id, Callback<Catalog> callback) {
		Call<Catalog> call = restApi.getCatalogById(id);
		call.enqueue(callback);
	}
}
