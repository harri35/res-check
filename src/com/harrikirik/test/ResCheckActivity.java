package com.harrikirik.test;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.TextView;

public class ResCheckActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final TextView textData = (TextView) findViewById(R.id.text_info);
		StringBuilder dataBuff = new StringBuilder();
		Display display = getWindowManager().getDefaultDisplay();
		dataBuff.append("Model: " + android.os.Build.MODEL + "\n");
		dataBuff.append("Device: " + android.os.Build.DEVICE + "\n");
		dataBuff.append("Manufacturer: " + android.os.Build.MANUFACTURER + "\n");
		dataBuff.append("Product: " + android.os.Build.PRODUCT + "\n\n");
		dataBuff.append("Display id: " + display.getDisplayId() + "\n");
		dataBuff.append("Display width: " + display.getWidth() + "\n");
		dataBuff.append("Display height: " + display.getHeight() + "\n");
		String orientation = "ORIENTATION_UNDEFINED";
		switch (getResources().getConfiguration().orientation) {
		case android.content.res.Configuration.ORIENTATION_LANDSCAPE:
			orientation = "ORIENTATION_LANDSCAPE";
			break;
		case android.content.res.Configuration.ORIENTATION_PORTRAIT:
			orientation = "ORIENTATION_PORTRAIT";
			break;
		case android.content.res.Configuration.ORIENTATION_SQUARE:
			orientation = "ORIENTATION_SQUARE";
			break;
		}
		dataBuff.append("Screen orientation: " + orientation + "\n");
		
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		dataBuff.append("Screen density: " + metrics.density + "\n");
		dataBuff.append("Screen densityDpi: " + metrics.densityDpi + "\n");
		dataBuff.append("Screen scaledDensity: " + metrics.scaledDensity + "\n");
		
		final int screenSize = (getResources().getConfiguration().screenLayout & 
			    Configuration.SCREENLAYOUT_SIZE_MASK);
		
		if (screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
			dataBuff.append("Screen size: NORMAL\n");
		} else if (screenSize == Configuration.SCREENLAYOUT_SIZE_SMALL) {
			dataBuff.append("Screen size: SMALL\n");
		} else if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
			dataBuff.append("Screen size: LARGE\n");
		} else if (screenSize == 4) {
			dataBuff.append("Screen size: XLARGE\n");
		} else if(screenSize == Configuration.SCREENLAYOUT_SIZE_UNDEFINED) {
			dataBuff.append("Screen size: UNDEFINED\n");
		}
		
		final int screenLength = (getResources().getConfiguration().screenLayout & 
			    Configuration.SCREENLAYOUT_LONG_MASK);
		
		if (screenLength == Configuration.SCREENLAYOUT_LONG_YES) {
			dataBuff.append("Screen length: LONG\n");
		} else if (screenLength == Configuration.SCREENLAYOUT_LONG_NO) {
			dataBuff.append("Screen length: NOTLONG\n");
		} else if(screenLength == Configuration.SCREENLAYOUT_LONG_UNDEFINED) {
			dataBuff.append("Screen length: UNDEFINED\n");
		}
		
		
		dataBuff.append("Device memory class: " + (getMemoryClass(this) / 1024d / 1024d) + " MB\n");
		dataBuff.append("Device allowed memory: " + (getAllowedMemory() / 1024d / 1024d) + " MB\n");
		textData.setText(dataBuff.toString());
	}
	
	/**
	 * Return the available memory size in bytes. <br/>
	 * PS: This is usually the same as {@link #getMemoryClass(Context)} but may be also bigger is some cases, for example for rooted devices with custom builds or when android:largeHeap="true" is set in the Manifest<br/>
	 * PPS: Going over this will crash the app.
	 */
	public static long getAllowedMemory() {
		final long defaultValue = 16l * 1024l * 1024l;

		final Runtime runtime = Runtime.getRuntime();
		if (runtime != null) {
			final Long value = runtime.maxMemory();
			if (value == Long.MAX_VALUE) {
				// No value available
				return defaultValue;
			} else {
				return value;
			}

		}

		return defaultValue;
	}
	
	/**
	 * Gives the device memory class, eg the max memory size in bytes that the app should use.<br/>
	 * PS: Should be equal or smaller than {@link #getAllowedMemory()}<br/>
	 * PPS: Going over this may or may not crash the app depending on the actual #getAllowedMemory() value. But it is strongly advised to stay under this limit.
	 */
	public static long getMemoryClass(final Context context) {
		final long defaultValue = 16l * 1024l * 1024l;
		
		final ActivityManager activityManager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
		if (activityManager != null) {
			long memoryClass = activityManager.getMemoryClass() * 1024l * 1024l;
			return memoryClass;
		}

		return defaultValue;
	}
}