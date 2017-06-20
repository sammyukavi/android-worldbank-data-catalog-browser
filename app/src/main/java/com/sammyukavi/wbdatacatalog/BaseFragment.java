package com.sammyukavi.wbdatacatalog;

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
