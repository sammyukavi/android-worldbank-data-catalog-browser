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

import com.sammyukavi.wbdatacatalog.activities.BasePresenter;
import com.sammyukavi.wbdatacatalog.data.ListCatalogDataService;

import android.support.annotation.NonNull;

public class ShowCatalogItemPresenter extends BasePresenter implements ShowCatalogItemContract.Presenter {
	
	@NonNull
	private ShowCatalogItemContract.View findListCatalogView;
	private int page = 0;
	private int limit = 10;
	private ListCatalogDataService listCatalogDataService;
	private boolean loading;
	
	public ShowCatalogItemPresenter(@NonNull ShowCatalogItemContract.View view, String lastQuery) {
		this.findListCatalogView = view;
		this.findListCatalogView.setPresenter(this);
		this.listCatalogDataService = new ListCatalogDataService();
	}
	
	public ShowCatalogItemPresenter(@NonNull ShowCatalogItemContract.View view) {
		this.findListCatalogView = view;
		this.findListCatalogView.setPresenter(this);
		this.listCatalogDataService = new ListCatalogDataService();
	}
	
	@Override
	public void subscribe() {
	}
	
}
