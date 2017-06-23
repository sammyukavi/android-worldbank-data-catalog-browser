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

package com.sammyukavi.wbdatacatalog;

import java.io.IOException;

import com.sammyukavi.wbdatacatalog.models.Catalog;
import com.sammyukavi.wbdatacatalog.models.DataCatalog;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MockSuccessResponse<T> implements Call<T> {
	
	private Response response;
	
	public MockSuccessResponse(DataCatalog[] datacatalog) {
		Catalog catalog = new Catalog();
		catalog.setDatacatalog(datacatalog);
		response = Response.success(catalog);
	}
	
	public MockSuccessResponse(Object resource) {
		response = Response.success(resource);
	}
	
	@Override
	public Response execute() throws IOException {
		return null;
	}
	
	@Override
	public void enqueue(Callback<T> callback) {
		callback.onResponse(this, response);
	}
	
	@Override
	public boolean isExecuted() {
		return false;
	}
	
	@Override
	public void cancel() {
		// This method is intentionally empty
	}
	
	@Override
	public boolean isCanceled() {
		return false;
	}
	
	@Override
	public Call<T> clone() {
		return null;
	}
	
	@Override
	public Request request() {
		return null;
	}
}