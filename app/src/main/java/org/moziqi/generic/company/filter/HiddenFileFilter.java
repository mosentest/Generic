package org.moziqi.generic.company.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by moziqi on 2015/2/25 0025.
 */
public class HiddenFileFilter implements FileFilter {

    private static HiddenFileFilter instance;

    private HiddenFileFilter() {
    }

    public static HiddenFileFilter getInstance() {
        if (instance == null) {
            synchronized (HiddenFileFilter.class) {
                if (instance == null) {
                    instance = new HiddenFileFilter();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean accept(File pathname) {
        return !pathname.isHidden();
    }
}
