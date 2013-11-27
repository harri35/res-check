package com.harrikirik.rescheck.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.harrikirik.rescheck.R;
import com.harrikirik.rescheck.dto.BaseInfoObject;

/**
 * Fragment for Info details
 * Harri Kirik, harri35@gmail.com
 */
public class DetailFragment extends Fragment {
    private static final String ARG_INFO_ITEM = "com.harrikirik.rescheck.ARG_INFO_ITEM";

    public static DetailFragment newInstance(final BaseInfoObject infoObject) {
        final DetailFragment fragment = new DetailFragment();
        final Bundle args = new Bundle();
        args.putSerializable(ARG_INFO_ITEM, infoObject);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_fragment_layout, container, false);
        return rootView;
    }


}
