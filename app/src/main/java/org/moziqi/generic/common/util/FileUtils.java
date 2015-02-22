package org.moziqi.generic.common.util;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by moziqi on 2015/2/21 0021.
 */
public final class FileUtils {
    private Context mContext;

    public FileUtils(Context mContext) {
        this.mContext = mContext;
    }

    public File getFilesDir() {
        File filesDir = mContext.getFilesDir();
        log(filesDir.getPath());
        return filesDir;
    }

    public File getCacheDir() {
        File cacheDir = mContext.getCacheDir();
        log(cacheDir.getPath());
        return cacheDir;
    }

    /**
     * 文件存在，就会抛出异常
     *
     * @param fileName
     * @param content
     * @throws Exception
     */
    public void writeByOpenFileOutput(String fileName, String content) throws Exception {
        FileOutputStream fileOutputStream = null;
        try {
            String[] strings = mContext.fileList();
            for (int i = 0; i < strings.length; i++) {
                if (strings[i].equals(fileName)) {
                    String msg = "已存在" + fileName;
//                    toast(msg);
                    throw new RuntimeException(msg);
                }
            }
            fileOutputStream = mContext.openFileOutput(fileName, Context.MODE_APPEND);
            fileOutputStream.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null)
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public String readByOpenFileInput(String fileName) {
        String content = null;
        FileInputStream fileInputStream = null;
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try {
            fileInputStream = mContext.openFileInput(fileName);
            while (fileInputStream.read(buffer) != -1) {
                arrayOutputStream.write(buffer, 0, buffer.length);
            }
            content = new String(arrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                arrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }

    /**
     * 文件路径
     *
     * @param url
     * @return
     */
    public File getTempFile(String url) {
        File file = null;
        try {
            String lastPathSegment = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(lastPathSegment, null, getCacheDir());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getAlbumStorageDir(String albumName) throws Exception {
        // Get the directory for the user's public pictures directory.
        if (!isExternalStorageWritable()) {
            throw new RuntimeException("不够权限操作SD卡");
        }
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            log("Directory not created");
        }
        return file;
    }

    public File getAlbumStoragePrivateDir(String albumName) throws Exception {
        // Get the directory for the app's private pictures directory.
        if (!isExternalStorageWritable()) {
            throw new RuntimeException("不够权限操作SD卡");
        }
        File file = new File(mContext.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            log("Directory not created");
        }
        return file;
    }

    /**
     * 获得sd卡剩余容量，即可用大小
     *
     * @return
     */
    public String getExternalStorageDirectoryFreeSpace() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(mContext, blockSize * availableBlocks);
    }

    /**
     * 获得SD卡总大小
     *
     * @return
     */
    public String getExternalStorageDirectoryTotalSpace() {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(externalStorageDirectory.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(mContext, blockSize * totalBlocks);
    }

    /**
     * 获得机身内存总大小 
     *
     * @return
     */
    private String getRomTotalSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(mContext, blockSize * totalBlocks);
    }

    /**
     * 获得机身可用内存 
     *
     * @return
     */
    private String getRomAvailableSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(mContext, blockSize * availableBlocks);
    }
    
    public void log(String msg) {
        Log.i("FileUtils", msg);
    }

    public void toast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
