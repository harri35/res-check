package com.harrikirik.rescheck.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import timber.log.Timber;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.TextView;

import com.harrikirik.rescheck.R;
import com.harrikirik.rescheck.adapter.InfoAdapter;
import com.harrikirik.rescheck.decarator.DividerItemDecoration;
import com.harrikirik.rescheck.dto.BaseInfoObject;
import com.harrikirik.rescheck.util.InfoUtil;
import com.harrikirik.rescheck.util.Util;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;

public class SpecFragment extends Fragment implements InfoAdapter.InfoAdapterListener {
    public static final int MODE_NORMAL = 0;
    public static final int MODE_TV = 1;

    private static final String ARG_MODE = "com.harrikirik.rescheck.ARG_MODE";

    private static final String STATE_FILTER = "com.harrikirik.rescheck.STATE_FILTER";

    private RecyclerView recyclerView;
    private String filterText;

    public static Fragment newInstance(final int mode) {
        // No args for now
        SpecFragment fragment = new SpecFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MODE, mode);
        fragment.setArguments(args);
        return fragment;
    }

    private int getMode() {
        assert getArguments() != null;
        return getArguments().getInt(ARG_MODE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        final View view = inflater.inflate(R.layout.spec_fragment_layout, container, false);
        recyclerView = view.findViewById(R.id.recycler_spec_info);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(container.getContext(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        final TextView textEmpty = view.findViewById(R.id.text_spec_empty);
        textEmpty.setVisibility(View.GONE);
        if (savedInstanceState != null) {
            filterText = savedInstanceState.getString(STATE_FILTER);
        }
        displayInfo();
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_FILTER, filterText);
    }

    private void displayInfo() {
        if (recyclerView == null) {
            return;
        }
        final ArrayList<BaseInfoObject> items = InfoUtil.getFullInfo(getActivity());
        final InfoAdapter adapter = new InfoAdapter(recyclerView.getContext().getApplicationContext(), items, this);
        recyclerView.setAdapter(adapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            recyclerView.addItemDecoration(new StickyRecyclerHeadersDecoration(adapter));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (getMode() == MODE_NORMAL) {
            inflater.inflate(R.menu.main_menu, menu);
            //noinspection ConstantConditions
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

    }

    private void setListFilter(final String filterText) {
        Timber.d("setListFilter: %s", filterText);
        if (recyclerView == null || recyclerView.getAdapter() == null) {
            return;
        }
        this.filterText = filterText;
        ((Filterable) recyclerView.getAdapter()).getFilter().filter(filterText);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_copy_to_clipboard:
                Util.copyToClipboard(getActivity(), InfoUtil.getFullInfoString(getActivity()), true);
                return true;
            case R.id.menu_share_with:
                //noinspection ConstantConditions
                Util.shareText(getActivity(), getActivity().getString(R.string.title_share_with), InfoUtil.getFullInfoString(getActivity()));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(BaseInfoObject data) {
        Util.copyToClipboard(getActivity(), data.toPrintableString(), true);
    }

    @Override
    public void onItemLongClick(BaseInfoObject data) {
        if (getActivity() == null) {
            return;
        }
        Util.shareText(getActivity(), getActivity().getString(R.string.title_share_with), data.toPrintableString());
    }
}
