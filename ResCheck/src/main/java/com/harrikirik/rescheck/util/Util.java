package com.harrikirik.rescheck.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

public class Util {

	/**
	 * Return the available memory size in bytes. <br/>
	 * PS: This is usually the same as {@link #getMemoryClass(android.content.Context)} but may be also bigger is some cases, for example for rooted devices with custom builds or when android:largeHeap="true" is set in the Manifest<br/>
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
