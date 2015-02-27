package org.moziqi.generic.company.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.SupportMapFragment;

import org.moziqi.generic.R;
import org.moziqi.generic.common.constant.GenericApplication;
import org.moziqi.generic.common.fragment.GenericFragment;


public class ExploreFragment extends SupportMapFragment {


    private static final String ARG_SECTION_TITLE = "section_title";

    /**
     * 返回根据title参数创建的fragment
     */
    public static SupportMapFragment newInstance(String title, com.baidu.mapapi.map.BaiduMapOptions baiduMapOptions) {
        SupportMapFragment supportMapFragment = newInstance(baiduMapOptions);
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_TITLE, title);
        supportMapFragment.setArguments(args);
        return supportMapFragment;
    }
}
