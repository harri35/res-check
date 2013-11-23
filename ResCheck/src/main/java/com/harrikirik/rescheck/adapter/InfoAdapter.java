package com.harrikirik.rescheck.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.harrikirik.rescheck.R;
import com.harrikirik.rescheck.dto.BaseInfoObject;
import com.harrikirik.rescheck.dto.InfoHeader;
import com.harrikirik.rescheck.dto.InfoImageItem;
import com.harrikirik.rescheck.dto.InfoItem;

import java.util.ArrayList;

/**
 * Adapter for info items.
 * Harri Kirik, harri35@gmail.com
 */
public class InfoAdapter extends BaseAdapter {
    private static final int ITEM_TYPE_COUNT = 3;
    private static final int ITEM_TYPE_DATA = 0;
    private static final int ITEM_TYPE_HEADER = 1;
    private static final int ITEM_TYPE_IMAGE = 2;

    private Context context;
    private ArrayList<BaseInfoObject> items;

    public InfoAdapter(final Context context, final ArrayList<BaseInfoObject> items) {
        this.context = context;
        this.items = items;
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
            final InfoHeader header = (InfoHeader) getItem(position);
            holder.textTitle.setText(header.getTitle().toUpperCase());
        } else if (getItemViewType(position) == ITEM_TYPE_IMAGE) {
            final InfoImageItem item = (InfoImageItem) getItem(position);
            holder.textTitle.setText(item.getKey().toUpperCase());
            holder.imageValue.setImageResource(item.getDrawableId());
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof InfoHeader) {
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
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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