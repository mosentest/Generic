package org.moziqi.generic.company.adapter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.bitmap.BitmapCallBack;
import org.kymjs.kjframe.bitmap.BitmapConfig;
import org.kymjs.kjframe.bitmap.BitmapDownloader;
import org.moziqi.generic.R;
import org.moziqi.generic.common.cache.ImageLoader;
import org.moziqi.generic.common.util.FileType;
import org.moziqi.generic.company.filter.HiddenFileFilter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import libcore.io.DiskLruCache;

/**
 * Created by moziqi on 2015/2/23 0023.
 */
public class FileListAdapter extends BaseAdapter {

    private static Map<String, Integer> FILE_TYPE_ICONS = new HashMap<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private File[] mCurrentFiles;
    private boolean mBusy = false;
    private KJBitmap kjb;

    public void setFlagBusy(boolean busy) {
        this.mBusy = busy;
    }

    private ImageLoader mImageLoader;

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
        this.mCurrentFiles = currentFiles;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImageLoader = new ImageLoader(context);
        BitmapConfig bitmapConfig = new BitmapConfig();
        BitmapConfig.CACHEPATH = "mo/cache"; // 设置图片缓存路径为SD卡根目录hello文件夹下world文件夹内
        BitmapConfig.CACHE_FILENAME_PREFIX = "mo_";// 设置缓存前缀
        bitmapConfig.downloader = new BitmapDownloader(bitmapConfig); // 使用自定义的图片加载器（默认使用框架中的）
        kjb = KJBitmap.create(bitmapConfig);
    }


    public ImageLoader getImageLoader() {
        return mImageLoader;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
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
        if (mCurrentFiles != null) {
            // 给ImageView设置一个Tag，保证异步加载图片时不会乱序
            viewHolder.iv_icon.setTag(mCurrentFiles[position].getPath());
            String fileType = FileType.getFileType(mCurrentFiles[position]);
            if (mCurrentFiles[position].isDirectory()) {
                File[] files = mCurrentFiles[position].listFiles(HiddenFileFilter.getInstance());
                viewHolder.tv_count.setText(files == null || files.length == 0 ? "空文件夹" : files.length + "项");
                drawable = mContext.getResources().getDrawable(R.drawable.folder);
//                viewHolder.iv_icon.setImageDrawable(drawable);
            } else if (mCurrentFiles[position].isFile() && checkEndsWithInStringArray(mCurrentFiles[position].toString(),
                    mContext.getResources().getStringArray(R.array.fileEndingImage)) && !mBusy) {
                viewHolder.tv_count.setText("");
                kjb.display(viewHolder.iv_icon, mCurrentFiles[position].getPath(), R.drawable.empty_photo);
            } else if (mCurrentFiles[position].isFile() && checkEndsWithInStringArray(mCurrentFiles[position].toString(),
                    mContext.getResources().getStringArray(R.array.fileEndingImage)) && mBusy) {
                viewHolder.tv_count.setText("");
                kjb.display(viewHolder.iv_icon, mCurrentFiles[position].getPath(), R.drawable.empty_photo);
                kjb.setCallback(new BitmapCallBack() {
                    Drawable drawable2 = null;

                    @Override
                    public void onPreLoad(View view) {
                        super.onPreLoad(view);
                        drawable2 = mContext.getResources().getDrawable(R.drawable.empty_photo);
                        viewHolder.iv_icon.setImageDrawable(drawable2);
                    }
                });

            } else if (mCurrentFiles[position].isFile() && FILE_TYPE_ICONS.containsKey(fileType)) {
                viewHolder.tv_count.setText("");
                drawable = mContext.getResources().getDrawable(FILE_TYPE_ICONS.get(fileType));
//                viewHolder.iv_icon.setImageDrawable(drawable);
            } else if (mCurrentFiles[position].isFile() && !FILE_TYPE_ICONS.containsKey(fileType)) {
                viewHolder.tv_count.setText("");
                drawable = mContext.getResources().getDrawable(R.drawable.empty_photo);
//                viewHolder.iv_icon.setImageDrawable(drawable);
            }
            viewHolder.iv_icon.setImageDrawable(drawable);
            viewHolder.tv_name.setText(mCurrentFiles[position].getName());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long lastModified = mCurrentFiles[position].lastModified();
            viewHolder.tv_datetime.setText(dateFormat.format(new Date(lastModified)) + "");
        } else {
            Toast.makeText(mContext, "没有文件", Toast.LENGTH_LONG).show();
        }
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

    private boolean checkEndsWithInStringArray(String checkItsEnd, String[] fileEndings) {
        for (String aEnd : fileEndings) {
            if (checkItsEnd.endsWith(aEnd))
                return true;
        }
        return false;
    }

}
