package org.moziqi.generic.common.util;

import android.content.Context;
import android.net.Uri;
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

    public void log(String msg) {
        Log.i("FileUtils", msg);
    }

    public void toast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
