package org.moziqi.generic.common.activity;

import java.io.Serializable;

/**
 * Created by moziqi on 2015/1/29 0029.
 */
public abstract class GenericDataSaveAndUpdateActivity<T extends Serializable> extends GenericActivity {

    protected abstract boolean save(T entity);

    protected abstract boolean update(T entity);
}
