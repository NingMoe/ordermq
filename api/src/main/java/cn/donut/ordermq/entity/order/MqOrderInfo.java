package cn.donut.ordermq.entity.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MqOrderInfo implements Serializable {
    private Integer id;
    //订单号
    private String orderNo;
    //用户Id
    private Integer userId;
    //    校园代理Id
    private Integer campusId;
    //新代理商系统代理商ID
    private Integer agentNewId;
    //新代理商系统代理商账户ID
    private Integer agentAccountId;
    //新代理商系统账户版本ID
    private Integer agentAccountVersionId;
    //新代理商系统账户ID对应的SsoId
    private String agentSsoid;
    //鲨鱼订单商户id-没啥用
    private Integer partnerId;
    //订单状态(0：未支付，1：已支付，2:取消)
    private Byte status;
    //订单创建类型,1-购买,2-换课购买,3-退课,4-换课退课
    private Byte orderType;
    //下单来源,具体参照OrderEnums.OrderSourceEnum枚举
    private Byte orderSource;
    //创建订单时间
    private Date orderTime;
    //取消订单时间
    private Date cancelTime;
    //支付时间
    private Date payTime;
    //订单原金额面值
    private BigDecimal originalPrice;
    //订单原金额净值
    private BigDecimal netValue;
    //订单实际付款金额面值
    private BigDecimal strikePrice;
    //是否是原单(0-否,1-是,默认是1)
    private Byte isBasicOrder;
    //原单id
    private Integer basicOrderId;
    //订单产品信息
    private List<MqOrderProduct> mqOrderProducts;
    //支付方式
    Map<Integer, String> payWayMap;

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

    public BigDecimal getNetValue() {
        return netValue;
    }

    public void setNetValue(BigDecimal netValue) {
        this.netValue = netValue;
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

    public List<MqOrderProduct> getMqOrderProducts() {
        return mqOrderProducts;
    }

    public void setMqOrderProducts(List<MqOrderProduct> mqOrderProducts) {
        this.mqOrderProducts = mqOrderProducts;
    }

    public Map<Integer, String> getPayWayMap() {
        return payWayMap;
    }

    public void setPayWayMap(Map<Integer, String> payWayMap) {
        this.payWayMap = payWayMap;
    }

    @Override
    public String toString() {
        return "MqOrderInfo{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", userId=" + userId +
                ", campusId=" + campusId +
                ", agentNewId=" + agentNewId +
                ", agentAccountId=" + agentAccountId +
                ", agentAccountVersionId=" + agentAccountVersionId +
                ", agentSsoid='" + agentSsoid + '\'' +
                ", partnerId=" + partnerId +
                ", status=" + status +
                ", orderType=" + orderType +
                ", orderSource=" + orderSource +
                ", orderTime=" + orderTime +
                ", cancelTime=" + cancelTime +
                ", payTime=" + payTime +
                ", originalPrice=" + originalPrice +
                ", netValue=" + netValue +
                ", strikePrice=" + strikePrice +
                ", isBasicOrder=" + isBasicOrder +
                ", basicOrderId=" + basicOrderId +
                ", mqOrderProducts=" + mqOrderProducts +
                ", payWayMap=" + payWayMap +
                '}';
    }
}