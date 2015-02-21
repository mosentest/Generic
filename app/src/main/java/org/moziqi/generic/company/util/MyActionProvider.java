package org.moziqi.generic.company.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import org.moziqi.generic.R;
import org.moziqi.generic.company.activity.MainActivity;

/**
 * Created by moziqi on 2015/2/19 0019.
 */
public class MyActionProvider extends ActionProvider {
    private Context mContext;

    public MyActionProvider(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public View onCreateActionView() {

        // Inflate the action view to be shown on the action bar.
//        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
//        View view = layoutInflater.inflate(R.layout.action_provider_my, null);
//        ImageButton button = (ImageButton) view.findViewById(R.id.img_btn_one);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "点击1", Toast.LENGTH_LONG).show();
//            }
//        });
//        return view;
        return null;
    }

    @Override
    public boolean onPerformDefaultAction() {
        return super.onPerformDefaultAction();
    }

    @Override
    public void onPrepareSubMenu(SubMenu subMenu) {
        subMenu.clear();
        subMenu.add("跳转Draw").setIcon(R.drawable.ic_launcher)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
//                        Intent mIntent = new Intent(mContext, MainActivity.class);
//                        mContext.startActivity(mIntent);
                        return true;
                    }
                });
        subMenu.add("material design").setIcon(R.drawable.ic_launcher)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return false;
                    }
                });
    }

    @Override
    public boolean hasSubMenu() {
        return true;
    }

}
