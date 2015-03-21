package com.harrikirik.rescheck.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.harrikirik.rescheck.R;
import com.harrikirik.rescheck.dto.BaseInfoObject;
import com.harrikirik.rescheck.dto.CategorisedInfoItem;
import com.harrikirik.rescheck.dto.InfoImageItem;
import com.harrikirik.rescheck.dto.InfoItem;
import com.harrikirik.rescheck.util.Log;
import com.harrikirik.rescheck.util.Util;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Adapter for info items.
 * Harri Kirik, harri35@gmail.com
 */
public class InfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable, StickyRecyclerHeadersAdapter<InfoAdapter.HeaderViewHolder> {
    private static final int ITEM_TYPE_DATA = 100;
    private static final int ITEM_TYPE_IMAGE = 101;
    private final InfoAdapterListener listener;

    private Context context;
    private ArrayList<BaseInfoObject> items;
    private ArrayList<BaseInfoObject> filteredItems;
    private InfoFilter filter;
    private HashMap<String, Long> headerIds = new HashMap<>();
    private Log log = Log.getInstance(this);

    public InfoAdapter(final Context context, final ArrayList<BaseInfoObject> items, final InfoAdapterListener listener) {
        this.context = context;
        this.items = items;
        this.filteredItems = new ArrayList<>();
        this.filteredItems.addAll(this.items);
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_DATA: {
                return new DataViewHolder(LayoutInflater.from(context).inflate(R.layout.info_item, parent, false));
            }
            case ITEM_TYPE_IMAGE: {
                return new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.info_image_item, parent, false));
            }
        }
        return null;
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        final View v = LayoutInflater.from(context).inflate(R.layout.info_header, viewGroup, false);
        return new HeaderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final BaseInfoObject item = getItem(position);
        if (holder instanceof DataViewHolder) {
            ((DataViewHolder) holder).textTitle.setText(((InfoItem) item).getKey().toUpperCase(Locale.ENGLISH));
            ((DataViewHolder) holder).textValue.setText(((InfoItem) item).getValue());
        } else if (holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).textTitle.setText(((InfoImageItem) item).getKey().toUpperCase(Locale.ENGLISH));
            ((ImageViewHolder) holder).imageValue.setImageResource(((InfoImageItem) item).getDrawableId());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(item);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    listener.onItemLongClick(item);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder headerViewHolder, int position) {
        final BaseInfoObject item = getItem(position);
        if (item instanceof CategorisedInfoItem) {
            headerViewHolder.textHeaderLine1.setText(((CategorisedInfoItem) item).getCategory().getName().toUpperCase(Locale.ENGLISH));
        }
    }

    @Override
    public long getHeaderId(int position) {
        final BaseInfoObject item = getItem(position);
        if (!(item instanceof CategorisedInfoItem)) {
            return Integer.MAX_VALUE;
        }

        final String name = ((CategorisedInfoItem) item).getCategory().getName();
        if (!headerIds.containsKey(name)) {
            headerIds.put(name, Long.valueOf(headerIds.size()));
        }

        return headerIds.get(name);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof InfoImageItem) {
            return ITEM_TYPE_IMAGE;
        } else if (getItem(position) instanceof InfoItem) {
            return ITEM_TYPE_DATA;
        }
        return super.getItemViewType(position);
    }

    private BaseInfoObject getItem(int position) {
        return filteredItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new InfoFilter();
        }
        return filter;
    }

    private class InfoFilter extends Filter {

        @Override
        protected FilterResults performFiltering(final CharSequence constraint) {
            final ArrayList<BaseInfoObject> filteredItems = new ArrayList<BaseInfoObject>();
            final FilterResults results = new FilterResults();
            if (TextUtils.isEmpty(constraint)) {
                // No filter
                filteredItems.addAll(items);
            } else {
                final String needle = constraint.toString().toLowerCase(Locale.ENGLISH);
                // We have a filter
                for (BaseInfoObject o : items) {
                    if (o instanceof InfoItem && (Util.containsIgnoreCase(needle, ((InfoItem) o).getValue()) || Util.containsIgnoreCase(needle, ((InfoItem) o).getKey()))) {
                        filteredItems.add(o);
                    } else if (o instanceof InfoImageItem && Util.containsIgnoreCase(needle, ((InfoImageItem) o).getKey())) {
                        filteredItems.add(o);
                    }
                    // No headers needed for now ..
                }
            }
            results.values = filteredItems;
            results.count = filteredItems.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredItems = (ArrayList<BaseInfoObject>) results.values;
            notifyDataSetChanged();
        }
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        public TextView textTitle;
        public TextView textValue;

        public DataViewHolder(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.text_key);
            textValue = (TextView) itemView.findViewById(R.id.text_value);
        }
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textTitle;
        public ImageView imageValue;

        public ImageViewHolder(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.text_key);
            imageValue = (ImageView) itemView.findViewById(R.id.image_value);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public final TextView textHeaderLine1;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            textHeaderLine1 = (TextView) itemView.findViewById(R.id.text_header);
        }
    }

    public interface InfoAdapterListener {
        void onItemClick(final BaseInfoObject data);

        void onItemLongClick(final BaseInfoObject data);
    }

}
