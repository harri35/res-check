package com.harrikirik.rescheck.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.harrikirik.rescheck.BuildConfig;
import com.harrikirik.rescheck.R;
import com.harrikirik.rescheck.fragment.SpecFragment;

/**
 * https://kuler.adobe.com/create/color-wheel/?base=2&rule=Compound&selected=1&name=My%20Kuler%20Theme&mode=rgb&rgbvalues=0.12657239266851425,0.4490174712408629,0.8,0.39492929450138564,0.4931195662210287,0.6,0.25821549083564277,1,0.995909379361133,1,0.6167865489474553,0.5082154908356428,0.8,0.17703652800975603,0.12657239266851425&swatchOrder=0,1,2,3,4
 */
public class MainActivity extends BaseActivity {
	private static final String TAG_SPEC_FRAGMENT = "com.harrikirik.rescheck.TAG_SPEC_FRAGMENT";
	private static final int MENU_ITEM_ID_TV = 42;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.generic_activity_layout);
		setupToolbar(getString(R.string.app_name));
		addSpecFragment();
	}

	private void addSpecFragment() {
		final FragmentManager fragmentManager = getSupportFragmentManager();
		Fragment fragment = fragmentManager.findFragmentByTag(TAG_SPEC_FRAGMENT);
		if (fragment != null) {
			// Already added
			return;
		}
		fragment = SpecFragment.newInstance(SpecFragment.MODE_NORMAL);
		final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.layout_fragment_container, fragment, TAG_SPEC_FRAGMENT);
		fragmentTransaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (BuildConfig.DEBUG) {
			menu.add(Menu.NONE, MENU_ITEM_ID_TV, 0, getString(R.string.menu_open_tv));
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (BuildConfig.DEBUG) {
			if (item.getItemId() == MENU_ITEM_ID_TV) {
				startActivity(new Intent(this, TvActivity.class));
				return true;
			}
		}
		return super.onOptionsItemSelected(item);
	}
}