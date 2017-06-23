/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package com.sammyukavi.wbdatacatalog.activities.showcatalogitem;

import com.sammyukavi.wbdatacatalog.activities.BasePresenterContract;
import com.sammyukavi.wbdatacatalog.activities.BaseView;
import com.sammyukavi.wbdatacatalog.models.Catalog;

import android.content.Context;

public interface ShowCatalogItemContract {
	
	interface View extends BaseView<Presenter> {
		
		void blockUI();
		
		void unBlockUI();
		
		void updateUI(Catalog catalog);
		
		void showMessage(int messageId);
		
		void showAlert(int messageId);
		
		Context getContext();
		
		void showHeader(boolean show);
		
		void showInternetRequired(boolean show);
	}
	
	interface Presenter extends BasePresenterContract {
		
		void fetchItemSource(int id);
		
	}
	
}