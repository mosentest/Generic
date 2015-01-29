package org.moziqi.generic.common.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Created by moziqi on 2015/1/29 0029.
 */
public abstract class GenericActivity extends Activity {

    private final static String TAG = "mo_zi_qi_activity";

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initUI();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backPressedEvent();
    }

    protected abstract void initUI();

    protected abstract void backPressedEvent();

    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void showLog(String msg) {
        showLog(TAG, msg);
    }

    public void showLog(String tag, String msg) {
        Log.e(tag, msg);
    }
}
