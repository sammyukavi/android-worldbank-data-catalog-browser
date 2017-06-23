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

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

public class ConsoleLogger {
	
	public ConsoleLogger() {
		
	}
	
	public static void dump(String string) {
		System.out.println("=========================== START OUTPUT OF STRING ===========================");
		System.out.println(string);
		System.out.println("=========================== END OUTPUT OF STRING ===========================");
	}
	
	public static void dump(boolean variable) {
		System.out.println("=========================== START OUTPUT OF BOOLEAN ===========================");
		System.out.println(variable);
		System.out.println("=========================== END OUTPUT OF BOOLEAN ===========================");
	}
	
	public static void dump(Boolean variable) {
		System.out.println("=========================== START OUTPUT OF BOOLEAN ===========================");
		System.out.println(variable);
		System.out.println("=========================== END OUTPUT OF BOOLEAN ===========================");
	}
	
	public static void dump(int integer) {
		System.out.println("=========================== START OUTPUT OF INT ===========================");
		System.out.println(integer);
		System.out.println("=========================== END OUTPUT OF INT ===========================");
	}
	
	public static void dump(Map map) {
		System.out.println("=========================== START OUTPUT OF MAP ===========================");
		if (null != map) {
			Set keys = map.keySet();
			for (Iterator i = keys.iterator(); i.hasNext(); ) {
				String key = (String) i.next();
				String value = (String) map.get(key);
				System.out.printf("%s : %s%n", key, value);
			}
		} else {
			System.out.println(map);
		}
		System.out.println("=========================== END OUTPUT OF MAP ===========================");
	}
	
	public static void dump(Object object) {
		System.out.println("=========================== START LISTING OF OBJECT PROPERTIES ===========================");
		if (null != object) {
			for (Field field : object.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				String name = field.getName();
				Object value = null;
				try {
					value = field.get(object);
				}
				catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				//System.out.printf("Field name: %s, Field value: %s%n", name, value);
				System.out.printf("%s : %s%n", name, value);
			}
		} else {
			System.out.println(object);
		}
		System.out.println("=========================== END LISTING OF OBJECT PROPERTIES ===========================");
	}
	
	public static void dump(Object object, Object object1) {
		System.out.println("=========================== START DUMPING OF 2 OBJECTS ===========================");
		System.out.println("=========================== OBJECT 1 ===========================");
		dump(object);
		System.out.println("=========================== OBJECT 2 ===========================");
		dump(object1);
		System.out.println("=========================== END DUMPING OF 2 OBJECTS ===========================");
	}
	
	public static void dump(Object object, Object object1, Object object2) {
		System.out.println("=========================== START DUMPING OF 3 OBJECTS ===========================");
		System.out.println("=========================== OBJECT 1 ===========================");
		dump(object);
		System.out.println("=========================== OBJECT 2 ===========================");
		dump(object1);
		System.out.println("=========================== OBJECT 3 ===========================");
		dump(object2);
		System.out.println("=========================== END DUMPING OF 3 OBJECTS ===========================");
	}
	
	public static void dumpToJson(Object object) {
		System.out.println("=========================== START LISTING OF OBJECT PROPERTIES ===========================");
		Gson gson = new Gson();
		dump(gson.toJson(object));
		System.out.println("=========================== END LISTING OF OBJECT PROPERTIES ===========================");
	}
	
}