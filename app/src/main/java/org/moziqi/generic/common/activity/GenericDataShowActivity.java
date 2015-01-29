package org.moziqi.generic.common.activity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by moziqi on 2015/1/29 0029.
 */
public abstract class GenericDataShowActivity<T extends Serializable, PK extends Serializable> extends GenericActivity {
    protected abstract List<T> showAll();

    protected abstract T showOneById(PK id);
}
