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
	
	public class StringBundles {
		
		public static final String NAME = "name";
		public static final String DESCRIPTION = "description";
		public static final String DATACATALOG = "datacatalog";
	}
	
	public class Units {
		
		public static final int READ_TIME_OUT_SECONDS = 120;
		public static final int CONNECT_TIME_OUT_SECONDS = 120;
	}
	
	public class MessageCodes {
		
		public static final int NO_INTERNET = 100;
		public static final int ERROR_OCCURED = 101;
		public static final int SERVER_ERROR = 102;
		public static final int SOURCE_NOT_EXIST = 103;
		public static final int NO_RESULTS = 104;
	}
}
