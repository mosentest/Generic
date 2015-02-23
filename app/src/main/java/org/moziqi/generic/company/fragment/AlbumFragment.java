package org.moziqi.generic.company.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.Button;

import org.moziqi.generic.R;
import org.moziqi.generic.common.UI.listView.HorizontalListView;
import org.moziqi.generic.common.util.FileUtils;
import org.moziqi.generic.company.adapter.HorizontalListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends ListFragment {
    HorizontalListView hListView;
    HorizontalListViewAdapter hListViewAdapter;
    OnHeadlineSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hListView = (HorizontalListView) findViewById(R.id.horizon_listview);
        final String[] titles = {"怀师", "南怀瑾军校", "闭关", "南怀瑾", "南公庄严照", "怀师法相","怀师", "南怀瑾军校", "闭关", "南怀瑾", "南公庄严照", "怀师法相","怀师", "南怀瑾军校", "闭关", "南怀瑾", "南公庄严照", "怀师法相"};
        hListViewAdapter = new HorizontalListViewAdapter(getActivity(), titles);
        hListView.setAdapter(hListViewAdapter);
        hListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                toast(titles[position]);
                hListViewAdapter.setSelectIndex(position);
                hListViewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public View findViewById(int paramInt) {
        return getView().findViewById(paramInt);
    }

    public void log(String msg) {
        Log.i("FileUtils", msg);
    }

    public void toast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
