package com.harrikirik.rescheck.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.harrikirik.rescheck.R;
import com.harrikirik.rescheck.dto.BaseInfoObject;
import com.harrikirik.rescheck.dto.InfoCategory;
import com.harrikirik.rescheck.dto.InfoImageItem;
import com.harrikirik.rescheck.dto.InfoItem;
import com.harrikirik.rescheck.util.InfoUtil;
import com.harrikirik.rescheck.util.Util;

import java.util.ArrayList;

/**
 * Adapter for info items.
 * Harri Kirik, harri35@gmail.com
 */
public class InfoAdapter extends BaseAdapter implements Filterable {
    private static final int ITEM_TYPE_COUNT = 3;
    private static final int ITEM_TYPE_DATA = 0;
    private static final int ITEM_TYPE_HEADER = 1;
    private static final int ITEM_TYPE_IMAGE = 2;

    private Context context;
    private ArrayList<BaseInfoObject> items;
    private ArrayList<BaseInfoObject> filteredItems;
    private InfoFilter filter;

    public InfoAdapter(final Context context, final ArrayList<BaseInfoObject> items) {
        this.context = context;
        this.items = items;
        this.filteredItems = InfoUtil.createCategorizedInfo(items); // No filter at start
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            // View not yet created
            if (getItemViewType(position) == ITEM_TYPE_DATA) {
                convertView = View.inflate(context, R.layout.info_item, null);
                holder.textTitle = (TextView) convertView.findViewById(R.id.text_key);
                holder.textValue = (TextView) convertView.findViewById(R.id.text_value);
            } else if (getItemViewType(position) == ITEM_TYPE_HEADER) {
                convertView = View.inflate(context, R.layout.info_header, null);
                holder.textTitle = (TextView) convertView.findViewById(R.id.text_header);
            } else if (getItemViewType(position) == ITEM_TYPE_IMAGE) {
                convertView = View.inflate(context, R.layout.info_image_item, null);
                holder.textTitle = (TextView) convertView.findViewById(R.id.text_key);
                holder.imageValue = (ImageView) convertView.findViewById(R.id.image_value);
            }
            convertView.setTag(holder);
        } else {
            // View created, fetch the holder
            holder = (ViewHolder) convertView.getTag();
        }

        // Fill the view
        if (getItemViewType(position) == ITEM_TYPE_DATA) {
            final InfoItem item = (InfoItem) getItem(position);
            holder.textTitle.setText(item.getKey().toUpperCase());
            holder.textValue.setText(item.getValue());
        } else if (getItemViewType(position) == ITEM_TYPE_HEADER) {
            final InfoCategory cat = (InfoCategory) getItem(position);
            holder.textTitle.setText(cat.getName().toUpperCase());
        } else if (getItemViewType(position) == ITEM_TYPE_IMAGE) {
            final InfoImageItem item = (InfoImageItem) getItem(position);
            holder.textTitle.setText(item.getKey().toUpperCase());
            holder.imageValue.setImageResource(item.getDrawableId());
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof InfoCategory) {
            return ITEM_TYPE_HEADER;
        } else if (getItem(position) instanceof InfoImageItem) {
            return ITEM_TYPE_IMAGE;
        } else {
            return ITEM_TYPE_DATA;
        }
    }

    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) == ITEM_TYPE_DATA;
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_TYPE_COUNT;
    }

    @Override
    public int getCount() {
        return filteredItems.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
                final String needle = constraint.toString().toLowerCase();
                // We have a filter
                for (BaseInfoObject o : items) {
                    if (o instanceof InfoItem && (Util.contains(needle, ((InfoItem) o).getValue()) || Util.contains(constraint.toString().toLowerCase(), ((InfoItem) o).getKey().toLowerCase()))) {
                        // Add all headers
                        filteredItems.add(o);
                    } else if (o instanceof InfoImageItem && Util.contains(needle, ((InfoImageItem) o).getKey().toLowerCase())) {
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
            filteredItems = InfoUtil.createCategorizedInfo((ArrayList<BaseInfoObject>) results.values);
            notifyDataSetChanged();
        }
    }

    /**
     * ViewHolder object for view access optimisation
     */
    private static class ViewHolder {
        TextView textTitle;
        TextView textValue;
        ImageView imageValue;
    }

}
