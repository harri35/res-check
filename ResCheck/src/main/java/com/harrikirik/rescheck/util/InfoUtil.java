package com.harrikirik.rescheck.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;

import com.harrikirik.rescheck.R;
import com.harrikirik.rescheck.dto.BaseInfoObject;
import com.harrikirik.rescheck.dto.CategorisedInfoItem;
import com.harrikirik.rescheck.dto.InfoCategory;
import com.harrikirik.rescheck.dto.InfoImageItem;
import com.harrikirik.rescheck.dto.InfoItem;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Util class to get the actual device info
 * Harri Kirik, harri35@gmail.com
 */
public class InfoUtil {
    private static final int LOW_RAM_DEVICE_UNKNOWN = R.string.text_low_ram_device_unknown;
    private static final int LOW_RAM_DEVICE_YES = R.string.text_low_ram_device_yes;
    private static final int LOW_RAM_DEVICE_NO = R.string.text_low_ram_device_no;

    private static Log log = Log.getInstance(InfoUtil.class);

    public static ArrayList<BaseInfoObject> getFullInfo(final Activity activity) {
        final ArrayList<BaseInfoObject> items = new ArrayList<>();

        try {
            items.addAll(getGeneralDeviceInfo(activity));
        } catch (Exception e) {
            log.e("getFullInfo -> getGeneralDeviceInfo", e);
        }

        try {
            items.addAll(getDisplayInfo(activity));
        } catch (Exception e) {
            log.e("getFullInfo -> getDisplayInfo", e);
        }

        try {
            items.addAll(getScreenInfo(activity));
        } catch (Exception e) {
            log.e("getFullInfo -> getScreenInfo", e);
        }

        try {
            items.addAll(getMemoryInfo(activity));
        } catch (Exception e) {
            log.e("getFullInfo -> getMemoryInfo", e);
        }

        try {
            items.addAll(getABIInfo(activity));
        } catch (Exception e) {
            log.e("getFullInfo -> getABIInfo", e);
        }

        try {
            items.addAll(getGraphicsInfo(activity));
        } catch (Exception e) {
            log.e("getFullInfo -> getGraphicsInfo", e);
        }

        return items;
    }

    @SuppressLint("HardwareIds")
    private static ArrayList<BaseInfoObject> getGeneralDeviceInfo(final Activity activity) {
        final ArrayList<BaseInfoObject> items = new ArrayList<>();
        final InfoCategory cat = new InfoCategory(activity.getString(R.string.text_device_general_info));
        addItem(items, activity.getString(R.string.text_model), Build.MODEL, cat);
        addItem(items, activity.getString(R.string.text_build_version), getBuildVersionName(), cat);
        addItem(items, activity.getString(R.string.text_api_level), getAndroidVersionName(activity), cat);
        addItem(items, activity.getString(R.string.text_manufacturer), Build.MANUFACTURER, cat);
        addItem(items, activity.getString(R.string.text_device), Build.DEVICE, cat);
        addItem(items, activity.getString(R.string.text_product), Build.PRODUCT, cat);
        addItem(items, activity.getString(R.string.text_serial), Build.SERIAL, cat);
        addItem(items, activity.getString(R.string.text_build_tags), Build.TAGS, cat);

        return items;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static ArrayList<BaseInfoObject> getABIInfo(final Activity activity) {
        final ArrayList<BaseInfoObject> items = new ArrayList<>();
        final InfoCategory cat = new InfoCategory(activity.getString(R.string.text_application_binary_interface));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            addItem(items, activity.getString(R.string.text_supported_abis), Arrays.toString(Build.SUPPORTED_ABIS), cat);
        } else {
            addItem(items, activity.getString(R.string.text_cpu_abi_1), Build.CPU_ABI, cat);
            addItem(items, activity.getString(R.string.text_cpu_abi_2), Build.CPU_ABI2, cat);
        }
        return items;
    }

