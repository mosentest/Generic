package org.moziqi.generic.company.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.CheckBox;
import com.gc.materialdesign.views.Switch;

import org.moziqi.generic.R;
import org.moziqi.generic.common.fragment.GenericFragment;


public class ExploreFragment extends GenericFragment implements View.OnClickListener, OnGetPoiSearchResultListener, OnGetSuggestionResultListener {

    private static final String ARG_SECTION_TITLE = "section_title";
    private static final String MAP_TYPE_NORMAL = "普通地图";
    private static final String MAP_TYPE_SATELLITE = "卫星地图";
    private boolean mapTypeStatus = false;

    private MapView mMapView = null;
    private ButtonFlat mButtonFlat = null;
    private BaiduMap mBaiduMap = null;
    private CheckBox cb_traffic = null;
    private CheckBox cb_heat_map = null;

    private PoiSearch mPoiSearch = null;
    private SuggestionSearch mSuggestionSearch = null;

    /**
     * 搜索关键字输入窗口
     */
    private AutoCompleteTextView keyWorldsView = null;
    private ArrayAdapter<String> sugAdapter = null;
    private int load_Index = 0;

    /**
     * 返回根据title参数创建的fragment
     */
    public static ExploreFragment newInstance(String title) {
        ExploreFragment fragment = new ExploreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //UI初始化
        mMapView = (MapView) findViewById(R.id.mv_map);
        mBaiduMap = mMapView.getMap();
        mPoiSearch = PoiSearch.newInstance();
        mButtonFlat = (ButtonFlat) findViewById(R.id.btn_map_type);
        cb_traffic = (CheckBox) findViewById(R.id.cb_traffic);
        cb_heat_map = (CheckBox) findViewById(R.id.cb_heat_map);
        //UI监听事件
        mButtonFlat.setOnClickListener(this);
        cb_traffic.setOncheckListener(new CheckBox.OnCheckListener() {
            @Override
            public void onCheck(boolean b) {
                //开启交通图
                mBaiduMap.setTrafficEnabled(b);
            }
        });
        cb_heat_map.setOncheckListener(new CheckBox.OnCheckListener() {
            @Override
            public void onCheck(boolean b) {
                //开启交通图
                mBaiduMap.setBaiduHeatMapEnabled(b);
            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if (mMapView != null) {
            mMapView.onDestroy();
        }
        if (mPoiSearch != null) {
            mPoiSearch.destroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_map_type:
                if (mapTypeStatus) {
                    //普通地图
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    mButtonFlat.setText(MAP_TYPE_NORMAL);
                    mapTypeStatus = false;
                } else {
                    //卫星地图
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                    mButtonFlat.setText(MAP_TYPE_SATELLITE);
                    mapTypeStatus = true;
                }
        }
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {

    }
}
