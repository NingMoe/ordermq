package cn.donut.ordermq.entity;

import java.util.Date;

public class MqInformation {
    private Integer id;

    private Byte changeType;

    private String dataType;

    private Integer primaryKey;

    private Integer productLine;

    private Byte isPulish;

    private Date createTime;

    private Date pushTime;

    private Date updateTime;

    private Integer failCount;

    private Byte isDelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getChangeType() {
        return changeType;
    }

    public void setChangeType(Byte changeType) {
        this.changeType = changeType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    public Integer getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Integer getProductLine() {
        return productLine;
    }

    public void setProductLine(Integer productLine) {
        this.productLine = productLine;
    }

    public Byte getIsPulish() {
        return isPulish;
    }

    public void setIsPulish(Byte isPulish) {
        this.isPulish = isPulish;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }
}