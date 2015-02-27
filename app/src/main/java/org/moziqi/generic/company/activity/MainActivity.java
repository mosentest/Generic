package org.moziqi.generic.company.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;

import org.moziqi.generic.R;
import org.moziqi.generic.common.activity.GenericActivity;
import org.moziqi.generic.company.fragment.CollectFragment;
import org.moziqi.generic.company.fragment.DraftFragment;
import org.moziqi.generic.company.fragment.ExploreFragment;
import org.moziqi.generic.company.fragment.FollowFragment;
import org.moziqi.generic.company.fragment.HomeFragment;
import org.moziqi.generic.company.fragment.NavigationDrawerFragment;
import org.moziqi.generic.company.fragment.QuestionFragment;
import org.moziqi.generic.company.fragment.SearchFragment;
import org.moziqi.generic.company.fragment.SettingFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends GenericActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    /**
     * 左侧划出抽屉内部fragment
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    /**
     * 存放上次显示在action bar中的title
     */
    private CharSequence mTitle;

    private Fragment currentFragment;

    private Fragment lastFragment;

    private List<Fragment> fragments;

    private static final String ARG_SECTION_TITLE = "section_title";

    private SupportMapFragment supportMapFragment;

    private MapView mMapView;

    private BaiduMap mBaiduMap;

    private SDKReceiver mReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initUI() {
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        // 设置抽屉
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance("首页"));
        //百度LBS
        LatLng GEO_GUANGZHOU = new LatLng(23.155, 113.264);
        Point point = new Point();
        MapStatus ms = new MapStatus
                .Builder()
                .overlook(-20)
                .zoom(15)
                .target(GEO_GUANGZHOU)
                .targetScreen(point)
                .rotate(1f)
                .build();
        BaiduMapOptions bo = new BaiduMapOptions()
                .mapStatus(ms)
                .mapType(BaiduMap.MAP_TYPE_SATELLITE)
                .compassEnabled(true)
                .scaleControlEnabled(true)
                .zoomControlsEnabled(true)
                .zoomGesturesEnabled(true);
        supportMapFragment = SupportMapFragment.newInstance(bo);
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_TITLE, "发现");
        supportMapFragment.setArguments(args);
        fragments.add(supportMapFragment);
//        fragments.add(ExploreFragment.newInstance("发现", bo));
        fragments.add(FollowFragment.newInstance("关注"));
        fragments.add(CollectFragment.newInstance("收藏"));
        fragments.add(DraftFragment.newInstance("草稿"));
        fragments.add(SearchFragment.newInstance("搜索"));
        fragments.add(QuestionFragment.newInstance("提问"));
        fragments.add(SettingFragment.newInstance("设置"));

        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mReceiver = new SDKReceiver();
        registerReceiver(mReceiver, iFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView = supportMapFragment.getMapView();
        mBaiduMap = supportMapFragment.getBaiduMap();
//        mBaiduMap = mMapView.getMap();
//        //开启交通图
//        mBaiduMap.setBaiduHeatMapEnabled(true);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onNavigationDrawerItemSelected(String title) {
        String[] itemTitle = getResources().getStringArray(R.array.item_title);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        currentFragment = fragmentManager.findFragmentByTag(title);
        for (int i = 0; i < itemTitle.length; i++) {
            if (itemTitle[i].equals(title)) {
                if (currentFragment == null) {
                    currentFragment = fragments.get(i);
                    ft.add(R.id.container, currentFragment, title);
                }
                if (lastFragment != null) {
                    ft.hide(lastFragment);
                }
                if (currentFragment.isDetached()) {
                    ft.attach(currentFragment);
                }
                ft.show(currentFragment);
                lastFragment = currentFragment;
                ft.commit();
                onSectionAttached(title);
            }
        }
    }

    public void onSectionAttached(String title) {
        mTitle = title;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    public class SDKReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();
            if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
                showToast("key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
            } else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                showToast("网络出错");
            }
        }
    }
}

