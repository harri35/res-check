package com.harrikirik.rescheck.activity;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.harrikirik.rescheck.R;

/**
 * Created by Harri Kirik (harri35@gmail.com)
 */
public class BaseActivity extends ActionBarActivity {
    protected Toolbar toolbar;

    public Toolbar getToolbar() {
        if (toolbar == null) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
        }
        return toolbar;
    }

    protected void setupToolbar(final String title) {
        setupToolbar(title, false);
    }

    protected void setupToolbar(final String title, final boolean enableUpNavigation) {
        final Toolbar toolbar = getToolbar();
        if (toolbar == null) {
            return;
        }
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (enableUpNavigation) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        }
    }
}
