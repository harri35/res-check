package com.harrikirik.rescheck.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.*;
import android.widget.AdapterView;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;
import com.harrikirik.rescheck.R;
import com.harrikirik.rescheck.adapter.InfoAdapter;
import com.harrikirik.rescheck.dto.BaseInfoObject;
import com.harrikirik.rescheck.util.InfoUtil;
import com.harrikirik.rescheck.util.Log;
import com.harrikirik.rescheck.util.Util;

import java.util.ArrayList;

public class SpecFragment extends Fragment {
    private static final String STATE_FILTER = "com.harrikirik.rescheck.STATE_FILTER";

    private Log log = Log.getInstance(this);
    private ListView listSpec;
    private String filterText;

    public static Fragment newInstance() {
        // No args for now
        return new SpecFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        final View view = inflater.inflate(R.layout.spec_fragment_layout, null);
        listSpec = (ListView) view.findViewById(R.id.list_spec_info);
        final TextView textEmpty = (TextView) view.findViewById(R.id.text_spec_empty);
        listSpec.setEmptyView(textEmpty);
        if (savedInstanceState != null) {
            filterText = savedInstanceState.getString(STATE_FILTER);
        }
        displayInfo();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_FILTER, filterText);
    }

    private void displayInfo() {
        if (listSpec == null) {
            return;
        }
        final ArrayList<BaseInfoObject> items = InfoUtil.getFullInfo(getActivity());
        final InfoAdapter adapter = new InfoAdapter(getActivity().getApplicationContext(), items);
        listSpec.setAdapter(adapter);
        listSpec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Object o = adapter.getItem(position);
                if (!(o instanceof BaseInfoObject)) {
                    // Something is wrong here
                    return;
                }
                Util.copyToClipboard(getActivity(), ((BaseInfoObject) o).toPrintableString(), true);
            }
        });
        listSpec.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Object o = adapter.getItem(position);
                if (!(o instanceof BaseInfoObject)) {
                    // Something is wrong here
                    return false;
                }
                Util.shareText(getActivity(), getActivity().getString(R.string.title_share_with), ((BaseInfoObject) o).toPrintableString());
                return true;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
        Util.setupActionBarSearch(getActivity(), menu, filterText, new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                setListFilter(s);
                return true;
            }
        });

    }

    private void setListFilter(final String filterText) {
        log.d("setListFilter: " + filterText);
        if (listSpec == null || listSpec.getAdapter() == null) {
            return;
        }
        this.filterText = filterText;
        ((Filterable) listSpec.getAdapter()).getFilter().filter(filterText);
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
