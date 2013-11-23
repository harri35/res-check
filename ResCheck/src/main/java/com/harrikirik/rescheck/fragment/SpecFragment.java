package com.harrikirik.rescheck.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.harrikirik.rescheck.R;
import com.harrikirik.rescheck.adapter.InfoAdapter;
import com.harrikirik.rescheck.dto.BaseInfoObject;
import com.harrikirik.rescheck.util.InfoUtil;
import com.harrikirik.rescheck.util.Util;

import java.util.ArrayList;

public class SpecFragment extends Fragment {

    private ListView listSpec;

    public static Fragment newInstance() {
        // No args for now
        return new SpecFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        final View view = inflater.inflate(R.layout.spec_fragment_layout, null);
        listSpec = (ListView) view.findViewById(R.id.list_spec_info);
        displayInfo();
        return view;
    }

    private void displayInfo() {
        if (listSpec == null) {
            return;
        }
        final ArrayList<BaseInfoObject> items = InfoUtil.getFullInfo(getActivity());
        listSpec.setAdapter(new InfoAdapter(getActivity().getApplicationContext(), items));
        listSpec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Util.copyToClipboard(getActivity(), items.get(position).toPrintableString(), true);
            }
        });
        listSpec.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Util.shareText(getActivity(), getActivity().getString(R.string.title_share_with), items.get(position).toPrintableString());
                return true;
            }
        });
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
                Util.copyToClipboard(getActivity(), InfoUtil.getFullInfoString(getActivity()).toString(), true);
                return true;
            case R.id.menu_share_with:
                Util.shareText(getActivity(), getActivity().getString(R.string.title_share_with), InfoUtil.getFullInfoString(getActivity()).toString());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
