package org.moziqi.generic.common.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.moziqi.generic.R;

/**
 * Created by moziqi on 2015/1/29 0029.
 */
public class GenericFragment extends Fragment {

    private final static String TAG = "moziqi_fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public View findViewById(int paramInt) {
        return getView().findViewById(paramInt);
    }


    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    public void showLog(String msg) {
        showLog(TAG, msg);
    }

    public void showLog(String tag, String msg) {
        Log.e(tag, msg);
    }
}
