package org.moziqi.generic.company.fragment;

import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.SupportMapFragment;


public class ExploreFragment extends SupportMapFragment {

    private static final String ARG_SECTION_TITLE = "section_title";

    /**
     * 返回根据title参数创建的fragment
     */
    public static SupportMapFragment newI2nstance(String title, com.baidu.mapapi.map.BaiduMapOptions baiduMapOptions) {
        SupportMapFragment supportMapFragment = newInstance(baiduMapOptions);
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_TITLE, title);
        supportMapFragment.setArguments(args);
        supportMapFragment.getMapView().getMap().setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        return supportMapFragment;
    }

public void f(){
    SupportMapFragment supportMapFragment = ExploreFragment.newInstance();
}
}
