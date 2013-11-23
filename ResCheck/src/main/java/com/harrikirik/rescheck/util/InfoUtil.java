package com.harrikirik.rescheck.util;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Display;
import com.harrikirik.rescheck.R;
import com.harrikirik.rescheck.dto.BaseInfoObject;
import com.harrikirik.rescheck.dto.InfoHeader;
import com.harrikirik.rescheck.dto.InfoImageItem;
import com.harrikirik.rescheck.dto.InfoItem;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Harri Kirik, harri35@gmail.com
 */
public class InfoUtil {

    public static ArrayList<BaseInfoObject> getFullInfo(final Activity activity) {
        final ArrayList<BaseInfoObject> items = new ArrayList<BaseInfoObject>();

        items.addAll(getGeneralDeviceInfo(activity));
        items.addAll(getDisplayInfo(activity));
        items.addAll(getScreenInfo(activity));
        items.addAll(getMemoryInfo(activity));

        return items;
    }

    private static ArrayList<BaseInfoObject> getGeneralDeviceInfo(final Activity activity) {
        ArrayList<BaseInfoObject> items = new ArrayList<BaseInfoObject>();
        addHeader(items, "Device general info");
        addItem(items, "Model", android.os.Build.MODEL);
        addItem(items, "Device", android.os.Build.DEVICE);
        addItem(items, "Manufacturer", android.os.Build.MANUFACTURER);
        addItem(items, "Product", android.os.Build.PRODUCT);

        return items;
    }

    private static ArrayList<BaseInfoObject> getDisplayInfo(final Activity activity) {
        ArrayList<BaseInfoObject> items = new ArrayList<BaseInfoObject>();
        Display display = activity.getWindowManager().getDefaultDisplay();

        addHeader(items, "Device display");
        addItem(items, "Display id", String.valueOf(display.getDisplayId()));
        addItem(items, "Display width", String.valueOf(display.getWidth()));
        addItem(items, "Display height", String.valueOf(display.getHeight()));


        String orientation = "ORIENTATION_UNDEFINED";
        switch (activity.getResources().getConfiguration().orientation) {
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
        addItem(items, "Display orientation", orientation);

        return items;
    }

    public static ArrayList<BaseInfoObject> getScreenInfo(final Activity activity) {
        ArrayList<BaseInfoObject> items = new ArrayList<BaseInfoObject>();
        Display display = activity.getWindowManager().getDefaultDisplay();

        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        addHeader(items, "Device screen");
        items.add(new InfoImageItem("Screen density", R.drawable.icon_dpi));
        addItem(items, "Screen density value DPI", String.valueOf(metrics.densityDpi));
        addItem(items, "Screen density value ", String.valueOf(metrics.density));
        addItem(items, "Screen scaled density", String.valueOf(metrics.scaledDensity));

        final int screenSize = (activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK);

        String sizeValue = null;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            sizeValue = "NORMAL";
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            sizeValue = "SMALL";
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            sizeValue = "LARGE";
        } else if (screenSize == 4) {
            sizeValue = "XLARGE";
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_UNDEFINED) {
            sizeValue = "UNDEFINED";
        }
        addItem(items, "Screen size", sizeValue);

        final int screenLength = (activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_LONG_MASK);

        String lengthValue = null;
        if (screenLength == Configuration.SCREENLAYOUT_LONG_YES) {
            lengthValue = "LONG";
        } else if (screenLength == Configuration.SCREENLAYOUT_LONG_NO) {
            lengthValue = "NOTLONG";
        } else if (screenLength == Configuration.SCREENLAYOUT_LONG_UNDEFINED) {
            lengthValue = "UNDEFINED";
        }
        addItem(items, "Screen length", lengthValue);

        return items;
    }

    public static ArrayList<BaseInfoObject> getMemoryInfo(final Activity activity) {
        ArrayList<BaseInfoObject> items = new ArrayList<BaseInfoObject>();
        addHeader(items, "Device memory");
        addItem(items, "Device memory class", (Util.getMemoryClass(activity) / 1024d / 1024d) + " MB");
        addItem(items, "Device memory class", (Util.getAllowedMemory() / 1024d / 1024d) + " MB");

        return items;
    }

    public static String getFullInfoString(final Activity activity) {
        final StringBuilder builder = new StringBuilder();
        final ArrayList<BaseInfoObject> items = getFullInfo(activity);
        for (BaseInfoObject item : items) {
            if (item instanceof InfoImageItem) {
                // Skip
                continue;
            }

            if (item instanceof InfoHeader && builder.length() != 0) {
                builder.append("\n\n");
            } else if (builder.length() != 0) {
                builder.append("\n");
            }
            builder.append(item.toPrintableString());
        }

        return builder.toString();
    }

    private static void addHeader(ArrayList<BaseInfoObject> items, final String text) {
        items.add(new InfoHeader(text));
    }

    private static void addItem(ArrayList<BaseInfoObject> items, final String key, final String value) {
        items.add(new InfoItem(key, value));
    }
}
