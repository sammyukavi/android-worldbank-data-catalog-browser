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

import com.sammyukavi.wbdatacatalog.activities.BasePresenterContract;
import com.sammyukavi.wbdatacatalog.activities.BaseView;
import com.sammyukavi.wbdatacatalog.models.Catalog;

import android.content.Context;

public interface ListCatalogContract {
	
	interface View extends BaseView<Presenter> {
		
		void blockUI();
		
		void unBlockUI();
		
		void showMessage(int messageCode);
		
		void updateCatalogList(Catalog catalog);
				
		Context getContext();
		
		void showHeader(boolean show);
		
		void showInternetRequired(boolean show);
	}
	
	interface Presenter extends BasePresenterContract {
		
		void fetchCatalog();
				
		void setPage(int page);
		
		void setResultsPerPage(int resultsPerPage);
		
		void search(String searchTerm);
		
		void setOperation(String operation);
		
		String getOperation();
	}
	
}