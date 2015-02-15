package org.moziqi.generic.company.activity;

import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.moziqi.generic.R;

public class LoadingActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        //静态
//        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//        final Intent batteryIntent = this.registerReceiver(null, filter);
//        int currentBattery = getCurrentBattery(batteryIntent);
//        boolean charging = isCharging(batteryIntent);
//        Toast.makeText(LoadingActivity.this, currentBattery + "-" + charging, Toast.LENGTH_LONG).show();
        //动态注册
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MY_POWER");
        sendBroadcast(intent);
        //TODO 兼容旧版本 actionbar
        ActionBar supportActionBar = getSupportActionBar();
        setActionBar(supportActionBar);
    }

    private void setActionBar(ActionBar actionBar) {
        actionBar.setDisplayShowHomeEnabled(true);
        //不显示文字
        //actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setSubtitle("\"陈赫\"");
    }

    @Override
    public void onCreateSupportNavigateUpTaskStack(TaskStackBuilder builder) {
        super.onCreateSupportNavigateUpTaskStack(builder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loading, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    private int getCurrentBattery(final Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int battery = (int) (level / (float) scale * 100);
        return battery;
    }

    private boolean isCharging(final Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        return status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
    }
}
