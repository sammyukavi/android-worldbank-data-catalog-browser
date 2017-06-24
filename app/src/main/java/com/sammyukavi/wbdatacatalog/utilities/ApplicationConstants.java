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

package com.sammyukavi.wbdatacatalog.utilities;

public class ApplicationConstants {
	
	public static String API_BASE_URL = "http://api.worldbank.org/v2/datacatalog/";
	
	public static String getBaseURL() {
		return API_BASE_URL;
	}
	
	public class StringBundles {
		
		public static final String OPERATION_SEARCH = "search";
		public static final String OPERATION_BROWSE = "browse";
		public static final String CATALOG_ID = "catalog_id";
		public static final String NAME = "name";
		public static final String DESCRIPTION = "description";
		public static final String URL = "url";
		public static final String TYPE = "type";
		public static final String LANGUAGES = "languagesupported";
		public static final String PERIODICITY = "periodicity";
		public static final String ECONOMY_COVERAGE = "economycoverage";
		public static final String GRANULARITY = "granularity";
		public static final String NUMBER_OF_ECONOMIES = "numberofeconomies";
		public static final String TOPICS = "topics";
		public static final String UPDATE_FREQUENCY = "updatefrequency";
		public static final String UPDATE_SCHEDULE = "updateschedule";
		public static final String LAST_REVISION_DATE = "lastrevisiondate";
		public static final String CONTACT_DETAILS = "contactdetails";
		public static final String BULK_DOWNLOAD = "bulkdownload";
		public static final String CITE = "cite";
		public static final String DETAIL_PAGE_URL = "detailpageurl";
		public static final String POPULARITY = "popularity";
		public static final String COVERAGE = "coverage";
		
	}
	
	public class Units {
		
		public static final int READ_TIME_OUT_SECONDS = 120;
		public static final int CONNECT_TIME_OUT_SECONDS = 120;
	}
	
	public class MessageCodes {
		
		public static final int NO_INTERNET = 100;
		public static final int ERROR_OCCURRED = 101;
		public static final int SERVER_ERROR = 102;
		public static final int SOURCE_NOT_EXIST = 103;
		public static final int NO_RESULTS = 104;
	}
}
