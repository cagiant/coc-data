package com.coc.data.model.base;

import lombok.Builder;

import java.util.Date;

@Builder
public class Clan {
    private Integer id;

    private String tag;

    private String name;

    private Byte provideClanWarReport;

    private Byte provideLeagueWarReport;

    private Date expireTime;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getProvideClanWarReport() {
        return provideClanWarReport;
    }

    public void setProvideClanWarReport(Byte provideClanWarReport) {
        this.provideClanWarReport = provideClanWarReport;
    }

    public Byte getProvideLeagueWarReport() {
        return provideLeagueWarReport;
    }

    public void setProvideLeagueWarReport(Byte provideLeagueWarReport) {
        this.provideLeagueWarReport = provideLeagueWarReport;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
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