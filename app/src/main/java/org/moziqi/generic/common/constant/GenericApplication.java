package org.moziqi.generic.common.constant;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by moziqi on 2015/1/29 0029.
 */
public class GenericApplication extends Application {

    private final static String TAG = "GenericApplication";

    private static GenericApplication instance;

    public static GenericApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        showLog("系统处于匮乏状态");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private void showLog(String msg) {
        showLog(TAG, msg);
    }

    private void showLog(String tag, String msg) {
        Log.e(tag, msg);
    }

}
