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

import com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants;
import com.sammyukavi.wbdatacatalog.R;

import android.support.v4.app.Fragment;

public class BaseFragment<T extends BasePresenterContract> extends Fragment implements BaseView<T> {
	
	protected T mPresenter;
	
	@Override
	public void setPresenter(T presenter) {
		mPresenter = presenter;
	}
	
	public boolean isActive() {
		return isAdded();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (mPresenter != null) {
			mPresenter.subscribe();
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if (mPresenter != null) {
			mPresenter.unsubscribe();
		}
	}
	
	public void createSnackbar(String message) {
		((BaseActivity) getActivity()).createSnackbar(message);
	}
	
	public void showError(int errorCode) {
		
		String message = "";
		
		switch (errorCode) {
			case ApplicationConstants.ErrorCodes.SERVER_ERROR:
				message = getString(R.string.server_error_dialog_message);
				break;
			case ApplicationConstants.ErrorCodes.NO_INTERNET:
				message = getString(R.string.offline_mode_not_supported);
				break;
		}
		
		createSnackbar(message);
	}
	
	public void showError(String message) {
		createSnackbar(message);
	}
}
