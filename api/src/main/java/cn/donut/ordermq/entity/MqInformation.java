package cn.donut.ordermq.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class MqInformation implements Serializable {
    private Integer id;


    //变更类型 1.新增 2.修改 3.删除
    private Byte changeType;

    //    变更的数据类型
    private String dataType;

    //变更数据主键
    private Integer primaryKey;

    //数据对应的产品线id
    private Integer productLine;

    //是否已推送给nodejs接口 0.未推送 1.已推送
    private Byte isPulish;

    private Date createTime;

    private Date pushTime;

    private Date updateTime;

    //失败次数
    private Integer failCount;

    private Byte isDelete;

    //json中附加信息部分
    private HashMap<String, String> attachments = new HashMap<String, String>();

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

    public HashMap<String, String> getAttachments() {
        return attachments;
    }

    public void setAttachments(HashMap<String, String> attachments) {
        this.attachments = attachments;
    }
}