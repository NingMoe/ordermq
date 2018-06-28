package cn.donut.ordermq.entity.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MqOrderProductExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MqOrderProductExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNull() {
            addCriterion("orderNo is null");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNotNull() {
            addCriterion("orderNo is not null");
            return (Criteria) this;
        }

        public Criteria andOrdernoEqualTo(String value) {
            addCriterion("orderNo =", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotEqualTo(String value) {
            addCriterion("orderNo <>", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThan(String value) {
            addCriterion("orderNo >", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThanOrEqualTo(String value) {
            addCriterion("orderNo >=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThan(String value) {
            addCriterion("orderNo <", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThanOrEqualTo(String value) {
            addCriterion("orderNo <=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLike(String value) {
            addCriterion("orderNo like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotLike(String value) {
            addCriterion("orderNo not like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoIn(List<String> values) {
            addCriterion("orderNo in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotIn(List<String> values) {
            addCriterion("orderNo not in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoBetween(String value1, String value2) {
            addCriterion("orderNo between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotBetween(String value1, String value2) {
            addCriterion("orderNo not between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andProductidIsNull() {
            addCriterion("productId is null");
            return (Criteria) this;
        }

        public Criteria andProductidIsNotNull() {
            addCriterion("productId is not null");
            return (Criteria) this;
        }

        public Criteria andProductidEqualTo(Integer value) {
            addCriterion("productId =", value, "productid");
            return (Criteria) this;
        }

        public Criteria andProductidNotEqualTo(Integer value) {
            addCriterion("productId <>", value, "productid");
            return (Criteria) this;
        }

        public Criteria andProductidGreaterThan(Integer value) {
            addCriterion("productId >", value, "productid");
            return (Criteria) this;
        }

        public Criteria andProductidGreaterThanOrEqualTo(Integer value) {
            addCriterion("productId >=", value, "productid");
            return (Criteria) this;
        }

        public Criteria andProductidLessThan(Integer value) {
            addCriterion("productId <", value, "productid");
            return (Criteria) this;
        }

        public Criteria andProductidLessThanOrEqualTo(Integer value) {
            addCriterion("productId <=", value, "productid");
            return (Criteria) this;
        }

        public Criteria andProductidIn(List<Integer> values) {
            addCriterion("productId in", values, "productid");
            return (Criteria) this;
        }

        public Criteria andProductidNotIn(List<Integer> values) {
            addCriterion("productId not in", values, "productid");
            return (Criteria) this;
        }

        public Criteria andProductidBetween(Integer value1, Integer value2) {
            addCriterion("productId between", value1, value2, "productid");
            return (Criteria) this;
        }

        public Criteria andProductidNotBetween(Integer value1, Integer value2) {
            addCriterion("productId not between", value1, value2, "productid");
            return (Criteria) this;
        }

        public Criteria andProductnameIsNull() {
            addCriterion("productName is null");
            return (Criteria) this;
        }

        public Criteria andProductnameIsNotNull() {
            addCriterion("productName is not null");
            return (Criteria) this;
        }

        public Criteria andProductnameEqualTo(String value) {
            addCriterion("productName =", value, "productname");
            return (Criteria) this;
        }

        public Criteria andProductnameNotEqualTo(String value) {
            addCriterion("productName <>", value, "productname");
            return (Criteria) this;
        }

        public Criteria andProductnameGreaterThan(String value) {
            addCriterion("productName >", value, "productname");
            return (Criteria) this;
        }

        public Criteria andProductnameGreaterThanOrEqualTo(String value) {
            addCriterion("productName >=", value, "productname");
            return (Criteria) this;
        }

        public Criteria andProductnameLessThan(String value) {
            addCriterion("productName <", value, "productname");
            return (Criteria) this;
        }

        public Criteria andProductnameLessThanOrEqualTo(String value) {
            addCriterion("productName <=", value, "productname");
            return (Criteria) this;
        }

        public Criteria andProductnameLike(String value) {
            addCriterion("productName like", value, "productname");
            return (Criteria) this;
        }

        public Criteria andProductnameNotLike(String value) {
            addCriterion("productName not like", value, "productname");
            return (Criteria) this;
        }

        public Criteria andProductnameIn(List<String> values) {
            addCriterion("productName in", values, "productname");
            return (Criteria) this;
        }

        public Criteria andProductnameNotIn(List<String> values) {
            addCriterion("productName not in", values, "productname");
            return (Criteria) this;
        }

        public Criteria andProductnameBetween(String value1, String value2) {
            addCriterion("productName between", value1, value2, "productname");
            return (Criteria) this;
        }

        public Criteria andProductnameNotBetween(String value1, String value2) {
            addCriterion("productName not between", value1, value2, "productname");
            return (Criteria) this;
        }

        public Criteria andProductlineIsNull() {
            addCriterion("productLine is null");
            return (Criteria) this;
        }

        public Criteria andProductlineIsNotNull() {
            addCriterion("productLine is not null");
            return (Criteria) this;
        }

        public Criteria andProductlineEqualTo(Integer value) {
            addCriterion("productLine =", value, "productline");
            return (Criteria) this;
        }

        public Criteria andProductlineNotEqualTo(Integer value) {
            addCriterion("productLine <>", value, "productline");
            return (Criteria) this;
        }

        public Criteria andProductlineGreaterThan(Integer value) {
            addCriterion("productLine >", value, "productline");
            return (Criteria) this;
        }

        public Criteria andProductlineGreaterThanOrEqualTo(Integer value) {
            addCriterion("productLine >=", value, "productline");
            return (Criteria) this;
        }

        public Criteria andProductlineLessThan(Integer value) {
            addCriterion("productLine <", value, "productline");
            return (Criteria) this;
        }

        public Criteria andProductlineLessThanOrEqualTo(Integer value) {
            addCriterion("productLine <=", value, "productline");
            return (Criteria) this;
        }

        public Criteria andProductlineIn(List<Integer> values) {
            addCriterion("productLine in", values, "productline");
            return (Criteria) this;
        }

        public Criteria andProductlineNotIn(List<Integer> values) {
            addCriterion("productLine not in", values, "productline");
            return (Criteria) this;
        }

        public Criteria andProductlineBetween(Integer value1, Integer value2) {
            addCriterion("productLine between", value1, value2, "productline");
            return (Criteria) this;
        }

        public Criteria andProductlineNotBetween(Integer value1, Integer value2) {
            addCriterion("productLine not between", value1, value2, "productline");
            return (Criteria) this;
        }

        public Criteria andProducttypeIsNull() {
            addCriterion("productType is null");
            return (Criteria) this;
        }

        public Criteria andProducttypeIsNotNull() {
            addCriterion("productType is not null");
            return (Criteria) this;
        }

        public Criteria andProducttypeEqualTo(Integer value) {
            addCriterion("productType =", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeNotEqualTo(Integer value) {
            addCriterion("productType <>", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeGreaterThan(Integer value) {
            addCriterion("productType >", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("productType >=", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeLessThan(Integer value) {
            addCriterion("productType <", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeLessThanOrEqualTo(Integer value) {
            addCriterion("productType <=", value, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeIn(List<Integer> values) {
            addCriterion("productType in", values, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeNotIn(List<Integer> values) {
            addCriterion("productType not in", values, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeBetween(Integer value1, Integer value2) {
            addCriterion("productType between", value1, value2, "producttype");
            return (Criteria) this;
        }

        public Criteria andProducttypeNotBetween(Integer value1, Integer value2) {
            addCriterion("productType not between", value1, value2, "producttype");
            return (Criteria) this;
        }

        public Criteria andExamseasonidIsNull() {
            addCriterion("examSeasonId is null");
            return (Criteria) this;
        }

        public Criteria andExamseasonidIsNotNull() {
            addCriterion("examSeasonId is not null");
            return (Criteria) this;
        }

        public Criteria andExamseasonidEqualTo(Integer value) {
            addCriterion("examSeasonId =", value, "examseasonid");
            return (Criteria) this;
        }

        public Criteria andExamseasonidNotEqualTo(Integer value) {
            addCriterion("examSeasonId <>", value, "examseasonid");
            return (Criteria) this;
        }

        public Criteria andExamseasonidGreaterThan(Integer value) {
            addCriterion("examSeasonId >", value, "examseasonid");
            return (Criteria) this;
        }

        public Criteria andExamseasonidGreaterThanOrEqualTo(Integer value) {
            addCriterion("examSeasonId >=", value, "examseasonid");
            return (Criteria) this;
        }

        public Criteria andExamseasonidLessThan(Integer value) {
            addCriterion("examSeasonId <", value, "examseasonid");
            return (Criteria) this;
        }

        public Criteria andExamseasonidLessThanOrEqualTo(Integer value) {
            addCriterion("examSeasonId <=", value, "examseasonid");
            return (Criteria) this;
        }

        public Criteria andExamseasonidIn(List<Integer> values) {
            addCriterion("examSeasonId in", values, "examseasonid");
            return (Criteria) this;
        }

        public Criteria andExamseasonidNotIn(List<Integer> values) {
            addCriterion("examSeasonId not in", values, "examseasonid");
            return (Criteria) this;
        }

        public Criteria andExamseasonidBetween(Integer value1, Integer value2) {
            addCriterion("examSeasonId between", value1, value2, "examseasonid");
            return (Criteria) this;
        }

        public Criteria andExamseasonidNotBetween(Integer value1, Integer value2) {
            addCriterion("examSeasonId not between", value1, value2, "examseasonid");
            return (Criteria) this;
        }

        public Criteria andProductstatusIsNull() {
            addCriterion("productStatus is null");
            return (Criteria) this;
        }

        public Criteria andProductstatusIsNotNull() {
            addCriterion("productStatus is not null");
            return (Criteria) this;
        }

        public Criteria andProductstatusEqualTo(Integer value) {
            addCriterion("productStatus =", value, "productstatus");
            return (Criteria) this;
        }

        public Criteria andProductstatusNotEqualTo(Integer value) {
            addCriterion("productStatus <>", value, "productstatus");
            return (Criteria) this;
        }

        public Criteria andProductstatusGreaterThan(Integer value) {
            addCriterion("productStatus >", value, "productstatus");
            return (Criteria) this;
        }

        public Criteria andProductstatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("productStatus >=", value, "productstatus");
            return (Criteria) this;
        }

        public Criteria andProductstatusLessThan(Integer value) {
            addCriterion("productStatus <", value, "productstatus");
            return (Criteria) this;
        }

        public Criteria andProductstatusLessThanOrEqualTo(Integer value) {
            addCriterion("productStatus <=", value, "productstatus");
            return (Criteria) this;
        }

        public Criteria andProductstatusIn(List<Integer> values) {
            addCriterion("productStatus in", values, "productstatus");
            return (Criteria) this;
        }

        public Criteria andProductstatusNotIn(List<Integer> values) {
            addCriterion("productStatus not in", values, "productstatus");
            return (Criteria) this;
        }

        public Criteria andProductstatusBetween(Integer value1, Integer value2) {
            addCriterion("productStatus between", value1, value2, "productstatus");
            return (Criteria) this;
        }

        public Criteria andProductstatusNotBetween(Integer value1, Integer value2) {
            addCriterion("productStatus not between", value1, value2, "productstatus");
            return (Criteria) this;
        }

        public Criteria andIsgiveproductIsNull() {
            addCriterion("isGiveProduct is null");
            return (Criteria) this;
        }

        public Criteria andIsgiveproductIsNotNull() {
            addCriterion("isGiveProduct is not null");
            return (Criteria) this;
        }

        public Criteria andIsgiveproductEqualTo(Integer value) {
            addCriterion("isGiveProduct =", value, "isgiveproduct");
            return (Criteria) this;
        }

        public Criteria andIsgiveproductNotEqualTo(Integer value) {
            addCriterion("isGiveProduct <>", value, "isgiveproduct");
            return (Criteria) this;
        }

        public Criteria andIsgiveproductGreaterThan(Integer value) {
            addCriterion("isGiveProduct >", value, "isgiveproduct");
            return (Criteria) this;
        }

        public Criteria andIsgiveproductGreaterThanOrEqualTo(Integer value) {
            addCriterion("isGiveProduct >=", value, "isgiveproduct");
            return (Criteria) this;
        }

        public Criteria andIsgiveproductLessThan(Integer value) {
            addCriterion("isGiveProduct <", value, "isgiveproduct");
            return (Criteria) this;
        }

        public Criteria andIsgiveproductLessThanOrEqualTo(Integer value) {
            addCriterion("isGiveProduct <=", value, "isgiveproduct");
            return (Criteria) this;
        }

        public Criteria andIsgiveproductIn(List<Integer> values) {
            addCriterion("isGiveProduct in", values, "isgiveproduct");
            return (Criteria) this;
        }

        public Criteria andIsgiveproductNotIn(List<Integer> values) {
            addCriterion("isGiveProduct not in", values, "isgiveproduct");
            return (Criteria) this;
        }

        public Criteria andIsgiveproductBetween(Integer value1, Integer value2) {
            addCriterion("isGiveProduct between", value1, value2, "isgiveproduct");
            return (Criteria) this;
        }

        public Criteria andIsgiveproductNotBetween(Integer value1, Integer value2) {
            addCriterion("isGiveProduct not between", value1, value2, "isgiveproduct");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceIsNull() {
            addCriterion("originalPrice is null");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceIsNotNull() {
            addCriterion("originalPrice is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceEqualTo(BigDecimal value) {
            addCriterion("originalPrice =", value, "originalprice");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceNotEqualTo(BigDecimal value) {
            addCriterion("originalPrice <>", value, "originalprice");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceGreaterThan(BigDecimal value) {
            addCriterion("originalPrice >", value, "originalprice");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("originalPrice >=", value, "originalprice");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceLessThan(BigDecimal value) {
            addCriterion("originalPrice <", value, "originalprice");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("originalPrice <=", value, "originalprice");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceIn(List<BigDecimal> values) {
            addCriterion("originalPrice in", values, "originalprice");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceNotIn(List<BigDecimal> values) {
            addCriterion("originalPrice not in", values, "originalprice");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("originalPrice between", value1, value2, "originalprice");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("originalPrice not between", value1, value2, "originalprice");
            return (Criteria) this;
        }

        public Criteria andOriginalpricenetvalueIsNull() {
            addCriterion("originalPriceNetValue is null");
            return (Criteria) this;
        }

        public Criteria andOriginalpricenetvalueIsNotNull() {
            addCriterion("originalPriceNetValue is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalpricenetvalueEqualTo(BigDecimal value) {
            addCriterion("originalPriceNetValue =", value, "originalpricenetvalue");
            return (Criteria) this;
        }

        public Criteria andOriginalpricenetvalueNotEqualTo(BigDecimal value) {
            addCriterion("originalPriceNetValue <>", value, "originalpricenetvalue");
            return (Criteria) this;
        }

        public Criteria andOriginalpricenetvalueGreaterThan(BigDecimal value) {
            addCriterion("originalPriceNetValue >", value, "originalpricenetvalue");
            return (Criteria) this;
        }

        public Criteria andOriginalpricenetvalueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("originalPriceNetValue >=", value, "originalpricenetvalue");
            return (Criteria) this;
        }

        public Criteria andOriginalpricenetvalueLessThan(BigDecimal value) {
            addCriterion("originalPriceNetValue <", value, "originalpricenetvalue");
            return (Criteria) this;
        }

        public Criteria andOriginalpricenetvalueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("originalPriceNetValue <=", value, "originalpricenetvalue");
            return (Criteria) this;
        }

        public Criteria andOriginalpricenetvalueIn(List<BigDecimal> values) {
            addCriterion("originalPriceNetValue in", values, "originalpricenetvalue");
            return (Criteria) this;
        }

        public Criteria andOriginalpricenetvalueNotIn(List<BigDecimal> values) {
            addCriterion("originalPriceNetValue not in", values, "originalpricenetvalue");
            return (Criteria) this;
        }

        public Criteria andOriginalpricenetvalueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("originalPriceNetValue between", value1, value2, "originalpricenetvalue");
            return (Criteria) this;
        }

        public Criteria andOriginalpricenetvalueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("originalPriceNetValue not between", value1, value2, "originalpricenetvalue");
            return (Criteria) this;
        }

        public Criteria andStrikepriceIsNull() {
            addCriterion("strikePrice is null");
            return (Criteria) this;
        }

        public Criteria andStrikepriceIsNotNull() {
            addCriterion("strikePrice is not null");
            return (Criteria) this;
        }

        public Criteria andStrikepriceEqualTo(BigDecimal value) {
            addCriterion("strikePrice =", value, "strikeprice");
            return (Criteria) this;
        }

        public Criteria andStrikepriceNotEqualTo(BigDecimal value) {
            addCriterion("strikePrice <>", value, "strikeprice");
            return (Criteria) this;
        }

        public Criteria andStrikepriceGreaterThan(BigDecimal value) {
            addCriterion("strikePrice >", value, "strikeprice");
            return (Criteria) this;
        }

        public Criteria andStrikepriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("strikePrice >=", value, "strikeprice");
            return (Criteria) this;
        }

        public Criteria andStrikepriceLessThan(BigDecimal value) {
            addCriterion("strikePrice <", value, "strikeprice");
            return (Criteria) this;
        }

        public Criteria andStrikepriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("strikePrice <=", value, "strikeprice");
            return (Criteria) this;
        }

        public Criteria andStrikepriceIn(List<BigDecimal> values) {
            addCriterion("strikePrice in", values, "strikeprice");
            return (Criteria) this;
        }

        public Criteria andStrikepriceNotIn(List<BigDecimal> values) {
            addCriterion("strikePrice not in", values, "strikeprice");
            return (Criteria) this;
        }

        public Criteria andStrikepriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("strikePrice between", value1, value2, "strikeprice");
            return (Criteria) this;
        }

        public Criteria andStrikepriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("strikePrice not between", value1, value2, "strikeprice");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}