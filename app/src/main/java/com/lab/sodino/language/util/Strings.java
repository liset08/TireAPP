package com.lab.sodino.language.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import com.vhxdemo.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author Sodino E-mail:sodinoopen@hotmail.com
 * @version Time：2011-7-10 下午05:23:24
 */
public class Strings {
	public static final String LANGUAGE_ENGLISH = "english";
	public static final String LANGUAGE_CHINESE = "chinese";
	/** 一个Integer对应一个string或者string[]，即&lt;Integer, Object&gt;。 */
	public static HashMap<Integer, Object> stringCustom;
	/** 标识当前显示的语系；默认值为英文。 */
	public static String language = LANGUAGE_ENGLISH;

	public static String getLanguage() {
		return language;
	}

	public static void initLanguage(Context context) {
		//stringCustom = readStringsXML(context, R.xml.english);
	}

	/** 获取指定id的字符串。 */
	public static String getString(int strId) {
		return (String) stringCustom.get(strId);
	}

	/** 获取指定id的字符串数组。 */
	public static String[] getStringArray(int strArrId) {
		return (String[]) stringCustom.get(strArrId);
	}

	private static String[] readStringArray(XmlResourceParser xmlParser)
			throws XmlPullParserException, IOException {
		String[] arr = null;
		LinkedList<String> list = new LinkedList<String>();
		String tagName, tagValue;
		while (true) {
			xmlParser.next();
			tagName = xmlParser.getName();
			if ("string-array".equals(tagName)) {
				arr = new String[list.size()];
				// 这个函数设计得好奇怪，传参和返参都一样。
				// list.toArray(arr);作用同下：
				arr = list.toArray(arr);
				break;
			}
			tagName = xmlParser.getName();
			if ((xmlParser.getEventType() == XmlResourceParser.START_TAG) && tagName.equals("item")) {
				xmlParser.next();
				tagValue = xmlParser.getText();
				list.add(tagValue);
				// Log.d("ANDROID_LAB", tagName + "=" + tagValue);
			}
		}
		return arr;
	}

	private static HashMap<Integer, Object> readStringsXML(Context context, int xmlId) {
		HashMap<Integer, Object> hashMap = new HashMap<Integer, Object>();
		Resources res = context.getResources();
		String pkg = context.getPackageName();
		XmlResourceParser xmlParser = context.getResources().getXml(xmlId);
		try {
			String tagName, attName, attValue, tagValue;
			int identifier = -1;
			int eventType = xmlParser.next();
			while (eventType != XmlResourceParser.END_DOCUMENT) {
				if (eventType == XmlResourceParser.START_DOCUMENT) {
					// Log.d("ANDROID_LAB", "[Start document]");
				} else if (eventType == XmlResourceParser.END_DOCUMENT) {
					// Log.d("ANDROID_LAB", "[End document]");
				} else if (eventType == XmlResourceParser.START_TAG) {
					tagName = xmlParser.getName();
					if ("string".equals(tagName)) {
						attName = xmlParser.getAttributeName(0);
						attValue = xmlParser.getAttributeValue(0);
						eventType = xmlParser.next();
						if (eventType == XmlResourceParser.TEXT) {
							tagValue = xmlParser.getText();
							// Log.d("ANDROID_LAB", "[Start tag]" + tagName +
							// " " + attName + "="
							// + attValue + " tagValue=" + tagValue);
							identifier = res.getIdentifier(attValue, "string", pkg);
							hashMap.put(identifier, tagValue);
							// Log.d("ANDROID_LAB",
							// Integer.toHexString(identifier) + " " + attValue
							// + "=" + tagValue);
						}
					} else if ("string-array".equals(tagName)) {
						attName = xmlParser.getAttributeName(0);
						attValue = xmlParser.getAttributeValue(0);
						identifier = res.getIdentifier(attValue, "array", pkg);
						String[] arr = readStringArray(xmlParser);
						hashMap.put(identifier, arr);
						// Log.d("ANDROID_LAB", "[Start tag]" + tagName + " " +
						// attName + "="
						// + attValue);
					}
				} else if (eventType == XmlResourceParser.END_TAG) {
					// Log.d("ANDROID_LAB", "[End tag]" + xmlParser.getName());
				} else if (eventType == XmlResourceParser.TEXT) {
					// Log.d("ANDROID_LAB", "[Text]" + xmlParser.getText());
				}
				eventType = xmlParser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hashMap;
	}


	public static void setLanguage(Context context, String language) {
		if (language.equals(Strings.language) && stringCustom != null) {
			return;
		}
		if (language.equals(Strings.LANGUAGE_CHINESE)) {
			stringCustom = readStringsXML(context, R.xml.chinese);
			Strings.language = language;
		} else if (language.equals(Strings.LANGUAGE_ENGLISH)) {
			stringCustom = readStringsXML(context, R.xml.english);
			Strings.language = language;
		} else {
			if (Strings.language.equals(Strings.LANGUAGE_ENGLISH) == false) {
				stringCustom = readStringsXML(context, R.xml.english);
				Strings.language = Strings.LANGUAGE_ENGLISH;
			}
		}
	}
}