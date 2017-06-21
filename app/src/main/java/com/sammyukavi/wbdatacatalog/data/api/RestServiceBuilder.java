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

package com.sammyukavi.wbdatacatalog.data.api;

import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.API_BASE_URL;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.UNITS.CONNECT_TIME_OUT_SECONDS;
import static com.sammyukavi.wbdatacatalog.utilities.ApplicationConstants.UNITS.READ_TIME_OUT_SECONDS;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestServiceBuilder {
	
	private static OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
	
	private static Retrofit.Builder builder;
	
	static {
		builder =
				new Retrofit.Builder()
						.baseUrl(API_BASE_URL)
						.addConverterFactory(buildGsonConverter())
						.client(getHttpClient());
	}
	
	private static OkHttpClient getHttpClient() {
		//add logging
		HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
		httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		
		okHttpClientBuilder.readTimeout(READ_TIME_OUT_SECONDS, TimeUnit.SECONDS)
				.connectTimeout(CONNECT_TIME_OUT_SECONDS, TimeUnit.SECONDS).addInterceptor(httpLoggingInterceptor)
				.build();
		return okHttpClientBuilder.build();
	}
	
	public static <S> S createService(Class<S> serviceClass) {
		OkHttpClient okHttpClient = getHttpClient();
		Retrofit retrofit = builder.client(okHttpClient).build();
		return retrofit.create(serviceClass);
	}
	
	private static GsonConverterFactory buildGsonConverter() {
		return GsonConverterFactory.create();
	}
	
}
