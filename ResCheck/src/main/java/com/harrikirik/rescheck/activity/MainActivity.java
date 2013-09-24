package com.harrikirik.rescheck.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.harrikirik.rescheck.R;
import com.harrikirik.rescheck.fragment.SpecFragment;

public class MainActivity extends FragmentActivity {
	private static final String TAG_SPEC_FRAGMENT = "com.harrikirik.rescheck.TAG_SPEC_FRAGMENT";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity_layout);
		addSpecFragment();
	}

	private void addSpecFragment() {
		final FragmentManager fragmentManager = getSupportFragmentManager();
		Fragment fragment = fragmentManager.findFragmentByTag(TAG_SPEC_FRAGMENT);
		if (fragment != null) {
			// Already added
			return;
		}
		fragment = SpecFragment.newInstance();
		final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.layout_spec_container, fragment, TAG_SPEC_FRAGMENT);
		fragmentTransaction.commit();
	}
}