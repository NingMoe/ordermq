package cn.donut.ordermq.dto;

import com.koolearn.ordercenter.model.order.basic.OrderBasicInfo;
import com.koolearn.ordercenter.model.order.basic.OrderProductBasicInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 实体传输类
 *
 * @author luoxuzheng
 * @create 2018-08-09 18:01
 **/
public class OrderBasicInfoDto implements Serializable,Cloneable {
    private static final long serialVersionUID = 5120641970740283461L;
    private Integer id;
    private String orderNo;
    private Integer userId;
    private Integer campusId;
    private Integer agentNewId;
    private Integer agentAccountId;
    private Integer agentAccountVersionId;
    private String agentSsoId;
    private Integer partnerId;
    private int status = 0;
    private int orderType = 1;
    private int orderSource = 0;
    private Date orderTime;
    private Date cancelTime;
    private Date payTime;
    private BigDecimal originalPrice = new BigDecimal("0.00");
    private BigDecimal originalPriceNetValue = new BigDecimal("0.00");
    private BigDecimal strikePrice = new BigDecimal("0.00");
    private int isBasicOrder = 1;
    private Integer basicOrderId;
    private String originalOrderNo;
    private List<OrderProductBasicInfo> orderProductBasicInfos;
    private String url;

    public OrderBasicInfoDto() {
    }

    public OrderBasicInfoDto(OrderBasicInfo info) {
        this.id = info.getId();
        this.orderNo = info.getOrderNo();
        this.userId = info.getUserId();
        this.campusId = info.getCampusId();
        this.agentNewId = info.getAgentNewId();
        this.agentAccountId = info.getAgentAccountId();
        this.agentAccountVersionId = info.getAgentAccountVersionId();
        this.agentSsoId = info.getAgentSsoId();
        this.partnerId = info.getPartnerId();
        this.status = info.getStatus();
        this.orderType = info.getOrderType();
        this.orderSource = info.getOrderSource();
        this.orderTime = info.getOrderTime();
        this.cancelTime = info.getCancelTime();
        this.payTime = info.getPayTime();
        this.originalPrice = info.getOriginalPrice();
        this.originalPriceNetValue = info.getOriginalPriceNetValue();
        this.strikePrice = info.getStrikePrice();
        this.isBasicOrder = info.getIsBasicOrder();
        this.basicOrderId = info.getBasicOrderId();
    }

    public OrderBasicInfoDto(Integer id, String orderNo, Integer userId, Integer campusId, Integer agentNewId, Integer agentAccountId, Integer agentAccountVersionId, String agentSsoId, Integer partnerId, int status, int orderType, int orderSource, Date orderTime, Date cancelTime, Date payTime, BigDecimal originalPrice, BigDecimal originalPriceNetValue, BigDecimal strikePrice, int isBasicOrder, Integer basicOrderId, List<OrderProductBasicInfo> orderProductBasicInfos) {
        this.id = id;
        this.orderNo = orderNo;
        this.userId = userId;
        this.campusId = campusId;
        this.agentNewId = agentNewId;
        this.agentAccountId = agentAccountId;
        this.agentAccountVersionId = agentAccountVersionId;
        this.agentSsoId = agentSsoId;
        this.partnerId = partnerId;
        this.status = status;
        this.orderType = orderType;
        this.orderSource = orderSource;
        this.orderTime = orderTime;
        this.cancelTime = cancelTime;
        this.payTime = payTime;
        this.originalPrice = originalPrice;
        this.originalPriceNetValue = originalPriceNetValue;
        this.strikePrice = strikePrice;
        this.isBasicOrder = isBasicOrder;
        this.basicOrderId = basicOrderId;
        this.orderProductBasicInfos = orderProductBasicInfos;
    }

    public String getOriginalOrderNo() {
        return originalOrderNo;
    }

    public void setOriginalOrderNo(String originalOrderNo) {
        this.originalOrderNo = originalOrderNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCampusId() {
        return campusId;
    }

    public void setCampusId(Integer campusId) {
        this.campusId = campusId;
    }

    public Integer getAgentNewId() {
        return agentNewId;
    }

    public void setAgentNewId(Integer agentNewId) {
        this.agentNewId = agentNewId;
    }

    public Integer getAgentAccountId() {
        return agentAccountId;
    }

    public void setAgentAccountId(Integer agentAccountId) {
        this.agentAccountId = agentAccountId;
    }

    public Integer getAgentAccountVersionId() {
        return agentAccountVersionId;
    }

    public void setAgentAccountVersionId(Integer agentAccountVersionId) {
        this.agentAccountVersionId = agentAccountVersionId;
    }

    public String getAgentSsoId() {
        return agentSsoId;
    }

    public void setAgentSsoId(String agentSsoId) {
        this.agentSsoId = agentSsoId;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(int orderSource) {
        this.orderSource = orderSource;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getOriginalPriceNetValue() {
        return originalPriceNetValue;
    }

    public void setOriginalPriceNetValue(BigDecimal originalPriceNetValue) {
        this.originalPriceNetValue = originalPriceNetValue;
    }

    public BigDecimal getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(BigDecimal strikePrice) {
        this.strikePrice = strikePrice;
    }

    public int getIsBasicOrder() {
        return isBasicOrder;
    }

    public void setIsBasicOrder(int isBasicOrder) {
        this.isBasicOrder = isBasicOrder;
    }

    public Integer getBasicOrderId() {
        return basicOrderId;
    }

    public void setBasicOrderId(Integer basicOrderId) {
        this.basicOrderId = basicOrderId;
    }

    public List<OrderProductBasicInfo> getOrderProductBasicInfos() {
        return orderProductBasicInfos;
    }

    public void setOrderProductBasicInfos(List<OrderProductBasicInfo> orderProductBasicInfos) {
        this.orderProductBasicInfos = orderProductBasicInfos;
    }

    public void setUrl(String url) {
        this.url=url;
    }
    public String getUrl(){
        return this.url;
    }

    @Override
    public String toString() {
        return "OrderBasicInfoDto{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", userId=" + userId +
                ", campusId=" + campusId +
                ", agentNewId=" + agentNewId +
                ", agentAccountId=" + agentAccountId +
                ", agentAccountVersionId=" + agentAccountVersionId +
                ", agentSsoId='" + agentSsoId + '\'' +
                ", partnerId=" + partnerId +
                ", status=" + status +
                ", orderType=" + orderType +
                ", orderSource=" + orderSource +
                ", orderTime=" + orderTime +
                ", cancelTime=" + cancelTime +
                ", payTime=" + payTime +
                ", originalPrice=" + originalPrice +
                ", originalPriceNetValue=" + originalPriceNetValue +
                ", strikePrice=" + strikePrice +
                ", isBasicOrder=" + isBasicOrder +
                ", basicOrderId=" + basicOrderId +
                ", originalOrderNo='" + originalOrderNo + '\'' +
                ", orderProductBasicInfos=" + orderProductBasicInfos +
                ", url='" + url + '\'' +
                '}';
    }
}
