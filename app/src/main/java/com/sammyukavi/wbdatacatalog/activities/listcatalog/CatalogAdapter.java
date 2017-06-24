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

import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.CATALOG_ID;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.DESCRIPTION;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.StringBundles.NAME;

import com.sammyukavi.wbdatacatalog.R;
import com.sammyukavi.wbdatacatalog.activities.showcatalogitem.ShowCatalogItemActivity;
import com.sammyukavi.wbdatacatalog.models.DataCatalog;
import com.sammyukavi.wbdatacatalog.models.MetaType;
import com.sammyukavi.wbdatacatalog.utilities.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A class adapter that lists catalog items in the recyclerview
 */
public class CatalogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	
	private ListCatalogActivity mListCatalogActivity;
	private DataCatalog[] mDataCatalog;
	
	public CatalogAdapter(DataCatalog[] mDataCatalog, Activity activity) {
		this.mDataCatalog = mDataCatalog;
		this.mListCatalogActivity = ((ListCatalogActivity) activity);
	}
	
	@Override
	public int getItemViewType(int position) {
		return position;
	}
	
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(mListCatalogActivity).inflate(R.layout.item_data_catalog, parent, false);
		return new CatalogViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		final DataCatalog dataCatalog = this.mDataCatalog[position];
		final CatalogViewHolder catalogViewHolder = (CatalogViewHolder) holder;
		catalogViewHolder.mDataCatalogId.setText(String.valueOf(dataCatalog.getId()));
		for (int index = 0; index < dataCatalog.getMetaType().length; index++) {
			MetaType metaType = dataCatalog.getMetaType()[index];
			if (metaType.getId().equalsIgnoreCase(NAME)) {
				catalogViewHolder.mName.setText(metaType.getValue());
			}
			if (metaType.getId().equalsIgnoreCase(DESCRIPTION)) {
				catalogViewHolder.mSummary.setText(StringUtils.ellipsize(android.text.Html.fromHtml(metaType.getValue())
						.toString().trim()));
				catalogViewHolder.mDescription.setText(android.text.Html.fromHtml(metaType.getValue()).toString().trim());
			}
		}
		
		catalogViewHolder.mExpandCollapseButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (catalogViewHolder.mDescription.getVisibility() == View.VISIBLE) {
					catalogViewHolder.mSummary.setVisibility(View.VISIBLE);
					catalogViewHolder.mDescription.setVisibility(View.GONE);
					catalogViewHolder.mExpandCollapseButton.setImageResource(R.drawable.ic_angle_down);
				} else {
					catalogViewHolder.mSummary.setVisibility(View.GONE);
					catalogViewHolder.mDescription.setVisibility(View.VISIBLE);
					catalogViewHolder.mExpandCollapseButton.setImageResource(R.drawable.ic_angle_up);
				}
			}
		});
		
		catalogViewHolder.mMore.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mListCatalogActivity, ShowCatalogItemActivity.class);
				intent.putExtra(CATALOG_ID, String.valueOf(dataCatalog.getId()));
				mListCatalogActivity.startActivity(intent);
			}
		});
	}
	
	@Override
	public int getItemCount() {
		return mDataCatalog == null ? 0 : mDataCatalog.length;
	}
	
	private class CatalogViewHolder extends RecyclerView.ViewHolder {
		
		private TextView mDataCatalogId, mName, mSummary, mDescription, mMore;
		private ImageView mExpandCollapseButton;
		
		public CatalogViewHolder(View view) {
			super(view);
			mDataCatalogId = (TextView) view.findViewById(R.id.datacatalogId);
			mName = (TextView) view.findViewById(R.id.name);
			mSummary = (TextView) view.findViewById(R.id.summary);
			mDescription = (TextView) view.findViewById(R.id.description);
			mMore = (TextView) view.findViewById(R.id.moreFromSource);
			mExpandCollapseButton = (ImageView) view.findViewById(R.id.expandCollapseButton);
		}
	}
}