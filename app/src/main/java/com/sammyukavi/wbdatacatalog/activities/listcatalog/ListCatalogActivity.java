package com.sammyukavi.wbdatacatalog.activities.listcatalog;

import com.sammyukavi.wbdatacatalog.BaseActivity;
import com.sammyukavi.wbdatacatalog.R;

import android.os.Bundle;

public class ListCatalogActivity extends BaseActivity {
	
	public ListCatalogContract.Presenter findPatientPresenter;
	ListCatalogFragment findPatientRecordFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getLayoutInflater().inflate(R.layout.activity_list_catalog, frameLayout);
		setTitle(R.string.app_name);
		
		// Create fragment
		findPatientRecordFragment =
				(ListCatalogFragment) getSupportFragmentManager().findFragmentById(R.id
						.findPatientContentFrame);
		if (findPatientRecordFragment == null) {
			findPatientRecordFragment = ListCatalogFragment.newInstance();
		}
		if (!findPatientRecordFragment.isActive()) {
			addFragmentToActivity(getSupportFragmentManager(), findPatientRecordFragment, R.id.findPatientContentFrame);
		}
		
		findPatientPresenter = new ListCatalogPresenter(findPatientRecordFragment);
		findPatientPresenter = new ListCatalogPresenter(findPatientRecordFragment);
	}
	
	
}
