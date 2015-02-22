package org.moziqi.generic.company.entity;

import java.io.Serializable;

/**
 * Created by moziqi on 2015/2/22 0022.
 */
public abstract class BaseContract implements Serializable{
    protected Long id;
    protected String createDate;
    protected String updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
