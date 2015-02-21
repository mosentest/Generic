package org.moziqi.generic.company.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.Button;

import org.moziqi.generic.R;
import org.moziqi.generic.common.util.FileUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment {
    private Button mButton;
    private TextView mTextView;
    private EditText mEditText;

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
        mButton = (Button) findViewById(R.id.button);
        mTextView = (TextView) findViewById(R.id.textView);
        mEditText = (EditText) findViewById(R.id.editText);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = "moziqi";
                String msg = mEditText.getText().toString();
                FileUtils fileUtils = new FileUtils(getActivity());
                try {
                    fileUtils.writeByOpenFileOutput(fileName, msg);
                } catch (Exception e) {
                    String error = "已存在" + fileName;
                    toast(error);
                    e.printStackTrace();
                }
                String content = fileUtils.readByOpenFileInput(fileName);
                mTextView.setText(content);
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
        mButton = null;
        mTextView = null;
        mEditText = null;
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
