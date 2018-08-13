package cn.donut.ordermq.entity;

import java.io.Serializable;
import java.util.Date;

public class MqPushFailure implements Serializable {

    private Integer id;

    //内容;json
    private String message;

    //推送失败次数
    private Integer pushCount;

    //推送标记：1失败
    private Byte pushFlag;

    private Date pushTime;

    private Byte isDelete;

    //推送目标  具体调用接口
    private String pushTarget;
    //来源队列
    private String originalRoute;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Integer getPushCount() {
        return pushCount;
    }

    public void setPushCount(Integer pushCount) {
        this.pushCount = pushCount;
    }

    public Byte getPushFlag() {
        return pushFlag;
    }

    public void setPushFlag(Byte pushFlag) {
        this.pushFlag = pushFlag;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public String getPushTarget() {
        return pushTarget;
    }

    public void setPushTarget(String pushTarget) {
        this.pushTarget = pushTarget == null ? null : pushTarget.trim();
    }

    public String getOriginalRoute() {
        return originalRoute;
    }

    public void setOriginalRoute(String originalRoute) {
        this.originalRoute = originalRoute == null ? null : originalRoute.trim();
    }
}