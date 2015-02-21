package org.moziqi.generic.company.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by moziqi on 2015/2/21 0021.
 */
public class DrawerListItem {
    private Drawable icon;
    private String title;

    public DrawerListItem() {
    }

    public DrawerListItem(Drawable icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
