package com.harrikirik.rescheck.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.SearchManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;
import com.harrikirik.rescheck.R;
import android.support.v4.view.MenuItemCompat;


public class Util {

    private static Log log = Log.getInstance(Util.class);

    public static void copyToClipboard(final Context context, final String content, final boolean showToast) {
        boolean success = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Newer versions
            success = copyToClipboardWithContentClipboardManager(context, content);
        } else {
            // Earlier versions
            success = copyToClipboardWithTextClipboardManager(context, content);
        }

        if (showToast && success) {
            Toast.makeText(context, context.getString(R.string.text_copied_to_clipboard), Toast.LENGTH_SHORT).show();
        } else if (showToast) {
            Toast.makeText(context, context.getString(R.string.err_unexpected_error), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("Depricated")
    private static boolean copyToClipboardWithTextClipboardManager(final Context context, final String content) {
        try {
            final android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(content);
            return true;
        } catch (Exception e) {
            log.e("copyToClipboardWithTextClipboardManager", e);
        }
        return false;
    }

    /**
     * PS: API level 11 and over
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static boolean copyToClipboardWithContentClipboardManager(final Context context, final String content) {
        Log.getInstance(Util.class).d("copyToClipboardWithContentClipboardManager - content: " + content);
        try {
            final android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            final ClipData clip = ClipData.newPlainText(context.getText(R.string.app_name), content);
            clipboard.setPrimaryClip(clip);
            return true;
        } catch (Exception e) {
            Log.getInstance(Util.class).e("copyToClipboardWithContentClipboardManager", e);
        }
        return false;
    }

    public static void shareText(final Context context, final String chooserTitle, final String text) {
        Log.getInstance(Util.class).d("shareText: " + text);

        try {
            final Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, text);
            context.startActivity(Intent.createChooser(intent, chooserTitle));
        } catch (Exception e) {
            Log.getInstance(Util.class).e("shareText", e);
        }
    }

    public static boolean contains(final String needle, final String heystack) {
        return !TextUtils.isEmpty(needle) && !TextUtils.isEmpty(heystack) && heystack.contains(needle);
    }

    public static void setupActionBarSearch(final Activity activity, Menu menu, final String initialValue, final SearchView.OnQueryTextListener queryTextListener) {
        final SearchManager searchManager = (SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
        final MenuItem menuItem =  menu.findItem(R.id.menu_filter);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        searchView.setSubmitButtonEnabled(false);
        searchView.setImeOptions(EditorInfo.IME_ACTION_NONE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.getComponentName()));

        if (!TextUtils.isEmpty(initialValue)) {
            MenuItemCompat.expandActionView(menuItem);
            searchView.setQuery(initialValue, false);
        } else {
            searchView.setQuery("", false);
        }
        searchView.clearFocus();
        searchView.setOnQueryTextListener(queryTextListener);
        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                // Clear the query
                searchView.setQuery("", false);
                return true;
            }
        });

        if (!TextUtils.isEmpty(initialValue) && queryTextListener != null) {
            queryTextListener.onQueryTextChange(initialValue);
        }
    }
}
