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
import com.gc.materialdesign.views.ButtonRectangle;

import org.moziqi.generic.R;
import org.moziqi.generic.common.UI.listView.HorizontalListView;
import org.moziqi.generic.common.util.FileUtils;
import org.moziqi.generic.company.adapter.FileListAdapter;
import org.moziqi.generic.company.adapter.HorizontalListViewAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment {

    private ListView lv_file;

    private FileListAdapter fileListAdapter;

    private HorizontalListView hListView;

    private HorizontalListViewAdapter hListViewAdapter;

    private OnHeadlineSelectedListener mCallback;

    // 记录当前的父文件夹
    private File currentParent;

    // 记录当前路径下的所有文件夹的文件数组
    private File[] currentFiles;
    private List<String> titles;
    private StringBuffer stringBuffer;


    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentParent = FileUtils.getSDPath();
//        log(currentParent.getPath());
        titles = new ArrayList<>();
        titles.add(currentParent.getName());
        hListViewAdapter = new HorizontalListViewAdapter(getActivity(), titles);
        currentFiles = currentParent.listFiles();
        fileListAdapter = new FileListAdapter(getActivity(), currentFiles);
    }

    @Override
    public void onPause() {
        super.onPause();
        log("onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        log("onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        log("onStop");
    }

    @Override
    public void onStart() {
        super.onStart();
        log("onStart");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        log("onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        log("onDetach");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        log("onCreateView");
        return inflater.inflate(R.layout.fragment_album, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        log("onActivityCreated");
        hListView = (HorizontalListView) findViewById(R.id.horizon_listview);
        hListView.setAdapter(hListViewAdapter);
        lv_file = (ListView) findViewById(R.id.lv_file);
        lv_file.setAdapter(fileListAdapter);
        //先初始化，后才能更新数据

        hListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取路径
                stringBuffer = new StringBuffer();
                stringBuffer.append("/storage/");
                for (int i = 0; i < position; i++) {
                    stringBuffer.append(titles.get(i));
                    stringBuffer.append("/");
                }
                stringBuffer.append(titles.get(position));
//                log(stringBuffer.toString());
                currentParent = new File(stringBuffer.toString());
                currentFiles = currentParent.listFiles();
                //根据导航条更新下面的列表
                fileListAdapter.updateData(currentFiles);
                //更新导航条
                for (int i = titles.size() - 1; i > position; i--) {
                    titles.remove(i);
                }
                hListViewAdapter.setSelectIndex(position);
                hListViewAdapter.notifyDataSetChanged();

            }
        });
        lv_file.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentFiles[position].isFile()) {
                    return;
                }
                File[] files = currentFiles[position].listFiles();
                if (files == null || files.length == 0) {
                    toast("当前路径不可访问或者该路径下没有文件");
                } else {
                    // 获取用户单击的列表项对应的文件夹，设为当前的父文件夹
                    currentParent = currentFiles[position];
                    titles.add(currentParent.getName());
                    hListViewAdapter.notifyDataSetChanged();
                    currentFiles = files;
                    fileListAdapter.updateData(currentFiles);
                }
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
        lv_file = null;
        hListView = null;
        hListViewAdapter = null;
        mCallback = null;
        stringBuffer = null;
    }

    public View findViewById(int paramInt) {
        return getView().findViewById(paramInt);
    }

    public void log(String msg) {
        Log.i("af", msg);
    }

    public void toast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
