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

import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.DATACATALOG;

import com.sammyukavi.wbdatacatalog.R;
import com.sammyukavi.wbdatacatalog.activities.BaseFragment;
import com.sammyukavi.wbdatacatalog.utilities.ConsoleLogger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ShowCatalogItemFragment extends BaseFragment<ShowCatalogItemContract.Presenter>
		implements ShowCatalogItemContract.View {
	
	public static ShowCatalogItemFragment newInstance() {
		return new ShowCatalogItemFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View mRootView = inflater.inflate(R.layout.fragment_show_catalog_item, container, false);
		String sourceId = getActivity().getIntent().getExtras().getString(DATACATALOG);
		ConsoleLogger.dump(sourceId);
		return mRootView;
	}
	
}
