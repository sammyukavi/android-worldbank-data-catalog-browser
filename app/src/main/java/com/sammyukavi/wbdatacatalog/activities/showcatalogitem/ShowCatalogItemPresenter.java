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

import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.MessageCodes.ERROR_OCCURRED;
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

/**
 * The presenter class. Fetches the catalog item and passes it to the fragment for rendering
 */
public class ShowCatalogItemPresenter extends BasePresenter implements ShowCatalogItemContract.Presenter {
	
	@NonNull
	private ShowCatalogItemContract.View mShowCatalogItemView;
	private CatalogDataService mCatalogDataService;
	
	public ShowCatalogItemPresenter(@NonNull ShowCatalogItemContract.View view) {
		this.mShowCatalogItemView = view;
		this.mShowCatalogItemView.setPresenter(this);
		this.mCatalogDataService = new CatalogDataService();
	}
	
	@Override
	public void subscribe() {
		//left intentionally
	}
	
	@Override
	public void fetchItemSource(int id) {
		mShowCatalogItemView.blockUI();
		Callback<Catalog> callback = new Callback<Catalog>() {
			
			@Override
			public void onResponse(Call<Catalog> call, Response<Catalog> response) {
				
				mShowCatalogItemView.unBlockUI();
				if (response.isSuccessful()) {
					mShowCatalogItemView.updateUI(response.body());
				} else {
					mShowCatalogItemView.showMessage(ERROR_OCCURRED);
				}
			}
			
			@Override
			public void onFailure(Call<Catalog> call, Throwable t) {
				mShowCatalogItemView.unBlockUI();
				if (t.getClass().getName().equalsIgnoreCase("com.google.gson.JsonSyntaxException")) {
					mShowCatalogItemView.showAlert(SOURCE_NOT_EXIST);
				}
				t.printStackTrace();
			}
		};		
		
		if (NetworkUtils.isOnline(mShowCatalogItemView.getContext())) {
			mShowCatalogItemView.showHeader(true);
			mShowCatalogItemView.showInternetRequired(false);
			mCatalogDataService.getCatalogById(id, callback);
		} else {
			mShowCatalogItemView.unBlockUI();
			mShowCatalogItemView.showHeader(false);
			mShowCatalogItemView.showInternetRequired(true);
			mShowCatalogItemView.showMessage(NO_INTERNET);
		}
	}
	
}
