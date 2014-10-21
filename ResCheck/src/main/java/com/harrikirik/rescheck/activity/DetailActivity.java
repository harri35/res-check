package com.harrikirik.rescheck.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import com.harrikirik.rescheck.R;
import com.harrikirik.rescheck.dto.BaseInfoObject;
import com.harrikirik.rescheck.fragment.DetailFragment;
import com.harrikirik.rescheck.util.Log;

public class DetailActivity extends ActionBarActivity {
    public static final String EXTRA_INFO_ITEM = "com.harrikirik.rescheck.EXTRA_INFO_ITEM";
    private static final String TAG_DETAIL_FRAGMENT = "com.harrikirik.rescheck.TAG_DETAIL_FRAGMENT";

    private Log log = Log.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseInfoObject infoObject = null;
        if (getIntent().getExtras() != null) {
            infoObject = (BaseInfoObject) getIntent().getExtras().getSerializable(EXTRA_INFO_ITEM);
        }

        if (infoObject == null) {
            log.e("onCreate: No infoObject given, aborting!");
            finish();
            return;
        }

        setContentView(R.layout.detail_activity_layout);
        addDetailFragment(infoObject);
    }

    private void addDetailFragment(final BaseInfoObject infoObject) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(TAG_DETAIL_FRAGMENT);
        if (fragment != null) {
            // Already added
            return;
        }
        fragment = DetailFragment.newInstance(infoObject);
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.layout_detail_container, fragment, TAG_DETAIL_FRAGMENT);
        fragmentTransaction.commit();
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.detail, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
