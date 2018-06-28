package cn.donut.ordermq.entity.order;

import java.math.BigDecimal;
import java.util.Date;

public class MqOrderInfo {
    private Integer id;

    private String orderNo;

    private Integer userId;

    private Integer campusId;

    private Integer agentNewId;

    private Integer agentAccountId;

    private Integer agentAccountVersionId;

    private String agentSsoid;

    private Integer partnerId;

    private Byte status;

    private Byte orderType;

    private Byte orderSource;

    private Date orderTime;

    private Date cancelTime;

    private Date payTime;

    private BigDecimal originalPrice;

    private BigDecimal originalPriceNetValue;

    private BigDecimal strikePrice;

    private Byte isBasicOrder;

    private Integer basicOrderId;

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
        this.orderNo = orderNo == null ? null : orderNo.trim();
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

    public String getAgentSsoid() {
        return agentSsoid;
    }

    public void setAgentSsoid(String agentSsoid) {
        this.agentSsoid = agentSsoid == null ? null : agentSsoid.trim();
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getOrderType() {
        return orderType;
    }

    public void setOrderType(Byte orderType) {
        this.orderType = orderType;
    }

    public Byte getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(Byte orderSource) {
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

    public Byte getIsBasicOrder() {
        return isBasicOrder;
    }

    public void setIsBasicOrder(Byte isBasicOrder) {
        this.isBasicOrder = isBasicOrder;
    }

    public Integer getBasicOrderId() {
        return basicOrderId;
    }

    public void setBasicOrderId(Integer basicOrderId) {
        this.basicOrderId = basicOrderId;
    }
}