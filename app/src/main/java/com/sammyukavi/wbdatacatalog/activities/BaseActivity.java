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

package com.sammyukavi.wbdatacatalog.activities;

import com.sammyukavi.wbdatacatalog.R;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public abstract class BaseActivity extends AppCompatActivity {
	
	protected FragmentManager mFragmentManager;
	protected FrameLayout mFrameLayout;
	protected Toolbar toolbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acbase);
		mFragmentManager = getSupportFragmentManager();
		mFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
		intitializeToolbar();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		supportInvalidateOptionsMenu();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public boolean onPrepareOptionsMenu(final Menu menu) {
		return true;
	}
	
	public void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.add(frameId, fragment);
		transaction.commit();
	}
	
	private void intitializeToolbar() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}
	}
	
	public void createSnackbar(String message) {
		int colorWhite = ContextCompat.getColor(getApplicationContext(), R.color.color_white);
		final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);
		snackbar.setActionTextColor(colorWhite);
		View snackBarView = snackbar.getView();
		int snackBarTextId = android.support.design.R.id.snackbar_text;
		TextView textView = (TextView) snackBarView.findViewById(snackBarTextId);
		textView.setTextColor(colorWhite);
		snackbar.setActionTextColor(Color.YELLOW);
		snackbar.setAction(R.string.label_dismiss, new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				snackbar.dismiss();
			}
		});
		snackbar.show();
	}
	
	public void createAlert(String message) {
		AlertDialog.Builder builder;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
		} else {
			builder = new AlertDialog.Builder(this);
		}
		builder.setTitle(getString(R.string.info))
				.setMessage(message)
				.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						
					}
				})
				.setIcon(android.R.drawable.ic_dialog_alert)
				.show();
	}
}