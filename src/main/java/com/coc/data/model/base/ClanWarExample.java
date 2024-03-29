package com.coc.data.model.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClanWarExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ClanWarExample() {
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

        public Criteria andOpponentClanTagIsNull() {
            addCriterion("opponent_clan_tag is null");
            return (Criteria) this;
        }

        public Criteria andOpponentClanTagIsNotNull() {
            addCriterion("opponent_clan_tag is not null");
            return (Criteria) this;
        }

        public Criteria andOpponentClanTagEqualTo(String value) {
            addCriterion("opponent_clan_tag =", value, "opponentClanTag");
            return (Criteria) this;
        }

        public Criteria andOpponentClanTagNotEqualTo(String value) {
            addCriterion("opponent_clan_tag <>", value, "opponentClanTag");
            return (Criteria) this;
        }

        public Criteria andOpponentClanTagGreaterThan(String value) {
            addCriterion("opponent_clan_tag >", value, "opponentClanTag");
            return (Criteria) this;
        }

        public Criteria andOpponentClanTagGreaterThanOrEqualTo(String value) {
            addCriterion("opponent_clan_tag >=", value, "opponentClanTag");
            return (Criteria) this;
        }

        public Criteria andOpponentClanTagLessThan(String value) {
            addCriterion("opponent_clan_tag <", value, "opponentClanTag");
            return (Criteria) this;
        }

        public Criteria andOpponentClanTagLessThanOrEqualTo(String value) {
            addCriterion("opponent_clan_tag <=", value, "opponentClanTag");
            return (Criteria) this;
        }

        public Criteria andOpponentClanTagLike(String value) {
            addCriterion("opponent_clan_tag like", value, "opponentClanTag");
            return (Criteria) this;
        }

        public Criteria andOpponentClanTagNotLike(String value) {
            addCriterion("opponent_clan_tag not like", value, "opponentClanTag");
            return (Criteria) this;
        }

        public Criteria andOpponentClanTagIn(List<String> values) {
            addCriterion("opponent_clan_tag in", values, "opponentClanTag");
            return (Criteria) this;
        }

        public Criteria andOpponentClanTagNotIn(List<String> values) {
            addCriterion("opponent_clan_tag not in", values, "opponentClanTag");
            return (Criteria) this;
        }

        public Criteria andOpponentClanTagBetween(String value1, String value2) {
            addCriterion("opponent_clan_tag between", value1, value2, "opponentClanTag");
            return (Criteria) this;
        }

        public Criteria andOpponentClanTagNotBetween(String value1, String value2) {
            addCriterion("opponent_clan_tag not between", value1, value2, "opponentClanTag");
            return (Criteria) this;
        }

        public Criteria andTagIsNull() {
            addCriterion("tag is null");
            return (Criteria) this;
        }

        public Criteria andTagIsNotNull() {
            addCriterion("tag is not null");
            return (Criteria) this;
        }

        public Criteria andTagEqualTo(String value) {
            addCriterion("tag =", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotEqualTo(String value) {
            addCriterion("tag <>", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagGreaterThan(String value) {
            addCriterion("tag >", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagGreaterThanOrEqualTo(String value) {
            addCriterion("tag >=", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLessThan(String value) {
            addCriterion("tag <", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLessThanOrEqualTo(String value) {
            addCriterion("tag <=", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLike(String value) {
            addCriterion("tag like", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotLike(String value) {
            addCriterion("tag not like", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagIn(List<String> values) {
            addCriterion("tag in", values, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotIn(List<String> values) {
            addCriterion("tag not in", values, "tag");
            return (Criteria) this;
        }

        public Criteria andTagBetween(String value1, String value2) {
            addCriterion("tag between", value1, value2, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotBetween(String value1, String value2) {
            addCriterion("tag not between", value1, value2, "tag");
            return (Criteria) this;
        }

        public Criteria andSeasonIsNull() {
            addCriterion("season is null");
            return (Criteria) this;
        }

        public Criteria andSeasonIsNotNull() {
            addCriterion("season is not null");
            return (Criteria) this;
        }

        public Criteria andSeasonEqualTo(String value) {
            addCriterion("season =", value, "season");
            return (Criteria) this;
        }

        public Criteria andSeasonNotEqualTo(String value) {
            addCriterion("season <>", value, "season");
            return (Criteria) this;
        }

        public Criteria andSeasonGreaterThan(String value) {
            addCriterion("season >", value, "season");
            return (Criteria) this;
        }

        public Criteria andSeasonGreaterThanOrEqualTo(String value) {
            addCriterion("season >=", value, "season");
            return (Criteria) this;
        }

        public Criteria andSeasonLessThan(String value) {
            addCriterion("season <", value, "season");
            return (Criteria) this;
        }

        public Criteria andSeasonLessThanOrEqualTo(String value) {
            addCriterion("season <=", value, "season");
            return (Criteria) this;
        }

        public Criteria andSeasonLike(String value) {
            addCriterion("season like", value, "season");
            return (Criteria) this;
        }

        public Criteria andSeasonNotLike(String value) {
            addCriterion("season not like", value, "season");
            return (Criteria) this;
        }

        public Criteria andSeasonIn(List<String> values) {
            addCriterion("season in", values, "season");
            return (Criteria) this;
        }

        public Criteria andSeasonNotIn(List<String> values) {
            addCriterion("season not in", values, "season");
            return (Criteria) this;
        }

        public Criteria andSeasonBetween(String value1, String value2) {
            addCriterion("season between", value1, value2, "season");
            return (Criteria) this;
        }

        public Criteria andSeasonNotBetween(String value1, String value2) {
            addCriterion("season not between", value1, value2, "season");
            return (Criteria) this;
        }

        public Criteria andLeagueTagIsNull() {
            addCriterion("league_tag is null");
            return (Criteria) this;
        }

        public Criteria andLeagueTagIsNotNull() {
            addCriterion("league_tag is not null");
            return (Criteria) this;
        }

        public Criteria andLeagueTagEqualTo(String value) {
            addCriterion("league_tag =", value, "leagueTag");
            return (Criteria) this;
        }

        public Criteria andLeagueTagNotEqualTo(String value) {
            addCriterion("league_tag <>", value, "leagueTag");
            return (Criteria) this;
        }

        public Criteria andLeagueTagGreaterThan(String value) {
            addCriterion("league_tag >", value, "leagueTag");
            return (Criteria) this;
        }

        public Criteria andLeagueTagGreaterThanOrEqualTo(String value) {
            addCriterion("league_tag >=", value, "leagueTag");
            return (Criteria) this;
        }

        public Criteria andLeagueTagLessThan(String value) {
            addCriterion("league_tag <", value, "leagueTag");
            return (Criteria) this;
        }

        public Criteria andLeagueTagLessThanOrEqualTo(String value) {
            addCriterion("league_tag <=", value, "leagueTag");
            return (Criteria) this;
        }

        public Criteria andLeagueTagLike(String value) {
            addCriterion("league_tag like", value, "leagueTag");
            return (Criteria) this;
        }

        public Criteria andLeagueTagNotLike(String value) {
            addCriterion("league_tag not like", value, "leagueTag");
            return (Criteria) this;
        }

        public Criteria andLeagueTagIn(List<String> values) {
            addCriterion("league_tag in", values, "leagueTag");
            return (Criteria) this;
        }

        public Criteria andLeagueTagNotIn(List<String> values) {
            addCriterion("league_tag not in", values, "leagueTag");
            return (Criteria) this;
        }

        public Criteria andLeagueTagBetween(String value1, String value2) {
            addCriterion("league_tag between", value1, value2, "leagueTag");
            return (Criteria) this;
        }

        public Criteria andLeagueTagNotBetween(String value1, String value2) {
            addCriterion("league_tag not between", value1, value2, "leagueTag");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(String value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(String value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(String value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(String value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(String value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(String value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLike(String value) {
            addCriterion("state like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotLike(String value) {
            addCriterion("state not like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<String> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<String> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(String value1, String value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(String value1, String value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andTeamSizeIsNull() {
            addCriterion("team_size is null");
            return (Criteria) this;
        }

        public Criteria andTeamSizeIsNotNull() {
            addCriterion("team_size is not null");
            return (Criteria) this;
        }

        public Criteria andTeamSizeEqualTo(Short value) {
            addCriterion("team_size =", value, "teamSize");
            return (Criteria) this;
        }

        public Criteria andTeamSizeNotEqualTo(Short value) {
            addCriterion("team_size <>", value, "teamSize");
            return (Criteria) this;
        }

        public Criteria andTeamSizeGreaterThan(Short value) {
            addCriterion("team_size >", value, "teamSize");
            return (Criteria) this;
        }

        public Criteria andTeamSizeGreaterThanOrEqualTo(Short value) {
            addCriterion("team_size >=", value, "teamSize");
            return (Criteria) this;
        }

        public Criteria andTeamSizeLessThan(Short value) {
            addCriterion("team_size <", value, "teamSize");
            return (Criteria) this;
        }

        public Criteria andTeamSizeLessThanOrEqualTo(Short value) {
            addCriterion("team_size <=", value, "teamSize");
            return (Criteria) this;
        }

        public Criteria andTeamSizeIn(List<Short> values) {
            addCriterion("team_size in", values, "teamSize");
            return (Criteria) this;
        }

        public Criteria andTeamSizeNotIn(List<Short> values) {
            addCriterion("team_size not in", values, "teamSize");
            return (Criteria) this;
        }

        public Criteria andTeamSizeBetween(Short value1, Short value2) {
            addCriterion("team_size between", value1, value2, "teamSize");
            return (Criteria) this;
        }

        public Criteria andTeamSizeNotBetween(Short value1, Short value2) {
            addCriterion("team_size not between", value1, value2, "teamSize");
            return (Criteria) this;
        }

        public Criteria andPreparationStartTimeIsNull() {
            addCriterion("preparation_start_time is null");
            return (Criteria) this;
        }

        public Criteria andPreparationStartTimeIsNotNull() {
            addCriterion("preparation_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andPreparationStartTimeEqualTo(Date value) {
            addCriterion("preparation_start_time =", value, "preparationStartTime");
            return (Criteria) this;
        }

        public Criteria andPreparationStartTimeNotEqualTo(Date value) {
            addCriterion("preparation_start_time <>", value, "preparationStartTime");
            return (Criteria) this;
        }

        public Criteria andPreparationStartTimeGreaterThan(Date value) {
            addCriterion("preparation_start_time >", value, "preparationStartTime");
            return (Criteria) this;
        }

        public Criteria andPreparationStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("preparation_start_time >=", value, "preparationStartTime");
            return (Criteria) this;
        }

        public Criteria andPreparationStartTimeLessThan(Date value) {
            addCriterion("preparation_start_time <", value, "preparationStartTime");
            return (Criteria) this;
        }

        public Criteria andPreparationStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("preparation_start_time <=", value, "preparationStartTime");
            return (Criteria) this;
        }

        public Criteria andPreparationStartTimeIn(List<Date> values) {
            addCriterion("preparation_start_time in", values, "preparationStartTime");
            return (Criteria) this;
        }

        public Criteria andPreparationStartTimeNotIn(List<Date> values) {
            addCriterion("preparation_start_time not in", values, "preparationStartTime");
            return (Criteria) this;
        }

        public Criteria andPreparationStartTimeBetween(Date value1, Date value2) {
            addCriterion("preparation_start_time between", value1, value2, "preparationStartTime");
            return (Criteria) this;
        }

        public Criteria andPreparationStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("preparation_start_time not between", value1, value2, "preparationStartTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
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