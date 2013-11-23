package com.harrikirik.rescheck.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.TextView;
import com.harrikirik.rescheck.R;
import com.harrikirik.rescheck.util.InfoUtil;
import com.harrikirik.rescheck.util.Util;

public class SpecFragment extends Fragment {

    public static Fragment newInstance() {
        // No args for now
        return new SpecFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        final View view = inflater.inflate(R.layout.spec_fragment_layout, null);

        final TextView textData = (TextView) view.findViewById(R.id.text_spec_info);
        textData.setText(InfoUtil.getFullInfo(getActivity()).toString());

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_copy_to_clipboard:
                Util.copyToClipboard(getActivity(), InfoUtil.getFullInfo(getActivity()).toString(), true);
                return true;
            case R.id.menu_share_with:
                Util.shareText(getActivity(), getActivity().getString(R.string.title_share_with), InfoUtil.getFullInfo(getActivity()).toString());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
