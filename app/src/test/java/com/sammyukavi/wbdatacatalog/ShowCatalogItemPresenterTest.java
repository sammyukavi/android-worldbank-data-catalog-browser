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

package com.sammyukavi.wbdatacatalog;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.sammyukavi.wbdatacatalog.activities.listcatalog.ListCatalogContract;
import com.sammyukavi.wbdatacatalog.activities.listcatalog.ListCatalogPresenter;
import com.sammyukavi.wbdatacatalog.data.CatalogDataService;
import com.sammyukavi.wbdatacatalog.data.api.RestApi;
import com.sammyukavi.wbdatacatalog.data.api.RestServiceBuilder;
import com.sammyukavi.wbdatacatalog.models.Catalog;
import com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants;
import com.sammyukavi.wbdatacatalog.utilities.NetworkUtils;

import android.content.Context;
import android.test.mock.MockContext;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ApplicationConstants.class, NetworkUtils.class, RestServiceBuilder.class })
@PowerMockIgnore("javax.net.ssl.*")

public class ShowCatalogItemPresenterTest extends UnitTestBase {
	
	@Mock
	private RestApi restApi;
	@Mock
	private ListCatalogContract.View view;
	@Mock
	private CatalogDataService catalogDataService;
	
	private ListCatalogPresenter presenter;
	private Context context;
	
	@Before
	public void setUp() {
		presenter = new ListCatalogPresenter(view);
		context = new MockContext();
		verify(view).setPresenter(presenter);
	}
	
	@Test
	public void shouldFetchOneItem_OK() {
		presenter.fetchCatalog();
		verify(view).blockUI();
		when(restApi.getCatalogById(1)).thenReturn(mockSuccessCall(new Catalog()));
	}
	
	@Test
	public void shouldFetchOnlineCatalog_Error() {
		presenter.fetchCatalog();
		verify(view).blockUI();
		when(restApi.getCatalogById(-1)).thenReturn(this.<Catalog>mockFailureCall());
	}
}
