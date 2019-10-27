package com.harrikirik.rescheck.activity;

import android.annotation.SuppressLint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.harrikirik.rescheck.R;

/**
 * Created by Harri Kirik (harri35@gmail.com)
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    protected Toolbar toolbar;

    public Toolbar getToolbar() {
        if (toolbar == null) {
            toolbar = findViewById(R.id.toolbar);
        }
        return toolbar;
    }

    protected void setupToolbar(final String title) {
        setupToolbar(title, false);
    }

    protected void setupToolbar(final String title, @SuppressWarnings("SameParameterValue") final boolean enableUpNavigation) {
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
