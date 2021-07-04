package com.coc.data.model.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClanWarMemberExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ClanWarMemberExample() {
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

        public Criteria andMemberTagIsNull() {
            addCriterion("member_tag is null");
            return (Criteria) this;
        }

        public Criteria andMemberTagIsNotNull() {
            addCriterion("member_tag is not null");
            return (Criteria) this;
        }

        public Criteria andMemberTagEqualTo(String value) {
            addCriterion("member_tag =", value, "memberTag");
            return (Criteria) this;
        }

        public Criteria andMemberTagNotEqualTo(String value) {
            addCriterion("member_tag <>", value, "memberTag");
            return (Criteria) this;
        }

        public Criteria andMemberTagGreaterThan(String value) {
            addCriterion("member_tag >", value, "memberTag");
            return (Criteria) this;
        }

        public Criteria andMemberTagGreaterThanOrEqualTo(String value) {
            addCriterion("member_tag >=", value, "memberTag");
            return (Criteria) this;
        }

        public Criteria andMemberTagLessThan(String value) {
            addCriterion("member_tag <", value, "memberTag");
            return (Criteria) this;
        }

        public Criteria andMemberTagLessThanOrEqualTo(String value) {
            addCriterion("member_tag <=", value, "memberTag");
            return (Criteria) this;
        }

        public Criteria andMemberTagLike(String value) {
            addCriterion("member_tag like", value, "memberTag");
            return (Criteria) this;
        }

        public Criteria andMemberTagNotLike(String value) {
            addCriterion("member_tag not like", value, "memberTag");
            return (Criteria) this;
        }

        public Criteria andMemberTagIn(List<String> values) {
            addCriterion("member_tag in", values, "memberTag");
            return (Criteria) this;
        }

        public Criteria andMemberTagNotIn(List<String> values) {
            addCriterion("member_tag not in", values, "memberTag");
            return (Criteria) this;
        }

        public Criteria andMemberTagBetween(String value1, String value2) {
            addCriterion("member_tag between", value1, value2, "memberTag");
            return (Criteria) this;
        }

        public Criteria andMemberTagNotBetween(String value1, String value2) {
            addCriterion("member_tag not between", value1, value2, "memberTag");
            return (Criteria) this;
        }

        public Criteria andClanTagIsNull() {
            addCriterion("clan_tag is null");
            return (Criteria) this;
        }

        public Criteria andClanTagIsNotNull() {
            addCriterion("clan_tag is not null");
            return (Criteria) this;
        }

        public Criteria andClanTagEqualTo(String value) {
            addCriterion("clan_tag =", value, "clanTag");
            return (Criteria) this;
        }

        public Criteria andClanTagNotEqualTo(String value) {
            addCriterion("clan_tag <>", value, "clanTag");
            return (Criteria) this;
        }

        public Criteria andClanTagGreaterThan(String value) {
            addCriterion("clan_tag >", value, "clanTag");
            return (Criteria) this;
        }

        public Criteria andClanTagGreaterThanOrEqualTo(String value) {
            addCriterion("clan_tag >=", value, "clanTag");
            return (Criteria) this;
        }

        public Criteria andClanTagLessThan(String value) {
            addCriterion("clan_tag <", value, "clanTag");
            return (Criteria) this;
        }

        public Criteria andClanTagLessThanOrEqualTo(String value) {
            addCriterion("clan_tag <=", value, "clanTag");
            return (Criteria) this;
        }

        public Criteria andClanTagLike(String value) {
            addCriterion("clan_tag like", value, "clanTag");
            return (Criteria) this;
        }

        public Criteria andClanTagNotLike(String value) {
            addCriterion("clan_tag not like", value, "clanTag");
            return (Criteria) this;
        }

        public Criteria andClanTagIn(List<String> values) {
            addCriterion("clan_tag in", values, "clanTag");
            return (Criteria) this;
        }

        public Criteria andClanTagNotIn(List<String> values) {
            addCriterion("clan_tag not in", values, "clanTag");
            return (Criteria) this;
        }

        public Criteria andClanTagBetween(String value1, String value2) {
            addCriterion("clan_tag between", value1, value2, "clanTag");
            return (Criteria) this;
        }

        public Criteria andClanTagNotBetween(String value1, String value2) {
            addCriterion("clan_tag not between", value1, value2, "clanTag");
            return (Criteria) this;
        }

        public Criteria andMemberNameIsNull() {
            addCriterion("member_name is null");
            return (Criteria) this;
        }

        public Criteria andMemberNameIsNotNull() {
            addCriterion("member_name is not null");
            return (Criteria) this;
        }

        public Criteria andMemberNameEqualTo(String value) {
            addCriterion("member_name =", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameNotEqualTo(String value) {
            addCriterion("member_name <>", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameGreaterThan(String value) {
            addCriterion("member_name >", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameGreaterThanOrEqualTo(String value) {
            addCriterion("member_name >=", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameLessThan(String value) {
            addCriterion("member_name <", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameLessThanOrEqualTo(String value) {
            addCriterion("member_name <=", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameLike(String value) {
            addCriterion("member_name like", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameNotLike(String value) {
            addCriterion("member_name not like", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameIn(List<String> values) {
            addCriterion("member_name in", values, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameNotIn(List<String> values) {
            addCriterion("member_name not in", values, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameBetween(String value1, String value2) {
            addCriterion("member_name between", value1, value2, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameNotBetween(String value1, String value2) {
            addCriterion("member_name not between", value1, value2, "memberName");
            return (Criteria) this;
        }

        public Criteria andMapPositionIsNull() {
            addCriterion("map_position is null");
            return (Criteria) this;
        }

        public Criteria andMapPositionIsNotNull() {
            addCriterion("map_position is not null");
            return (Criteria) this;
        }

        public Criteria andMapPositionEqualTo(Byte value) {
            addCriterion("map_position =", value, "mapPosition");
            return (Criteria) this;
        }

        public Criteria andMapPositionNotEqualTo(Byte value) {
            addCriterion("map_position <>", value, "mapPosition");
            return (Criteria) this;
        }

        public Criteria andMapPositionGreaterThan(Byte value) {
            addCriterion("map_position >", value, "mapPosition");
            return (Criteria) this;
        }

        public Criteria andMapPositionGreaterThanOrEqualTo(Byte value) {
            addCriterion("map_position >=", value, "mapPosition");
            return (Criteria) this;
        }

        public Criteria andMapPositionLessThan(Byte value) {
            addCriterion("map_position <", value, "mapPosition");
            return (Criteria) this;
        }

        public Criteria andMapPositionLessThanOrEqualTo(Byte value) {
            addCriterion("map_position <=", value, "mapPosition");
            return (Criteria) this;
        }

        public Criteria andMapPositionIn(List<Byte> values) {
            addCriterion("map_position in", values, "mapPosition");
            return (Criteria) this;
        }

        public Criteria andMapPositionNotIn(List<Byte> values) {
            addCriterion("map_position not in", values, "mapPosition");
            return (Criteria) this;
        }

        public Criteria andMapPositionBetween(Byte value1, Byte value2) {
            addCriterion("map_position between", value1, value2, "mapPosition");
            return (Criteria) this;
        }

        public Criteria andMapPositionNotBetween(Byte value1, Byte value2) {
            addCriterion("map_position not between", value1, value2, "mapPosition");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Boolean value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Boolean value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Boolean value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Boolean value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Boolean> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Boolean> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
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

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
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