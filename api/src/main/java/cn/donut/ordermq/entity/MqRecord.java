package cn.donut.ordermq.entity;

import java.util.Date;

public class MqRecord {
    private Integer id;

    private String jsonContent;

    private String routingKey;

    private Date createTime;

    private Byte persist;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJsonContent() {
        return jsonContent;
    }

    public void setJsonContent(String jsonContent) {
        this.jsonContent = jsonContent == null ? null : jsonContent.trim();
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey == null ? null : routingKey.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getPersist() {
        return persist;
    }

    public void setPersist(Byte persist) {
        this.persist = persist;
    }
}