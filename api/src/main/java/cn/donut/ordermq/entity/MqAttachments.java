package cn.donut.ordermq.entity;

import java.io.Serializable;
import java.util.Date;

public class MqAttachments implements Serializable {
    private Integer id;

    private Integer mqInformId;

    //json中的key
    private String theKey;
    //json中的value
    private String theValue;

    private Date createTime;

    private Date updateTime;

    private Byte isDelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMqInformId() {
        return mqInformId;
    }

    public void setMqInformId(Integer mqInformId) {
        this.mqInformId = mqInformId;
    }

    public String getTheKey() {
        return theKey;
    }

    public void setTheKey(String theKey) {
        this.theKey = theKey == null ? null : theKey.trim();
    }

    public String getTheValue() {
        return theValue;
    }

    public void setTheValue(String theValue) {
        this.theValue = theValue == null ? null : theValue.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }
}