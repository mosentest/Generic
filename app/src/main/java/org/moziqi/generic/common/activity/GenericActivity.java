package org.moziqi.generic.common.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Created by moziqi on 2015/1/29 0029.
 */
public abstract class GenericActivity extends Activity implements View.OnClickListener {

    private final static String TAG = "mo_zi_qi_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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

    /**
     * 重写finish,当fragment栈中只剩最后一个fragment时才finish
     */
    @Override
    public void finish() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
