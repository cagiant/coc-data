package com.coc.data.model.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClanWarLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ClanWarLogExample() {
        oredCriteria = new ArrayList<>();
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
            criteria = new ArrayList<>();
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andWarTagIsNull() {
            addCriterion("war_tag is null");
            return (Criteria) this;
        }

        public Criteria andWarTagIsNotNull() {
            addCriterion("war_tag is not null");
            return (Criteria) this;
        }

        public Criteria andWarTagEqualTo(String value) {
            addCriterion("war_tag =", value, "warTag");
            return (Criteria) this;
        }

        public Criteria andWarTagNotEqualTo(String value) {
            addCriterion("war_tag <>", value, "warTag");
            return (Criteria) this;
        }

        public Criteria andWarTagGreaterThan(String value) {
            addCriterion("war_tag >", value, "warTag");
            return (Criteria) this;
        }

        public Criteria andWarTagGreaterThanOrEqualTo(String value) {
            addCriterion("war_tag >=", value, "warTag");
            return (Criteria) this;
        }

        public Criteria andWarTagLessThan(String value) {
            addCriterion("war_tag <", value, "warTag");
            return (Criteria) this;
        }

        public Criteria andWarTagLessThanOrEqualTo(String value) {
            addCriterion("war_tag <=", value, "warTag");
            return (Criteria) this;
        }

        public Criteria andWarTagLike(String value) {
            addCriterion("war_tag like", value, "warTag");
            return (Criteria) this;
        }

        public Criteria andWarTagNotLike(String value) {
            addCriterion("war_tag not like", value, "warTag");
            return (Criteria) this;
        }

        public Criteria andWarTagIn(List<String> values) {
            addCriterion("war_tag in", values, "warTag");
            return (Criteria) this;
        }

        public Criteria andWarTagNotIn(List<String> values) {
            addCriterion("war_tag not in", values, "warTag");
            return (Criteria) this;
        }

        public Criteria andWarTagBetween(String value1, String value2) {
            addCriterion("war_tag between", value1, value2, "warTag");
            return (Criteria) this;
        }

        public Criteria andWarTagNotBetween(String value1, String value2) {
            addCriterion("war_tag not between", value1, value2, "warTag");
            return (Criteria) this;
        }

        public Criteria andAttackerTagIsNull() {
            addCriterion("attacker_tag is null");
            return (Criteria) this;
        }

        public Criteria andAttackerTagIsNotNull() {
            addCriterion("attacker_tag is not null");
            return (Criteria) this;
        }

        public Criteria andAttackerTagEqualTo(String value) {
            addCriterion("attacker_tag =", value, "attackerTag");
            return (Criteria) this;
        }

        public Criteria andAttackerTagNotEqualTo(String value) {
            addCriterion("attacker_tag <>", value, "attackerTag");
            return (Criteria) this;
        }

        public Criteria andAttackerTagGreaterThan(String value) {
            addCriterion("attacker_tag >", value, "attackerTag");
            return (Criteria) this;
        }

        public Criteria andAttackerTagGreaterThanOrEqualTo(String value) {
            addCriterion("attacker_tag >=", value, "attackerTag");
            return (Criteria) this;
        }

        public Criteria andAttackerTagLessThan(String value) {
            addCriterion("attacker_tag <", value, "attackerTag");
            return (Criteria) this;
        }

        public Criteria andAttackerTagLessThanOrEqualTo(String value) {
            addCriterion("attacker_tag <=", value, "attackerTag");
            return (Criteria) this;
        }

        public Criteria andAttackerTagLike(String value) {
            addCriterion("attacker_tag like", value, "attackerTag");
            return (Criteria) this;
        }

        public Criteria andAttackerTagNotLike(String value) {
            addCriterion("attacker_tag not like", value, "attackerTag");
            return (Criteria) this;
        }

        public Criteria andAttackerTagIn(List<String> values) {
            addCriterion("attacker_tag in", values, "attackerTag");
            return (Criteria) this;
        }

        public Criteria andAttackerTagNotIn(List<String> values) {
            addCriterion("attacker_tag not in", values, "attackerTag");
            return (Criteria) this;
        }

        public Criteria andAttackerTagBetween(String value1, String value2) {
            addCriterion("attacker_tag between", value1, value2, "attackerTag");
            return (Criteria) this;
        }

        public Criteria andAttackerTagNotBetween(String value1, String value2) {
            addCriterion("attacker_tag not between", value1, value2, "attackerTag");
            return (Criteria) this;
        }

        public Criteria andDefenderTagIsNull() {
            addCriterion("defender_tag is null");
            return (Criteria) this;
        }

        public Criteria andDefenderTagIsNotNull() {
            addCriterion("defender_tag is not null");
            return (Criteria) this;
        }

        public Criteria andDefenderTagEqualTo(String value) {
            addCriterion("defender_tag =", value, "defenderTag");
            return (Criteria) this;
        }

        public Criteria andDefenderTagNotEqualTo(String value) {
            addCriterion("defender_tag <>", value, "defenderTag");
            return (Criteria) this;
        }

        public Criteria andDefenderTagGreaterThan(String value) {
            addCriterion("defender_tag >", value, "defenderTag");
            return (Criteria) this;
        }

        public Criteria andDefenderTagGreaterThanOrEqualTo(String value) {
            addCriterion("defender_tag >=", value, "defenderTag");
            return (Criteria) this;
        }

        public Criteria andDefenderTagLessThan(String value) {
            addCriterion("defender_tag <", value, "defenderTag");
            return (Criteria) this;
        }

        public Criteria andDefenderTagLessThanOrEqualTo(String value) {
            addCriterion("defender_tag <=", value, "defenderTag");
            return (Criteria) this;
        }

        public Criteria andDefenderTagLike(String value) {
            addCriterion("defender_tag like", value, "defenderTag");
            return (Criteria) this;
        }

        public Criteria andDefenderTagNotLike(String value) {
            addCriterion("defender_tag not like", value, "defenderTag");
            return (Criteria) this;
        }

        public Criteria andDefenderTagIn(List<String> values) {
            addCriterion("defender_tag in", values, "defenderTag");
            return (Criteria) this;
        }

        public Criteria andDefenderTagNotIn(List<String> values) {
            addCriterion("defender_tag not in", values, "defenderTag");
            return (Criteria) this;
        }

        public Criteria andDefenderTagBetween(String value1, String value2) {
            addCriterion("defender_tag between", value1, value2, "defenderTag");
            return (Criteria) this;
        }

        public Criteria andDefenderTagNotBetween(String value1, String value2) {
            addCriterion("defender_tag not between", value1, value2, "defenderTag");
            return (Criteria) this;
        }

        public Criteria andStarIsNull() {
            addCriterion("star is null");
            return (Criteria) this;
        }

        public Criteria andStarIsNotNull() {
            addCriterion("star is not null");
            return (Criteria) this;
        }

        public Criteria andStarEqualTo(Short value) {
            addCriterion("star =", value, "star");
            return (Criteria) this;
        }

        public Criteria andStarNotEqualTo(Short value) {
            addCriterion("star <>", value, "star");
            return (Criteria) this;
        }

        public Criteria andStarGreaterThan(Short value) {
            addCriterion("star >", value, "star");
            return (Criteria) this;
        }

        public Criteria andStarGreaterThanOrEqualTo(Short value) {
            addCriterion("star >=", value, "star");
            return (Criteria) this;
        }

        public Criteria andStarLessThan(Short value) {
            addCriterion("star <", value, "star");
            return (Criteria) this;
        }

        public Criteria andStarLessThanOrEqualTo(Short value) {
            addCriterion("star <=", value, "star");
            return (Criteria) this;
        }

        public Criteria andStarIn(List<Short> values) {
            addCriterion("star in", values, "star");
            return (Criteria) this;
        }

        public Criteria andStarNotIn(List<Short> values) {
            addCriterion("star not in", values, "star");
            return (Criteria) this;
        }

        public Criteria andStarBetween(Short value1, Short value2) {
            addCriterion("star between", value1, value2, "star");
            return (Criteria) this;
        }

        public Criteria andStarNotBetween(Short value1, Short value2) {
            addCriterion("star not between", value1, value2, "star");
            return (Criteria) this;
        }

        public Criteria andDestructionPercentageIsNull() {
            addCriterion("destruction_percentage is null");
            return (Criteria) this;
        }

        public Criteria andDestructionPercentageIsNotNull() {
            addCriterion("destruction_percentage is not null");
            return (Criteria) this;
        }

        public Criteria andDestructionPercentageEqualTo(String value) {
            addCriterion("destruction_percentage =", value, "destructionPercentage");
            return (Criteria) this;
        }

        public Criteria andDestructionPercentageNotEqualTo(String value) {
            addCriterion("destruction_percentage <>", value, "destructionPercentage");
            return (Criteria) this;
        }

        public Criteria andDestructionPercentageGreaterThan(String value) {
            addCriterion("destruction_percentage >", value, "destructionPercentage");
            return (Criteria) this;
        }

        public Criteria andDestructionPercentageGreaterThanOrEqualTo(String value) {
            addCriterion("destruction_percentage >=", value, "destructionPercentage");
            return (Criteria) this;
        }

        public Criteria andDestructionPercentageLessThan(String value) {
            addCriterion("destruction_percentage <", value, "destructionPercentage");
            return (Criteria) this;
        }

        public Criteria andDestructionPercentageLessThanOrEqualTo(String value) {
            addCriterion("destruction_percentage <=", value, "destructionPercentage");
            return (Criteria) this;
        }

        public Criteria andDestructionPercentageLike(String value) {
            addCriterion("destruction_percentage like", value, "destructionPercentage");
            return (Criteria) this;
        }

        public Criteria andDestructionPercentageNotLike(String value) {
            addCriterion("destruction_percentage not like", value, "destructionPercentage");
            return (Criteria) this;
        }

        public Criteria andDestructionPercentageIn(List<String> values) {
            addCriterion("destruction_percentage in", values, "destructionPercentage");
            return (Criteria) this;
        }

        public Criteria andDestructionPercentageNotIn(List<String> values) {
            addCriterion("destruction_percentage not in", values, "destructionPercentage");
            return (Criteria) this;
        }

        public Criteria andDestructionPercentageBetween(String value1, String value2) {
            addCriterion("destruction_percentage between", value1, value2, "destructionPercentage");
            return (Criteria) this;
        }

        public Criteria andDestructionPercentageNotBetween(String value1, String value2) {
            addCriterion("destruction_percentage not between", value1, value2, "destructionPercentage");
            return (Criteria) this;
        }

        public Criteria andAttackOrderIsNull() {
            addCriterion("attack_order is null");
            return (Criteria) this;
        }

        public Criteria andAttackOrderIsNotNull() {
            addCriterion("attack_order is not null");
            return (Criteria) this;
        }

        public Criteria andAttackOrderEqualTo(Integer value) {
            addCriterion("attack_order =", value, "attackOrder");
            return (Criteria) this;
        }

        public Criteria andAttackOrderNotEqualTo(Integer value) {
            addCriterion("attack_order <>", value, "attackOrder");
            return (Criteria) this;
        }

        public Criteria andAttackOrderGreaterThan(Integer value) {
            addCriterion("attack_order >", value, "attackOrder");
            return (Criteria) this;
        }

        public Criteria andAttackOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("attack_order >=", value, "attackOrder");
            return (Criteria) this;
        }

        public Criteria andAttackOrderLessThan(Integer value) {
            addCriterion("attack_order <", value, "attackOrder");
            return (Criteria) this;
        }

        public Criteria andAttackOrderLessThanOrEqualTo(Integer value) {
            addCriterion("attack_order <=", value, "attackOrder");
            return (Criteria) this;
        }

        public Criteria andAttackOrderIn(List<Integer> values) {
            addCriterion("attack_order in", values, "attackOrder");
            return (Criteria) this;
        }

        public Criteria andAttackOrderNotIn(List<Integer> values) {
            addCriterion("attack_order not in", values, "attackOrder");
            return (Criteria) this;
        }

        public Criteria andAttackOrderBetween(Integer value1, Integer value2) {
            addCriterion("attack_order between", value1, value2, "attackOrder");
            return (Criteria) this;
        }

        public Criteria andAttackOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("attack_order not between", value1, value2, "attackOrder");
            return (Criteria) this;
        }

        public Criteria andDurationIsNull() {
            addCriterion("duration is null");
            return (Criteria) this;
        }

        public Criteria andDurationIsNotNull() {
            addCriterion("duration is not null");
            return (Criteria) this;
        }

        public Criteria andDurationEqualTo(Integer value) {
            addCriterion("duration =", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotEqualTo(Integer value) {
            addCriterion("duration <>", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationGreaterThan(Integer value) {
            addCriterion("duration >", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationGreaterThanOrEqualTo(Integer value) {
            addCriterion("duration >=", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationLessThan(Integer value) {
            addCriterion("duration <", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationLessThanOrEqualTo(Integer value) {
            addCriterion("duration <=", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationIn(List<Integer> values) {
            addCriterion("duration in", values, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotIn(List<Integer> values) {
            addCriterion("duration not in", values, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationBetween(Integer value1, Integer value2) {
            addCriterion("duration between", value1, value2, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotBetween(Integer value1, Integer value2) {
            addCriterion("duration not between", value1, value2, "duration");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
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