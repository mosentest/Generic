package org.moziqi.generic.company.activity;

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

import org.moziqi.generic.R;
import org.moziqi.generic.common.activity.GenericActivity;
import org.moziqi.generic.company.fragment.CollectFragment;
import org.moziqi.generic.company.fragment.DraftFragment;
import org.moziqi.generic.company.fragment.ExploreFragment;
import org.moziqi.generic.company.fragment.FollowFragment;
import org.moziqi.generic.company.fragment.HomeFragment;
import org.moziqi.generic.company.fragment.LoginFragment;
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
        fragments = new ArrayList<Fragment>();
        fragments.add(HomeFragment.newInstance("首页"));
        fragments.add(ExploreFragment.newInstance("发现"));
        fragments.add(FollowFragment.newInstance("关注"));
        fragments.add(CollectFragment.newInstance("收藏"));
        fragments.add(DraftFragment.newInstance("草稿"));
        fragments.add(SearchFragment.newInstance("搜索"));
        fragments.add(QuestionFragment.newInstance("提问"));
        fragments.add(SettingFragment.newInstance("设置"));
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

    /**
     * 内容fragment
     */
    public static class ContentFragment extends Fragment {

        private static final String ARG_SECTION_TITLE = "section_title";

        /**
         * 返回根据title参数创建的fragment
         */
        public static ContentFragment newInstance(String title) {
            ContentFragment fragment = new ContentFragment();
            Bundle args = new Bundle();
            args.putString(ARG_SECTION_TITLE, title);
            fragment.setArguments(args);
            return fragment;
        }

        public ContentFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getArguments().getString(ARG_SECTION_TITLE));
            return rootView;
        }
    }
}

