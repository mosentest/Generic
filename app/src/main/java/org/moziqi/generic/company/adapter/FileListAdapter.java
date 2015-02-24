package org.moziqi.generic.company.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.moziqi.generic.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by moziqi on 2015/2/23 0023.
 */
public class FileListAdapter extends BaseAdapter {
    private static Map<String, Integer> FILE_TYPE_ICONS = new HashMap<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private File[] mCurrentFiles;

    static {
        FILE_TYPE_ICONS.put("folder", R.drawable.folder);
        FILE_TYPE_ICONS.put("accdb", R.drawable.accdb);
        FILE_TYPE_ICONS.put("avi", R.drawable.avi);
        FILE_TYPE_ICONS.put("bmp", R.drawable.bmp);
        FILE_TYPE_ICONS.put("css", R.drawable.css);
        FILE_TYPE_ICONS.put("docx_mac", R.drawable.docx_mac);
        FILE_TYPE_ICONS.put("docx_win", R.drawable.docx_win);
        FILE_TYPE_ICONS.put("eml", R.drawable.eml);
        FILE_TYPE_ICONS.put("eps", R.drawable.eps);
        FILE_TYPE_ICONS.put("fla", R.drawable.fla);
        FILE_TYPE_ICONS.put("gif", R.drawable.gif);
        FILE_TYPE_ICONS.put("html", R.drawable.html);
        FILE_TYPE_ICONS.put("ind", R.drawable.ind);
        FILE_TYPE_ICONS.put("jpeg", R.drawable.jpeg);
        FILE_TYPE_ICONS.put("jsf", R.drawable.jsf);
        FILE_TYPE_ICONS.put("midi", R.drawable.midi);
        FILE_TYPE_ICONS.put("mov", R.drawable.mov);
        FILE_TYPE_ICONS.put("mp3", R.drawable.mp3);
        FILE_TYPE_ICONS.put("mpeg", R.drawable.mpeg);
        FILE_TYPE_ICONS.put("pdf", R.drawable.pdf);
        FILE_TYPE_ICONS.put("png", R.drawable.png);
        FILE_TYPE_ICONS.put("pptx_mac", R.drawable.pptx_mac);
        FILE_TYPE_ICONS.put("pptx_win", R.drawable.pptx_win);
        FILE_TYPE_ICONS.put("proj", R.drawable.proj);
        FILE_TYPE_ICONS.put("psd", R.drawable.psd);
        FILE_TYPE_ICONS.put("pst", R.drawable.pst);
        FILE_TYPE_ICONS.put("pub", R.drawable.pub);
        FILE_TYPE_ICONS.put("rar", R.drawable.rar);
        FILE_TYPE_ICONS.put("readme", R.drawable.readme);
        FILE_TYPE_ICONS.put("settings", R.drawable.settings);
        FILE_TYPE_ICONS.put("text", R.drawable.text);
        FILE_TYPE_ICONS.put("tiff", R.drawable.tiff);
        FILE_TYPE_ICONS.put("url", R.drawable.url);
        FILE_TYPE_ICONS.put("vsd", R.drawable.vsd);
        FILE_TYPE_ICONS.put("wav", R.drawable.wav);
        FILE_TYPE_ICONS.put("wma", R.drawable.wma);
        FILE_TYPE_ICONS.put("wmv", R.drawable.wmv);
        FILE_TYPE_ICONS.put("xlsx_mac", R.drawable.xlsx_mac);
        FILE_TYPE_ICONS.put("xlsx_win", R.drawable.xlsx_win);
        FILE_TYPE_ICONS.put("zip", R.drawable.zip);
    }

    public FileListAdapter(Context context, File[] currentFiles) {
        mContext = context;
        mCurrentFiles = currentFiles;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        if (mCurrentFiles == null) {
            return 0;
        }
        return mCurrentFiles.length;
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_file, null);
            viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_datetime = (TextView) convertView.findViewById(R.id.tv_datetime);
            viewHolder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Drawable drawable = null;
        if (mCurrentFiles[position].isDirectory()) {
//            File[] files = mCurrentFiles[position].listFiles();
//            viewHolder.tv_count.setText(files == null || files.length == 0 ? "空文件夹" : files.length + "项");
            drawable = mContext.getResources().getDrawable(R.drawable.folder);
            viewHolder.iv_icon.setImageDrawable(drawable);
        } else if (mCurrentFiles[position].isFile()) {
//            viewHolder.tv_count.setText("");
            drawable = mContext.getResources().getDrawable(R.drawable.css);
            viewHolder.iv_icon.setImageDrawable(drawable);
        }
        viewHolder.tv_name.setText(mCurrentFiles[position].getName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lastModified = mCurrentFiles[position].lastModified();
        viewHolder.tv_datetime.setText(dateFormat.format(new Date(lastModified)) + "");

        return convertView;
    }


    private static class ViewHolder {
        private ImageView iv_icon;
        private TextView tv_name;
        private TextView tv_datetime;
        private TextView tv_count;
    }

    public void updateData(File[] updateFiles) {
        mCurrentFiles = updateFiles;
        notifyDataSetChanged();
    }
}
