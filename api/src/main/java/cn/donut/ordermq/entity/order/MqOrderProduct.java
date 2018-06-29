package cn.donut.ordermq.entity.order;

import java.io.Serializable;
import java.math.BigDecimal;

public class MqOrderProduct implements Serializable {
    private Integer id;

    private String orderno;

    //产品ID
    private Integer productid;

    private String productname;

    private Integer productline;

    //产品类型
    private Integer producttype;
    //产品考季id
    private Integer examseasonid;
    //产品状态：0：未支付,1：已支付,2:取消;3:冻结;4:退课中;5:已退课;6:过期;7:休学中;8:换课中;9:已换课
    private Integer productstatus;
    //是否是赠送产品：是否是赠送产品，0：否，1：是
    private Integer isgiveproduct;

    private BigDecimal originalprice;

    private BigDecimal originalpricenetvalue;

    private BigDecimal strikeprice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname == null ? null : productname.trim();
    }

    public Integer getProductline() {
        return productline;
    }

    public void setProductline(Integer productline) {
        this.productline = productline;
    }

    public Integer getProducttype() {
        return producttype;
    }

    public void setProducttype(Integer producttype) {
        this.producttype = producttype;
    }

    public Integer getExamseasonid() {
        return examseasonid;
    }

    public void setExamseasonid(Integer examseasonid) {
        this.examseasonid = examseasonid;
    }

    public Integer getProductstatus() {
        return productstatus;
    }

    public void setProductstatus(Integer productstatus) {
        this.productstatus = productstatus;
    }

    public Integer getIsgiveproduct() {
        return isgiveproduct;
    }

    public void setIsgiveproduct(Integer isgiveproduct) {
        this.isgiveproduct = isgiveproduct;
    }

    public BigDecimal getOriginalprice() {
        return originalprice;
    }

    public void setOriginalprice(BigDecimal originalprice) {
        this.originalprice = originalprice;
    }

    public BigDecimal getOriginalpricenetvalue() {
        return originalpricenetvalue;
    }

    public void setOriginalpricenetvalue(BigDecimal originalpricenetvalue) {
        this.originalpricenetvalue = originalpricenetvalue;
    }

    public BigDecimal getStrikeprice() {
        return strikeprice;
    }

    public void setStrikeprice(BigDecimal strikeprice) {
        this.strikeprice = strikeprice;
    }
}