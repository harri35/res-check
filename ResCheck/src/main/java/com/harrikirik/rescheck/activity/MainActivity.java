package com.harrikirik.rescheck.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.harrikirik.rescheck.R;
import com.harrikirik.rescheck.fragment.SpecFragment;

/**
 * https://kuler.adobe.com/create/color-wheel/?base=2&rule=Compound&selected=1&name=My%20Kuler%20Theme&mode=rgb&rgbvalues=0.12657239266851425,0.4490174712408629,0.8,0.39492929450138564,0.4931195662210287,0.6,0.25821549083564277,1,0.995909379361133,1,0.6167865489474553,0.5082154908356428,0.8,0.17703652800975603,0.12657239266851425&swatchOrder=0,1,2,3,4
 */
public class MainActivity extends ActionBarActivity {
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