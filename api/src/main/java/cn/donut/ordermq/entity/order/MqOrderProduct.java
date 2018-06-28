package cn.donut.ordermq.entity.order;

import java.math.BigDecimal;

public class MqOrderProduct {
    private Integer id;

    private String orderno;

    private Integer productid;

    private String productname;

    private Integer productline;

    private Integer producttype;

    private Integer examseasonid;

    private Integer productstatus;

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