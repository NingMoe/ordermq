package cn.donut.ordermq.entity.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MqOrderInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MqOrderInfoExample() {
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

        public Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("order_no <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("order_no >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("order_no <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("order_no like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("order_no not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("order_no in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andCampusIdIsNull() {
            addCriterion("campus_id is null");
            return (Criteria) this;
        }

        public Criteria andCampusIdIsNotNull() {
            addCriterion("campus_id is not null");
            return (Criteria) this;
        }

        public Criteria andCampusIdEqualTo(Integer value) {
            addCriterion("campus_id =", value, "campusId");
            return (Criteria) this;
        }

        public Criteria andCampusIdNotEqualTo(Integer value) {
            addCriterion("campus_id <>", value, "campusId");
            return (Criteria) this;
        }

        public Criteria andCampusIdGreaterThan(Integer value) {
            addCriterion("campus_id >", value, "campusId");
            return (Criteria) this;
        }

        public Criteria andCampusIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("campus_id >=", value, "campusId");
            return (Criteria) this;
        }

        public Criteria andCampusIdLessThan(Integer value) {
            addCriterion("campus_id <", value, "campusId");
            return (Criteria) this;
        }

        public Criteria andCampusIdLessThanOrEqualTo(Integer value) {
            addCriterion("campus_id <=", value, "campusId");
            return (Criteria) this;
        }

        public Criteria andCampusIdIn(List<Integer> values) {
            addCriterion("campus_id in", values, "campusId");
            return (Criteria) this;
        }

        public Criteria andCampusIdNotIn(List<Integer> values) {
            addCriterion("campus_id not in", values, "campusId");
            return (Criteria) this;
        }

        public Criteria andCampusIdBetween(Integer value1, Integer value2) {
            addCriterion("campus_id between", value1, value2, "campusId");
            return (Criteria) this;
        }

        public Criteria andCampusIdNotBetween(Integer value1, Integer value2) {
            addCriterion("campus_id not between", value1, value2, "campusId");
            return (Criteria) this;
        }

        public Criteria andAgentNewIdIsNull() {
            addCriterion("agent_new_id is null");
            return (Criteria) this;
        }

        public Criteria andAgentNewIdIsNotNull() {
            addCriterion("agent_new_id is not null");
            return (Criteria) this;
        }

        public Criteria andAgentNewIdEqualTo(Integer value) {
            addCriterion("agent_new_id =", value, "agentNewId");
            return (Criteria) this;
        }

        public Criteria andAgentNewIdNotEqualTo(Integer value) {
            addCriterion("agent_new_id <>", value, "agentNewId");
            return (Criteria) this;
        }

        public Criteria andAgentNewIdGreaterThan(Integer value) {
            addCriterion("agent_new_id >", value, "agentNewId");
            return (Criteria) this;
        }

        public Criteria andAgentNewIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("agent_new_id >=", value, "agentNewId");
            return (Criteria) this;
        }

        public Criteria andAgentNewIdLessThan(Integer value) {
            addCriterion("agent_new_id <", value, "agentNewId");
            return (Criteria) this;
        }

        public Criteria andAgentNewIdLessThanOrEqualTo(Integer value) {
            addCriterion("agent_new_id <=", value, "agentNewId");
            return (Criteria) this;
        }

        public Criteria andAgentNewIdIn(List<Integer> values) {
            addCriterion("agent_new_id in", values, "agentNewId");
            return (Criteria) this;
        }

        public Criteria andAgentNewIdNotIn(List<Integer> values) {
            addCriterion("agent_new_id not in", values, "agentNewId");
            return (Criteria) this;
        }

        public Criteria andAgentNewIdBetween(Integer value1, Integer value2) {
            addCriterion("agent_new_id between", value1, value2, "agentNewId");
            return (Criteria) this;
        }

        public Criteria andAgentNewIdNotBetween(Integer value1, Integer value2) {
            addCriterion("agent_new_id not between", value1, value2, "agentNewId");
            return (Criteria) this;
        }

        public Criteria andAgentAccountIdIsNull() {
            addCriterion("agent_account_id is null");
            return (Criteria) this;
        }

        public Criteria andAgentAccountIdIsNotNull() {
            addCriterion("agent_account_id is not null");
            return (Criteria) this;
        }

        public Criteria andAgentAccountIdEqualTo(Integer value) {
            addCriterion("agent_account_id =", value, "agentAccountId");
            return (Criteria) this;
        }

        public Criteria andAgentAccountIdNotEqualTo(Integer value) {
            addCriterion("agent_account_id <>", value, "agentAccountId");
            return (Criteria) this;
        }

        public Criteria andAgentAccountIdGreaterThan(Integer value) {
            addCriterion("agent_account_id >", value, "agentAccountId");
            return (Criteria) this;
        }

        public Criteria andAgentAccountIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("agent_account_id >=", value, "agentAccountId");
            return (Criteria) this;
        }

        public Criteria andAgentAccountIdLessThan(Integer value) {
            addCriterion("agent_account_id <", value, "agentAccountId");
            return (Criteria) this;
        }

        public Criteria andAgentAccountIdLessThanOrEqualTo(Integer value) {
            addCriterion("agent_account_id <=", value, "agentAccountId");
            return (Criteria) this;
        }

        public Criteria andAgentAccountIdIn(List<Integer> values) {
            addCriterion("agent_account_id in", values, "agentAccountId");
            return (Criteria) this;
        }

        public Criteria andAgentAccountIdNotIn(List<Integer> values) {
            addCriterion("agent_account_id not in", values, "agentAccountId");
            return (Criteria) this;
        }

        public Criteria andAgentAccountIdBetween(Integer value1, Integer value2) {
            addCriterion("agent_account_id between", value1, value2, "agentAccountId");
            return (Criteria) this;
        }

        public Criteria andAgentAccountIdNotBetween(Integer value1, Integer value2) {
            addCriterion("agent_account_id not between", value1, value2, "agentAccountId");
            return (Criteria) this;
        }

        public Criteria andAgentAccountVersionIdIsNull() {
            addCriterion("agent_account_version_id is null");
            return (Criteria) this;
        }

        public Criteria andAgentAccountVersionIdIsNotNull() {
            addCriterion("agent_account_version_id is not null");
            return (Criteria) this;
        }

        public Criteria andAgentAccountVersionIdEqualTo(Integer value) {
            addCriterion("agent_account_version_id =", value, "agentAccountVersionId");
            return (Criteria) this;
        }

        public Criteria andAgentAccountVersionIdNotEqualTo(Integer value) {
            addCriterion("agent_account_version_id <>", value, "agentAccountVersionId");
            return (Criteria) this;
        }

        public Criteria andAgentAccountVersionIdGreaterThan(Integer value) {
            addCriterion("agent_account_version_id >", value, "agentAccountVersionId");
            return (Criteria) this;
        }

        public Criteria andAgentAccountVersionIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("agent_account_version_id >=", value, "agentAccountVersionId");
            return (Criteria) this;
        }

        public Criteria andAgentAccountVersionIdLessThan(Integer value) {
            addCriterion("agent_account_version_id <", value, "agentAccountVersionId");
            return (Criteria) this;
        }

        public Criteria andAgentAccountVersionIdLessThanOrEqualTo(Integer value) {
            addCriterion("agent_account_version_id <=", value, "agentAccountVersionId");
            return (Criteria) this;
        }

        public Criteria andAgentAccountVersionIdIn(List<Integer> values) {
            addCriterion("agent_account_version_id in", values, "agentAccountVersionId");
            return (Criteria) this;
        }

        public Criteria andAgentAccountVersionIdNotIn(List<Integer> values) {
            addCriterion("agent_account_version_id not in", values, "agentAccountVersionId");
            return (Criteria) this;
        }

        public Criteria andAgentAccountVersionIdBetween(Integer value1, Integer value2) {
            addCriterion("agent_account_version_id between", value1, value2, "agentAccountVersionId");
            return (Criteria) this;
        }

        public Criteria andAgentAccountVersionIdNotBetween(Integer value1, Integer value2) {
            addCriterion("agent_account_version_id not between", value1, value2, "agentAccountVersionId");
            return (Criteria) this;
        }

        public Criteria andAgentSsoidIsNull() {
            addCriterion("agent_ssoId is null");
            return (Criteria) this;
        }

        public Criteria andAgentSsoidIsNotNull() {
            addCriterion("agent_ssoId is not null");
            return (Criteria) this;
        }

        public Criteria andAgentSsoidEqualTo(String value) {
            addCriterion("agent_ssoId =", value, "agentSsoid");
            return (Criteria) this;
        }

        public Criteria andAgentSsoidNotEqualTo(String value) {
            addCriterion("agent_ssoId <>", value, "agentSsoid");
            return (Criteria) this;
        }

        public Criteria andAgentSsoidGreaterThan(String value) {
            addCriterion("agent_ssoId >", value, "agentSsoid");
            return (Criteria) this;
        }

        public Criteria andAgentSsoidGreaterThanOrEqualTo(String value) {
            addCriterion("agent_ssoId >=", value, "agentSsoid");
            return (Criteria) this;
        }

        public Criteria andAgentSsoidLessThan(String value) {
            addCriterion("agent_ssoId <", value, "agentSsoid");
            return (Criteria) this;
        }

        public Criteria andAgentSsoidLessThanOrEqualTo(String value) {
            addCriterion("agent_ssoId <=", value, "agentSsoid");
            return (Criteria) this;
        }

        public Criteria andAgentSsoidLike(String value) {
            addCriterion("agent_ssoId like", value, "agentSsoid");
            return (Criteria) this;
        }

        public Criteria andAgentSsoidNotLike(String value) {
            addCriterion("agent_ssoId not like", value, "agentSsoid");
            return (Criteria) this;
        }

        public Criteria andAgentSsoidIn(List<String> values) {
            addCriterion("agent_ssoId in", values, "agentSsoid");
            return (Criteria) this;
        }

        public Criteria andAgentSsoidNotIn(List<String> values) {
            addCriterion("agent_ssoId not in", values, "agentSsoid");
            return (Criteria) this;
        }

        public Criteria andAgentSsoidBetween(String value1, String value2) {
            addCriterion("agent_ssoId between", value1, value2, "agentSsoid");
            return (Criteria) this;
        }

        public Criteria andAgentSsoidNotBetween(String value1, String value2) {
            addCriterion("agent_ssoId not between", value1, value2, "agentSsoid");
            return (Criteria) this;
        }

        public Criteria andPartnerIdIsNull() {
            addCriterion("partner_id is null");
            return (Criteria) this;
        }

        public Criteria andPartnerIdIsNotNull() {
            addCriterion("partner_id is not null");
            return (Criteria) this;
        }

        public Criteria andPartnerIdEqualTo(Integer value) {
            addCriterion("partner_id =", value, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdNotEqualTo(Integer value) {
            addCriterion("partner_id <>", value, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdGreaterThan(Integer value) {
            addCriterion("partner_id >", value, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("partner_id >=", value, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdLessThan(Integer value) {
            addCriterion("partner_id <", value, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdLessThanOrEqualTo(Integer value) {
            addCriterion("partner_id <=", value, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdIn(List<Integer> values) {
            addCriterion("partner_id in", values, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdNotIn(List<Integer> values) {
            addCriterion("partner_id not in", values, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdBetween(Integer value1, Integer value2) {
            addCriterion("partner_id between", value1, value2, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("partner_id not between", value1, value2, "partnerId");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNull() {
            addCriterion("order_type is null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNotNull() {
            addCriterion("order_type is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeEqualTo(Byte value) {
            addCriterion("order_type =", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotEqualTo(Byte value) {
            addCriterion("order_type <>", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThan(Byte value) {
            addCriterion("order_type >", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("order_type >=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThan(Byte value) {
            addCriterion("order_type <", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThanOrEqualTo(Byte value) {
            addCriterion("order_type <=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIn(List<Byte> values) {
            addCriterion("order_type in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotIn(List<Byte> values) {
            addCriterion("order_type not in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeBetween(Byte value1, Byte value2) {
            addCriterion("order_type between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("order_type not between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderSourceIsNull() {
            addCriterion("order_source is null");
            return (Criteria) this;
        }

        public Criteria andOrderSourceIsNotNull() {
            addCriterion("order_source is not null");
            return (Criteria) this;
        }

        public Criteria andOrderSourceEqualTo(Byte value) {
            addCriterion("order_source =", value, "orderSource");
            return (Criteria) this;
        }

        public Criteria andOrderSourceNotEqualTo(Byte value) {
            addCriterion("order_source <>", value, "orderSource");
            return (Criteria) this;
        }

        public Criteria andOrderSourceGreaterThan(Byte value) {
            addCriterion("order_source >", value, "orderSource");
            return (Criteria) this;
        }

        public Criteria andOrderSourceGreaterThanOrEqualTo(Byte value) {
            addCriterion("order_source >=", value, "orderSource");
            return (Criteria) this;
        }

        public Criteria andOrderSourceLessThan(Byte value) {
            addCriterion("order_source <", value, "orderSource");
            return (Criteria) this;
        }

        public Criteria andOrderSourceLessThanOrEqualTo(Byte value) {
            addCriterion("order_source <=", value, "orderSource");
            return (Criteria) this;
        }

        public Criteria andOrderSourceIn(List<Byte> values) {
            addCriterion("order_source in", values, "orderSource");
            return (Criteria) this;
        }

        public Criteria andOrderSourceNotIn(List<Byte> values) {
            addCriterion("order_source not in", values, "orderSource");
            return (Criteria) this;
        }

        public Criteria andOrderSourceBetween(Byte value1, Byte value2) {
            addCriterion("order_source between", value1, value2, "orderSource");
            return (Criteria) this;
        }

        public Criteria andOrderSourceNotBetween(Byte value1, Byte value2) {
            addCriterion("order_source not between", value1, value2, "orderSource");
            return (Criteria) this;
        }

        public Criteria andOrderTimeIsNull() {
            addCriterion("order_time is null");
            return (Criteria) this;
        }

        public Criteria andOrderTimeIsNotNull() {
            addCriterion("order_time is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTimeEqualTo(Date value) {
            addCriterion("order_time =", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeNotEqualTo(Date value) {
            addCriterion("order_time <>", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeGreaterThan(Date value) {
            addCriterion("order_time >", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("order_time >=", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeLessThan(Date value) {
            addCriterion("order_time <", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeLessThanOrEqualTo(Date value) {
            addCriterion("order_time <=", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeIn(List<Date> values) {
            addCriterion("order_time in", values, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeNotIn(List<Date> values) {
            addCriterion("order_time not in", values, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeBetween(Date value1, Date value2) {
            addCriterion("order_time between", value1, value2, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeNotBetween(Date value1, Date value2) {
            addCriterion("order_time not between", value1, value2, "orderTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeIsNull() {
            addCriterion("cancel_time is null");
            return (Criteria) this;
        }

        public Criteria andCancelTimeIsNotNull() {
            addCriterion("cancel_time is not null");
            return (Criteria) this;
        }

        public Criteria andCancelTimeEqualTo(Date value) {
            addCriterion("cancel_time =", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeNotEqualTo(Date value) {
            addCriterion("cancel_time <>", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeGreaterThan(Date value) {
            addCriterion("cancel_time >", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("cancel_time >=", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeLessThan(Date value) {
            addCriterion("cancel_time <", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeLessThanOrEqualTo(Date value) {
            addCriterion("cancel_time <=", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeIn(List<Date> values) {
            addCriterion("cancel_time in", values, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeNotIn(List<Date> values) {
            addCriterion("cancel_time not in", values, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeBetween(Date value1, Date value2) {
            addCriterion("cancel_time between", value1, value2, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeNotBetween(Date value1, Date value2) {
            addCriterion("cancel_time not between", value1, value2, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNull() {
            addCriterion("pay_time is null");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNotNull() {
            addCriterion("pay_time is not null");
            return (Criteria) this;
        }

        public Criteria andPayTimeEqualTo(Date value) {
            addCriterion("pay_time =", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotEqualTo(Date value) {
            addCriterion("pay_time <>", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThan(Date value) {
            addCriterion("pay_time >", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("pay_time >=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThan(Date value) {
            addCriterion("pay_time <", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThanOrEqualTo(Date value) {
            addCriterion("pay_time <=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeIn(List<Date> values) {
            addCriterion("pay_time in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotIn(List<Date> values) {
            addCriterion("pay_time not in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeBetween(Date value1, Date value2) {
            addCriterion("pay_time between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotBetween(Date value1, Date value2) {
            addCriterion("pay_time not between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceIsNull() {
            addCriterion("original_price is null");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceIsNotNull() {
            addCriterion("original_price is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceEqualTo(BigDecimal value) {
            addCriterion("original_price =", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceNotEqualTo(BigDecimal value) {
            addCriterion("original_price <>", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceGreaterThan(BigDecimal value) {
            addCriterion("original_price >", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("original_price >=", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceLessThan(BigDecimal value) {
            addCriterion("original_price <", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("original_price <=", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceIn(List<BigDecimal> values) {
            addCriterion("original_price in", values, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceNotIn(List<BigDecimal> values) {
            addCriterion("original_price not in", values, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("original_price between", value1, value2, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("original_price not between", value1, value2, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andNetValueIsNull() {
            addCriterion("net_value is null");
            return (Criteria) this;
        }

        public Criteria andNetValueIsNotNull() {
            addCriterion("net_value is not null");
            return (Criteria) this;
        }

        public Criteria andNetValueEqualTo(BigDecimal value) {
            addCriterion("net_value =", value, "netValue");
            return (Criteria) this;
        }

        public Criteria andNetValueNotEqualTo(BigDecimal value) {
            addCriterion("net_value <>", value, "netValue");
            return (Criteria) this;
        }

        public Criteria andNetValueGreaterThan(BigDecimal value) {
            addCriterion("net_value >", value, "netValue");
            return (Criteria) this;
        }

        public Criteria andNetValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("net_value >=", value, "netValue");
            return (Criteria) this;
        }

        public Criteria andNetValueLessThan(BigDecimal value) {
            addCriterion("net_value <", value, "netValue");
            return (Criteria) this;
        }

        public Criteria andNetValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("net_value <=", value, "netValue");
            return (Criteria) this;
        }

        public Criteria andNetValueIn(List<BigDecimal> values) {
            addCriterion("net_value in", values, "netValue");
            return (Criteria) this;
        }

        public Criteria andNetValueNotIn(List<BigDecimal> values) {
            addCriterion("net_value not in", values, "netValue");
            return (Criteria) this;
        }

        public Criteria andNetValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("net_value between", value1, value2, "netValue");
            return (Criteria) this;
        }

        public Criteria andNetValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("net_value not between", value1, value2, "netValue");
            return (Criteria) this;
        }

        public Criteria andStrikePriceIsNull() {
            addCriterion("strike_price is null");
            return (Criteria) this;
        }

        public Criteria andStrikePriceIsNotNull() {
            addCriterion("strike_price is not null");
            return (Criteria) this;
        }

        public Criteria andStrikePriceEqualTo(BigDecimal value) {
            addCriterion("strike_price =", value, "strikePrice");
            return (Criteria) this;
        }

        public Criteria andStrikePriceNotEqualTo(BigDecimal value) {
            addCriterion("strike_price <>", value, "strikePrice");
            return (Criteria) this;
        }

        public Criteria andStrikePriceGreaterThan(BigDecimal value) {
            addCriterion("strike_price >", value, "strikePrice");
            return (Criteria) this;
        }

        public Criteria andStrikePriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("strike_price >=", value, "strikePrice");
            return (Criteria) this;
        }

        public Criteria andStrikePriceLessThan(BigDecimal value) {
            addCriterion("strike_price <", value, "strikePrice");
            return (Criteria) this;
        }

        public Criteria andStrikePriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("strike_price <=", value, "strikePrice");
            return (Criteria) this;
        }

        public Criteria andStrikePriceIn(List<BigDecimal> values) {
            addCriterion("strike_price in", values, "strikePrice");
            return (Criteria) this;
        }

        public Criteria andStrikePriceNotIn(List<BigDecimal> values) {
            addCriterion("strike_price not in", values, "strikePrice");
            return (Criteria) this;
        }

        public Criteria andStrikePriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("strike_price between", value1, value2, "strikePrice");
            return (Criteria) this;
        }

        public Criteria andStrikePriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("strike_price not between", value1, value2, "strikePrice");
            return (Criteria) this;
        }

        public Criteria andIsBasicOrderIsNull() {
            addCriterion("is_basic_order is null");
            return (Criteria) this;
        }

        public Criteria andIsBasicOrderIsNotNull() {
            addCriterion("is_basic_order is not null");
            return (Criteria) this;
        }

        public Criteria andIsBasicOrderEqualTo(Byte value) {
            addCriterion("is_basic_order =", value, "isBasicOrder");
            return (Criteria) this;
        }

        public Criteria andIsBasicOrderNotEqualTo(Byte value) {
            addCriterion("is_basic_order <>", value, "isBasicOrder");
            return (Criteria) this;
        }

        public Criteria andIsBasicOrderGreaterThan(Byte value) {
            addCriterion("is_basic_order >", value, "isBasicOrder");
            return (Criteria) this;
        }

        public Criteria andIsBasicOrderGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_basic_order >=", value, "isBasicOrder");
            return (Criteria) this;
        }

        public Criteria andIsBasicOrderLessThan(Byte value) {
            addCriterion("is_basic_order <", value, "isBasicOrder");
            return (Criteria) this;
        }

        public Criteria andIsBasicOrderLessThanOrEqualTo(Byte value) {
            addCriterion("is_basic_order <=", value, "isBasicOrder");
            return (Criteria) this;
        }

        public Criteria andIsBasicOrderIn(List<Byte> values) {
            addCriterion("is_basic_order in", values, "isBasicOrder");
            return (Criteria) this;
        }

        public Criteria andIsBasicOrderNotIn(List<Byte> values) {
            addCriterion("is_basic_order not in", values, "isBasicOrder");
            return (Criteria) this;
        }

        public Criteria andIsBasicOrderBetween(Byte value1, Byte value2) {
            addCriterion("is_basic_order between", value1, value2, "isBasicOrder");
            return (Criteria) this;
        }

        public Criteria andIsBasicOrderNotBetween(Byte value1, Byte value2) {
            addCriterion("is_basic_order not between", value1, value2, "isBasicOrder");
            return (Criteria) this;
        }

        public Criteria andBasicOrderIdIsNull() {
            addCriterion("basic_order_id is null");
            return (Criteria) this;
        }

        public Criteria andBasicOrderIdIsNotNull() {
            addCriterion("basic_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andBasicOrderIdEqualTo(Integer value) {
            addCriterion("basic_order_id =", value, "basicOrderId");
            return (Criteria) this;
        }

        public Criteria andBasicOrderIdNotEqualTo(Integer value) {
            addCriterion("basic_order_id <>", value, "basicOrderId");
            return (Criteria) this;
        }

        public Criteria andBasicOrderIdGreaterThan(Integer value) {
            addCriterion("basic_order_id >", value, "basicOrderId");
            return (Criteria) this;
        }

        public Criteria andBasicOrderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("basic_order_id >=", value, "basicOrderId");
            return (Criteria) this;
        }

        public Criteria andBasicOrderIdLessThan(Integer value) {
            addCriterion("basic_order_id <", value, "basicOrderId");
            return (Criteria) this;
        }

        public Criteria andBasicOrderIdLessThanOrEqualTo(Integer value) {
            addCriterion("basic_order_id <=", value, "basicOrderId");
            return (Criteria) this;
        }

        public Criteria andBasicOrderIdIn(List<Integer> values) {
            addCriterion("basic_order_id in", values, "basicOrderId");
            return (Criteria) this;
        }

        public Criteria andBasicOrderIdNotIn(List<Integer> values) {
            addCriterion("basic_order_id not in", values, "basicOrderId");
            return (Criteria) this;
        }

        public Criteria andBasicOrderIdBetween(Integer value1, Integer value2) {
            addCriterion("basic_order_id between", value1, value2, "basicOrderId");
            return (Criteria) this;
        }

        public Criteria andBasicOrderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("basic_order_id not between", value1, value2, "basicOrderId");
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