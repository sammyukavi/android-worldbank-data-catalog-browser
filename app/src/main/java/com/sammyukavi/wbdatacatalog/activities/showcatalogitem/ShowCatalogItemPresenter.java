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

import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.MessageCodes.ERROR_OCCURED;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.MessageCodes.NO_INTERNET;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.MessageCodes.SOURCE_NOT_EXIST;

import com.sammyukavi.wbdatacatalog.activities.BasePresenter;
import com.sammyukavi.wbdatacatalog.data.CatalogDataService;
import com.sammyukavi.wbdatacatalog.models.Catalog;
import com.sammyukavi.wbdatacatalog.utilities.NetworkUtils;

import android.support.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowCatalogItemPresenter extends BasePresenter implements ShowCatalogItemContract.Presenter {
	
	@NonNull
	private ShowCatalogItemContract.View findListCatalogView;
	private CatalogDataService catalogDataService;
	
	public ShowCatalogItemPresenter(@NonNull ShowCatalogItemContract.View view) {
		this.findListCatalogView = view;
		this.findListCatalogView.setPresenter(this);
		this.catalogDataService = new CatalogDataService();
	}
	
	@Override
	public void subscribe() {
		//left intentionally
	}
	
	@Override
	public void fetchItemSource(int id) {
		findListCatalogView.blockUI();
		Callback<Catalog> callback = new Callback<Catalog>() {
			
			@Override
			public void onResponse(Call<Catalog> call, Response<Catalog> response) {
				
				findListCatalogView.unBlockUI();
				if (response.isSuccessful()) {
					findListCatalogView.updateUI(response.body());
				} else {
					findListCatalogView.showMessage(ERROR_OCCURED);
				}
			}
			
			@Override
			public void onFailure(Call<Catalog> call, Throwable t) {
				findListCatalogView.unBlockUI();
				if (t.getClass().getName().equalsIgnoreCase("com.google.gson.JsonSyntaxException")) {
					//end of results, no more results
					findListCatalogView.showAlert(SOURCE_NOT_EXIST);
				}
				t.printStackTrace();
			}
		};		
		
		if (NetworkUtils.isOnline(findListCatalogView.getContext())) {
			findListCatalogView.showHeader(true);
			findListCatalogView.showInternetRequired(false);
			catalogDataService.getCatalogById(id, callback);
		} else {
			findListCatalogView.unBlockUI();
			findListCatalogView.showHeader(false);
			findListCatalogView.showInternetRequired(true);
			findListCatalogView.showMessage(NO_INTERNET);
		}
	}
	
}
