package org.moziqi.generic.common.constant;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
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

    /**
     * 打印土司
     *
     * @param msg
     */
    public static void showToast(String msg) {
        Toast.makeText(instance, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 打印日志
     *
     * @param msg
     */
    public static void showLog(String msg) {
        showLog(TAG, msg);
    }

    public static void showLog(String tag, String msg) {
        Log.e(tag, msg);
    }

    /**
     * 获取局部广播器
     *
     * @return
     */
    public static LocalBroadcastManager getLocalBroadcastManager() {
        LocalBroadcastManager instance1 = LocalBroadcastManager.getInstance(instance);
        return instance1;
    }

    public SharedPreferences sharePreference(String name, int mode) {
        SharedPreferences sharedPreferences = getSharedPreferences(name, mode);
        return sharedPreferences;
    }

    private SharedPreferences.Editor userEditor() {
        SharedPreferences user = sharePreference("user", MODE_PRIVATE);
        SharedPreferences.Editor edit = user.edit();
        return edit;
    }

    public void saveNewUserInfo(String username, String password) {
        SharedPreferences.Editor edit = userEditor();
        edit.putString("username", username);
        edit.putString("password", password);
        edit.commit();
    }

    public void deleteCurrentUserInfo() {
        SharedPreferences.Editor edit = userEditor();
        edit.remove("username");
        edit.remove("password");
        edit.commit();
    }
}
