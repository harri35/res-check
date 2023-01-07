package com.harrikirik.rescheck.util;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.harrikirik.rescheck.R;

import java.util.Locale;

import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import timber.log.Timber;


public class Util {

    public static void copyToClipboard(final Context context, final String content, final boolean showToast) {
        boolean success;
        // Newer versions
        success = copyToClipboardWithContentClipboardManager(context, content);

        if (showToast && success) {
            Toast.makeText(context, context.getString(R.string.text_copied_to_clipboard), Toast.LENGTH_SHORT).show();
        } else if (showToast) {
            Toast.makeText(context, context.getString(R.string.err_unexpected_error), Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean copyToClipboardWithContentClipboardManager(final Context context, final String content) {
       Timber.d("copyToClipboardWithContentClipboardManager - content: %s", content);
        try {
            final android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard == null) {
                return false;
            }
            final ClipData clip = ClipData.newPlainText(context.getText(R.string.app_name), content);
            clipboard.setPrimaryClip(clip);
            return true;
        } catch (Exception e) {
            Timber.e(e, "copyToClipboardWithContentClipboardManager");
        }
        return false;
    }

    public static void shareText(final Context context, final String chooserTitle, final String text) {
        Timber.d("shareText: %s", text);

        try {
            final Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, text);
            context.startActivity(Intent.createChooser(intent, chooserTitle));
        } catch (Exception e) {
            Timber.e(e, "shareText");
        }
    }

    @SuppressWarnings("unused")
    public static boolean contains(final String needle, final String heystack) {
        return !TextUtils.isEmpty(needle) && !TextUtils.isEmpty(heystack) && heystack.contains(needle);
    }

    public static boolean containsIgnoreCase(final String needle, final String heystack) {
        return !TextUtils.isEmpty(needle) && !TextUtils.isEmpty(heystack) && heystack.toLowerCase(Locale.ENGLISH).contains(needle.toLowerCase(Locale.ENGLISH));
    }

    public static void setupActionBarSearch(final Activity activity, Menu menu, final String initialValue, final SearchView.OnQueryTextListener queryTextListener) {
        final SearchManager searchManager = (SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
        final MenuItem menuItem = menu.findItem(R.id.menu_filter);
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
