package com.coc.data.model.base;

import lombok.Builder;

import java.util.Date;

@Builder
public class ClanWar {
    private Long id;

    private String clanTag;

    private String opponentClanTag;

    private String tag;

    private String season;

    private String state;

    private Short teamSize;

    private Date preparationStartTime;

    private Date startTime;

    private Date endTime;

    private String type;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClanTag() {
        return clanTag;
    }

    public void setClanTag(String clanTag) {
        this.clanTag = clanTag == null ? null : clanTag.trim();
    }

    public String getOpponentClanTag() {
        return opponentClanTag;
    }

    public void setOpponentClanTag(String opponentClanTag) {
        this.opponentClanTag = opponentClanTag == null ? null : opponentClanTag.trim();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season == null ? null : season.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Short getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(Short teamSize) {
        this.teamSize = teamSize;
    }

    public Date getPreparationStartTime() {
        return preparationStartTime;
    }

    public void setPreparationStartTime(Date preparationStartTime) {
        this.preparationStartTime = preparationStartTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}