    private static ArrayList<BaseInfoObject> getGraphicsInfo(final Activity activity) {
        final ArrayList<BaseInfoObject> items = new ArrayList<>();
        final InfoCategory cat = new InfoCategory(activity.getString(R.string.text_graphics));
        final ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            ConfigurationInfo info = am.getDeviceConfigurationInfo();
            addItem(items, activity.getString(R.string.text_openg_gl_version), activity.getString(R.string.text_opengl_es, info.getGlEsVersion()), cat);
        }
        return items;
    }

    private static String getBuildVersionName() {
        return TextUtils.equals(Build.VERSION.CODENAME, "REL") ? Build.VERSION.RELEASE : Build.VERSION.CODENAME;
    }

    private static String getAndroidVersionName(final Context context) {
        final int versionInt = Build.VERSION.SDK_INT;
        String versionName = null;
        switch (versionInt) {
            case Build.VERSION_CODES.JELLY_BEAN:
                versionName = "JELLY BEAN";
                break;
            case Build.VERSION_CODES.JELLY_BEAN_MR1:
                versionName = "JELLY BEAN MR1";
                break;
            case Build.VERSION_CODES.JELLY_BEAN_MR2:
                versionName = "JELLY BEAN MR2";
                break;
            case Build.VERSION_CODES.HONEYCOMB:
                versionName = "HONEYCOMB";
                break;
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH:
                versionName = "ICE CREAM SANDWICH";
                break;
            case Build.VERSION_CODES.BASE:
                versionName = "BASE";
                break;
            case Build.VERSION_CODES.CUPCAKE:
                versionName = "CUPCAKE";
                break;
            case Build.VERSION_CODES.CUR_DEVELOPMENT:
                versionName = "CUR DEVELOPMENT";
                break;
            case Build.VERSION_CODES.DONUT:
                versionName = "DONUT";
                break;
            case Build.VERSION_CODES.ECLAIR:
                versionName = "ECLAIR";
                break;
            case Build.VERSION_CODES.ECLAIR_0_1:
                versionName = "ECLAIR 0 1";
                break;
            case Build.VERSION_CODES.ECLAIR_MR1:
                versionName = "ECLAIR MR1";
                break;
            case Build.VERSION_CODES.FROYO:
                versionName = "FROYO";
                break;
            case Build.VERSION_CODES.GINGERBREAD:
                versionName = "GINGERBREAD";
                break;
            case Build.VERSION_CODES.GINGERBREAD_MR1:
                versionName = "GINGERBREAD MR1";
                break;
            case Build.VERSION_CODES.HONEYCOMB_MR1:
                versionName = "HONEYCOMB MR1";
                break;
            case Build.VERSION_CODES.HONEYCOMB_MR2:
                versionName = "HONEYCOMB MR2";
                break;
            case Build.VERSION_CODES.KITKAT:
                versionName = "KITKAT";
                break;
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1:
                versionName = "ICE CREAM SANDWICH MR1";
                break;
            case Build.VERSION_CODES.BASE_1_1:
                versionName = "BASE 1 1";
                break;
            case Build.VERSION_CODES.LOLLIPOP:
                versionName = "LOLLIPOP";
                break;
        }
        return !TextUtils.isEmpty(versionName) ? context.getString(R.string.text_api_level_x_y, Integer.toString(versionInt), versionName) : String.valueOf(versionInt);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static ArrayList<BaseInfoObject> getDisplayInfo(final Activity activity) {
        final Display display = activity.getWindowManager().getDefaultDisplay();
        final DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        final ArrayList<BaseInfoObject> items = new ArrayList<>();

        final InfoCategory cat = new InfoCategory(activity.getString(R.string.text_device_display));
        addItem(items, activity.getString(R.string.text_display_id), String.valueOf(display.getDisplayId()), cat);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            addItem(items, activity.getString(R.string.text_display_name), display.getName(), cat);
        } else {
            addItem(items, activity.getString(R.string.text_display_name), activity.getString(R.string.text_unknown), cat);
        }
        addItem(items, activity.getString(R.string.text_display_with), activity.getString(R.string.text_x_pixels, String.valueOf(metrics.widthPixels)), cat);
        addItem(items, activity.getString(R.string.text_display_height), activity.getString(R.string.text_x_pixels, String.valueOf(metrics.heightPixels)), cat);

        String orientation = activity.getString(R.string.text_orientation_unknown);
        switch (activity.getResources().getConfiguration().orientation) {
            case android.content.res.Configuration.ORIENTATION_LANDSCAPE:
                orientation = activity.getString(R.string.text_orientation_landscape);
                break;
            case android.content.res.Configuration.ORIENTATION_PORTRAIT:
                orientation = activity.getString(R.string.text_orientation_portrait);
                break;
            case android.content.res.Configuration.ORIENTATION_SQUARE:
                orientation = activity.getString(R.string.text_orientation_square);
                break;
        }
        addItem(items, activity.getString(R.string.text_display_orientation), orientation, cat);

        return items;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static ArrayList<BaseInfoObject> getScreenInfo(final Activity activity) {
        final Configuration conf = activity.getResources().getConfiguration();

        final ArrayList<BaseInfoObject> items = new ArrayList<>();
        final Display display = activity.getWindowManager().getDefaultDisplay();
        final DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        final InfoCategory cat = new InfoCategory(activity.getString(R.string.text_device_screen));
        addItem(items, activity.getString(R.string.text_screen_with), activity.getString(R.string.text_x_dpi, String.valueOf(conf.screenWidthDp)), cat);
        addItem(items, activity.getString(R.string.text_screen_height), activity.getString(R.string.text_x_dpi, String.valueOf(conf.screenHeightDp)), cat);
        addItem(items, activity.getString(R.string.text_screen_smallest_width_dp), activity.getString(R.string.text_x_dpi, String.valueOf(conf.smallestScreenWidthDp)), cat);

        items.add(new InfoImageItem(activity.getString(R.string.text_screen_density), R.drawable.icon_dpi, cat));
        addItem(items, activity.getString(R.string.text_screen_density_value_dpi), activity.getString(R.string.text_x_dpi, String.valueOf(metrics.densityDpi)), cat);
        addItem(items, activity.getString(R.string.text_screen_density_value), String.valueOf(metrics.density), cat);
        addItem(items, activity.getString(R.string.text_screen_scaled_density), String.valueOf(metrics.scaledDensity), cat);
        addItem(items, activity.getString(R.string.text_font_scale), String.valueOf(conf.fontScale), cat);

        final int screenSize = (conf.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK);

        String sizeValue = null;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            sizeValue = activity.getString(R.string.text_screen_normal_caps);
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            sizeValue = activity.getString(R.string.text_screen_small_caps);
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            sizeValue = activity.getString(R.string.text_screen_large_caps);
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            sizeValue = activity.getString(R.string.text_screen_xlarge_caps);
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_UNDEFINED) {
            sizeValue = activity.getString(R.string.text_screen_unknown_caps);
        }
        addItem(items, activity.getString(R.string.screen_size), sizeValue, cat);

        final int screenLength = (conf.screenLayout & Configuration.SCREENLAYOUT_LONG_MASK);

        String lengthValue = null;
        if (screenLength == Configuration.SCREENLAYOUT_LONG_YES) {
            lengthValue = activity.getString(R.string.screen_length_long_caps);
        } else if (screenLength == Configuration.SCREENLAYOUT_LONG_NO) {
            lengthValue = activity.getString(R.string.screen_length_notlong_caps);
        } else if (screenLength == Configuration.SCREENLAYOUT_LONG_UNDEFINED) {
            lengthValue = activity.getString(R.string.screen_length_unknown_caps);
        }
        addItem(items, activity.getString(R.string.text_screen_length), lengthValue, cat);


        String touchScreenValue = activity.getString(R.string.touchscreen_unknown);
        if (conf.touchscreen == Configuration.TOUCHSCREEN_FINGER) {
            touchScreenValue = activity.getString(R.string.touchscreen_finger);
        } else if (conf.touchscreen == Configuration.TOUCHSCREEN_NOTOUCH) {
            touchScreenValue = activity.getString(R.string.touchscreen_no_touch);
        }
        addItem(items, activity.getString(R.string.text_screen_touch_type), touchScreenValue, cat);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            String layoutDirectionValue = null;
            if (conf.getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
                layoutDirectionValue = activity.getString(R.string.screen_layout_direction_left_to_right);
            } else if (conf.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                layoutDirectionValue = activity.getString(R.string.screen_layout_direction_right_to_left);
            }
            addItem(items, activity.getString(R.string.text_screen_layout_direction), layoutDirectionValue, cat);
        } else {
            addItem(items, activity.getString(R.string.text_screen_layout_direction), activity.getString(R.string.text_unknown), cat);
        }

        return items;
    }

    private static ArrayList<BaseInfoObject> getMemoryInfo(final Activity activity) {
        final ArrayList<BaseInfoObject> items = new ArrayList<>();
        final InfoCategory cat = new InfoCategory(activity.getString(R.string.text_device_memory));
        addItem(items, activity.getString(R.string.text_device_memory_class_mb), activity.getString(R.string.text_x_mb, String.valueOf(getMemoryClass(activity) / 1024d / 1024d)), cat);
        addItem(items, activity.getString(R.string.text_device_max_memory_mb), activity.getString(R.string.text_x_mb, String.valueOf((getAllowedMemory() / 1024d / 1024d))), cat);
        addItem(items, activity.getString(R.string.text_low_ram_device), activity.getString(isLowRamDevice(activity)), cat);
        return items;
    }

    private static ArrayList<BaseInfoObject> createCategorizedInfo(final ArrayList<BaseInfoObject> items) {
        final ArrayList<BaseInfoObject> catItems = new ArrayList<>();
        if (items == null || items.size() == 0) {
            return catItems;
        }

        // Assume info is grouped by category already
        InfoCategory cat = null;
        for (BaseInfoObject o : items) {
            if (o instanceof CategorisedInfoItem && !((CategorisedInfoItem) o).getCategory().equals(cat)) {
                cat = new InfoCategory(((CategorisedInfoItem) o).getCategory().getName());
                catItems.add(cat);
            }
            catItems.add(o);
        }

        return catItems;
    }

    public static String getFullInfoString(final Activity activity) {
        final StringBuilder builder = new StringBuilder();
        final ArrayList<BaseInfoObject> items = createCategorizedInfo(getFullInfo(activity));
        for (BaseInfoObject item : items) {
            if (item instanceof InfoImageItem) {
                // Skip
                continue;
            }

            if (item instanceof InfoCategory && builder.length() != 0) {
                builder.append("\n\n");
            } else if (builder.length() != 0) {
                builder.append("\n");
            }
            builder.append(item.toPrintableString());
        }

        return builder.toString();
    }

    private static void addItem(ArrayList<BaseInfoObject> items, final String key, final String value, final InfoCategory cat) {
        items.add(new InfoItem(key, value, cat));
    }

    /**
     * Return the available memory size in bytes. <br/>
     * PS: This is usually the same as {@link #getMemoryClass(android.content.Context)} but may be also bigger is some cases, for example for rooted devices with custom builds or when android:largeHeap="true" is set in the Manifest<br/>
     * PPS: Going over this will crash the app.
     */
    private static long getAllowedMemory() {
        final long defaultValue = 16L * 1024L * 1024L;

        final Runtime runtime = Runtime.getRuntime();
        if (runtime != null) {
            final long value = runtime.maxMemory();
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
    private static long getMemoryClass(final Context context) {
        final long defaultValue = 16L * 1024L * 1024L;

        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        if (activityManager != null) {
            return activityManager.getMemoryClass() * 1024L * 1024L;
        }
        return defaultValue;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static int isLowRamDevice(final Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return LOW_RAM_DEVICE_UNKNOWN;
        }
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        if (activityManager == null) {
            return LOW_RAM_DEVICE_UNKNOWN;
        }
        return activityManager.isLowRamDevice() ? LOW_RAM_DEVICE_YES : LOW_RAM_DEVICE_NO;
    }
}
