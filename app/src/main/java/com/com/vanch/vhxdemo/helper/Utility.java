package com.com.vanch.vhxdemo.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

//import java.io.FileOutputStream;
//import org.apache.http.HttpStatus;

public class Utility {
	
	public static byte[] convert2HexArray(String hexString) {
		int len = hexString.length() / 2;
		char[] chars = hexString.toCharArray();
		String[] hexes = new String[len];
		byte[] bytes = new byte[len];
		for (int i = 0, j = 0; j < len; i = i + 2, j++) {
			hexes[j] = "" + chars[i] + chars[i + 1];
			bytes[j] = (byte) Integer.parseInt(hexes[j], 16);
		}

		return bytes;
	}
	
	public static String bytes2HexString(byte[] b, int count) {
		String ret = "";
		for (int i = 0; i < count; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase(Locale.getDefault());
		}
		return ret;
	}

	public static String bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase(Locale.getDefault());
		}
		return ret;
	}
	
	public static String bytes2HexStringWithSperator(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase(Locale.getDefault());
			if ((i+1) % 4 == 0 && (i +1) != b.length)
				ret += "-";
		}
		return ret;
	}
	
	public static AlertDialog WarningAlertDialg(Activity activity, String titile, String message) {
		return new AlertDialog.Builder(activity).setTitle(titile)
				.setMessage(message)
				.setPositiveButton("OK", null)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.create();
	}
	

	public static void showDialogInNonUIThread(final Activity activity, final String title, final String message) {
		new Thread() {
			public void run() {
				Looper.prepare();
				WarningAlertDialg(activity, title, message).show();
				Looper.loop();
			}
		}.start();
	}
	
	public static void showTostInNonUIThread(final Activity activity, final String message) {
		new Thread() {
			public void run() {
				Looper.prepare();
				Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
				Looper.loop();
			}
		}.start();
	}
	
  public static byte BYTE(int i) {
    return (byte) i;
  }
  
  /**
   * check whether the str is a hex str
   *
   * @param str str
   * @param bits bits
   * @return true or false
   */
  public static boolean isHexString(String str, int bits) {
      String patten = "[abcdefABCDEF0123456789]{" + bits + "}";
      if (str.matches(patten)) {
          return true;
      } else {
          return false;
      }
  }

  public static boolean isHexString(String str) {
      String patten = "[abcdefABCDEF0123456789]{1,}";
      if (str.matches(patten)) {
          return true;
      } else {
          return false;
      }
  }

  public static boolean isNumber(String str) {
      String patten = "[-]{0,1}[0123456789]{0,}";
      return str.matches(patten);
  }
  
    public static JSONObject get(String url){
	  StringBuffer strJson = new StringBuffer();
	  HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse response;
		try {
			response = client.execute(get);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		
				String line;
				while ((line = reader.readLine()) != null) {
					strJson.append(line);
				}
			} catch (ClientProtocolException e) {
				return null;
			} catch (IllegalStateException e) {
				
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				
				e.printStackTrace();
				return null;
			}
		
		if (strJson.length() <= 0) {
			Log.i("help", "get empty json file...");
			return null;
		}
		
		Log.i("help", "get json " + strJson.toString());
	  
	  JSONObject object = null;
	try {
		object = new JSONObject(strJson.toString());
	} catch (JSONException e) {
		e.printStackTrace();
	}

	  return object;
  }
}
