package org.moziqi.generic.common.UI.bar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.moziqi.generic.R;

/**
 * Created by moziqi on 2015/2/9 0009.
 */
public class TopBar extends LinearLayout {

    private Button leftButton, rightButton;
    private TextView textView;

    private LayoutParams leftParams, rightParams, textViewParams;

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTopBar, defStyleAttr, 0);
        leftButton = new Button(context);
        leftButton.setBackgroundColor(typedArray.getColor(R.styleable.MyTopBar_leftBG, 0));
        leftButton.setText(typedArray.getString(R.styleable.MyTopBar_leftTitle));
        typedArray.recycle();
        leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        addView(leftButton, leftParams);
    }

}
