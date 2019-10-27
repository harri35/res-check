package com.harrikirik.rescheck.activity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.harrikirik.rescheck.R;
import com.harrikirik.rescheck.fragment.SpecFragment;

/**
 * Created by Harri Kirik (harri35@gmail.com)
 */
public class TvActivity extends FragmentActivity {
    private static final String TAG_SPEC_FRAGMENT = "com.harrikirik.rescheck.TAG_SPEC_FRAGMENT";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_tv_activity_layout);
        addSpecFragment();
    }

    private void addSpecFragment() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(TAG_SPEC_FRAGMENT);
        if (fragment != null) {
            // Already added
            return;
        }
        fragment = SpecFragment.newInstance(SpecFragment.MODE_TV);
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.layout_fragment_container, fragment, TAG_SPEC_FRAGMENT);
        fragmentTransaction.commit();
    }
}
