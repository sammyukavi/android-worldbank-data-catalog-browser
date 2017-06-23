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

import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.BULK_DOWNLOAD;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.CATALOG_ID;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.CITE;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.CONTACT_DETAILS;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.COVERAGE;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.DESCRIPTION;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.DETAIL_PAGE_URL;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.ECONOMY_COVERAGE;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.GRANULARITY;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.LANGUAGES;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.LAST_REVISION_DATE;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.NAME;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.NUMBER_OF_ECONOMIES;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.PERIODICITY;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.POPULARITY;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.TOPICS;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.TYPE;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.UPDATE_FREQUENCY;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.UPDATE_SCHEDULE;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.URL;

import com.sammyukavi.wbdatacatalog.R;
import com.sammyukavi.wbdatacatalog.activities.BaseFragment;
import com.sammyukavi.wbdatacatalog.models.Catalog;
import com.sammyukavi.wbdatacatalog.models.DataCatalog;
import com.sammyukavi.wbdatacatalog.models.MetaType;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ShowCatalogItemFragment extends BaseFragment<ShowCatalogItemContract.Presenter>
		implements ShowCatalogItemContract.View {
	
	private ProgressBar mLoadingProgressBar;
	private View mViewsContainer, mRootView, mHeader, mNoInternetMessage;
	private TextView mName;
	private LinearLayout mFieldsContainer;
	
	public static ShowCatalogItemFragment newInstance() {
		return new ShowCatalogItemFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_show_catalog_item, container, false);
		initializeViews();
		String sourceId = getActivity().getIntent().getExtras().getString(CATALOG_ID);
		mPresenter.fetchItemSource(Integer.parseInt(sourceId));
		return mRootView;
	}
	
	private void initializeViews() {
		mLoadingProgressBar = (ProgressBar) mRootView.findViewById(R.id.loadingProgressBar);
		mHeader = mRootView.findViewById(R.id.header);
		mNoInternetMessage = mRootView.findViewById(R.id.noInternetMessage);
		mViewsContainer = mRootView.findViewById(R.id.viewsContainer);
		mName = (TextView) mRootView.findViewById(R.id.name);
		mFieldsContainer = (LinearLayout) mRootView.findViewById(R.id.fieldsContainer);
	}
	
	private void showProgressBar(boolean showProgressBar) {
		if (showProgressBar) {
			mLoadingProgressBar.setVisibility(View.VISIBLE);
			mViewsContainer.setVisibility(View.GONE);
		} else {
			mLoadingProgressBar.setVisibility(View.GONE);
			mViewsContainer.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public void blockUI() {
		showProgressBar(true);
	}
	
	@Override
	public void unBlockUI() {
		showProgressBar(false);
	}
	
	@Override
	public void updateUI(Catalog catalog) {
		DataCatalog dataCatalog = catalog.getDatacatalog()[0];
		for (int index = 0; index < dataCatalog.getMetaType().length; index++) {
			MetaType metaType = dataCatalog.getMetaType()[index];
			if (metaType.getId().equalsIgnoreCase(NAME)) {
				mName.setText(android.text.Html.fromHtml(metaType.getValue()).toString().trim());
			} else if (metaType.getId().equalsIgnoreCase(DESCRIPTION)) {
				mFieldsContainer.addView(buildTextView(getActivity().getString(R.string.description), metaType
						.getValue()));
			} else if (metaType.getId().equalsIgnoreCase(URL)) {
				mFieldsContainer.addView(buildTextView(getActivity().getString(R.string.url), metaType
						.getValue()));
			} else if (metaType.getId().equalsIgnoreCase(TYPE)) {
				mFieldsContainer.addView(buildTextView(getActivity().getString(R.string.type), metaType
						.getValue()));
			} else if (metaType.getId().equalsIgnoreCase(LANGUAGES)) {
				mFieldsContainer.addView(buildTextView(getActivity().getString(R.string.languages_supported), metaType
						.getValue()));
			} else if (metaType.getId().equalsIgnoreCase(PERIODICITY)) {
				mFieldsContainer.addView(buildTextView(getActivity().getString(R.string.periodicity), metaType
						.getValue()));
			} else if (metaType.getId().equalsIgnoreCase(ECONOMY_COVERAGE)) {
				mFieldsContainer.addView(buildTextView(getActivity().getString(R.string.economy_coverage), metaType
						.getValue()));
			} else if (metaType.getId().equalsIgnoreCase(GRANULARITY)) {
				mFieldsContainer.addView(buildTextView(getActivity().getString(R.string.granularity), metaType
						.getValue()));
			} else if (metaType.getId().equalsIgnoreCase(NUMBER_OF_ECONOMIES)) {
				mFieldsContainer.addView(buildTextView(getActivity().getString(R.string.number_of_economies), metaType
						.getValue()));
			} else if (metaType.getId().equalsIgnoreCase(TOPICS)) {
				mFieldsContainer.addView(buildTextView(getActivity().getString(R.string.topics), metaType
						.getValue()));
			} else if (metaType.getId().equalsIgnoreCase(UPDATE_FREQUENCY)) {
				mFieldsContainer.addView(buildTextView(getActivity().getString(R.string.update_frequency), metaType
						.getValue()));
			} else if (metaType.getId().equalsIgnoreCase(UPDATE_SCHEDULE)) {
				mFieldsContainer.addView(buildTextView(getActivity().getString(R.string.update_schedule), metaType
						.getValue()));
			} else if (metaType.getId().equalsIgnoreCase(LAST_REVISION_DATE)) {
				mFieldsContainer.addView(buildTextView(getActivity().getString(R.string.last_revision_date), metaType
						.getValue()));
			} else if (metaType.getId().equalsIgnoreCase(CONTACT_DETAILS)) {
				mFieldsContainer.addView(buildTextView(getActivity().getString(R.string.contact_details), metaType
						.getValue()));
			} else if (metaType.getId().equalsIgnoreCase(BULK_DOWNLOAD)) {
				mFieldsContainer.addView(buildTextView(getActivity().getString(R.string.bulk_download), metaType
						.getValue()));
			} else if (metaType.getId().equalsIgnoreCase(CITE)) {
				mFieldsContainer.addView(buildTextView(getActivity().getString(R.string.cite), metaType
						.getValue()));
			} else if (metaType.getId().equalsIgnoreCase(DETAIL_PAGE_URL)) {
				mFieldsContainer.addView(buildTextView(getActivity().getString(R.string.detail_page_url), metaType
						.getValue()));
			} else if (metaType.getId().equalsIgnoreCase(POPULARITY)) {
				mFieldsContainer.addView(buildTextView(getActivity().getString(R.string.popularity), metaType
						.getValue()));
			} else if (metaType.getId().equalsIgnoreCase(COVERAGE)) {
				mFieldsContainer.addView(buildTextView(getActivity().getString(R.string.coverage), metaType
						.getValue()));
			}
		}
	}
	
	@Override
	public void showHeader(boolean show) {
		mHeader.setVisibility(show ? View.VISIBLE : View.GONE);
	}
	
	@Override
	public void showInternetRequired(boolean show) {
		mNoInternetMessage.setVisibility(show ? View.VISIBLE : View.GONE);
	}
	
	private View buildTextView(String title, String value) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_catalog_metatype, null);
		((TextView) view.findViewById(R.id.title)).setText(android.text.Html.fromHtml(title).toString().trim());
		((TextView) view.findViewById(R.id.value)).setText(android.text.Html.fromHtml(value).toString().trim());
		return view;
	}
}
