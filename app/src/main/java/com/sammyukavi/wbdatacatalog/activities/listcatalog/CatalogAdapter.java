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

import com.sammyukavi.wbdatacatalog.R;
import com.sammyukavi.wbdatacatalog.models.DataCatalog;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CatalogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	
	private ListCatalogActivity listCatalogActivity;
	private DataCatalog[] dataCatalog;
	
	public CatalogAdapter(DataCatalog[] dataCatalog, Activity activity) {
		this.dataCatalog = dataCatalog;
		this.listCatalogActivity = ((ListCatalogActivity) activity);
	}
	
	@Override
	public int getItemViewType(int position) {
		return position;
	}
	
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(listCatalogActivity).inflate(R.layout.item_data_catalog, parent, false);
		return new CatalogViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		final DataCatalog dataCatalog = this.dataCatalog[position];
		CatalogViewHolder catalogViewHolder = (CatalogViewHolder) holder;
		catalogViewHolder.datacatalogId.setText(String.valueOf(dataCatalog.getId()));
		catalogViewHolder.name.setText(dataCatalog.getMetaType()[0].getValue());
		try {
			catalogViewHolder.description.setText(android.text.Html.fromHtml(dataCatalog.getMetaType()[1].getValue())
					.toString().trim());
		}
		catch (ArrayIndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		if (listCatalogActivity.isViewingSource()) {
			catalogViewHolder.moreFromSource.setVisibility(View.GONE);
		}
		catalogViewHolder.moreFromSource.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listCatalogActivity.isViewingSource(true);
				listCatalogActivity.mPresenter.setPage(1);
				listCatalogActivity.mPresenter.fetchItemSource(String.valueOf(dataCatalog.getId()));
			}
		});
	}
	
	@Override
	public int getItemCount() {
		return dataCatalog == null ? 0 : dataCatalog.length;
	}
	
	private class CatalogViewHolder extends RecyclerView.ViewHolder {
		
		private TextView datacatalogId, name, description, moreFromSource, viewDatacatalog;
		
		public CatalogViewHolder(View view) {
			super(view);
			datacatalogId = (TextView) view.findViewById(R.id.datacatalogId);
			name = (TextView) view.findViewById(R.id.name);
			description = (TextView) view.findViewById(R.id.description);
			moreFromSource = (TextView) view.findViewById(R.id.moreFromSource);
			viewDatacatalog = (TextView) view.findViewById(R.id.viewDatacatalog);
		}
	}
}