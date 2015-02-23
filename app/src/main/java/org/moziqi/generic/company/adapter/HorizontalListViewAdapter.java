package org.moziqi.generic.company.adapter;

/**
 * Created by moziqi on 2015/2/23 0023.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.moziqi.generic.R;

public class HorizontalListViewAdapter extends BaseAdapter {
    private String[] mTitles;
    private Context mContext;
    private LayoutInflater mInflater;
    private int selectIndex = -1;

    public HorizontalListViewAdapter(Context context, String[] titles) {
        this.mContext = context;
        this.mTitles = titles;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_horizontal, null);
            holder.mTitle = (TextView) convertView.findViewById(R.id.text_list_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == selectIndex) {
            convertView.setSelected(true);
        } else {
            convertView.setSelected(false);
        }

        holder.mTitle.setText(mTitles[position]);

        return convertView;
    }

    private static class ViewHolder {
        private TextView mTitle;
    }

    public void setSelectIndex(int i) {
        selectIndex = i;
    }
}