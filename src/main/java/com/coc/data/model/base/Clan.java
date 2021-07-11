package com.coc.data.model.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Clan {
    private Long id;

    private String tag;

    private String name;

    private Boolean provideClanWarReport;

    private Boolean provideLeagueWarReport;

    private Date expireTime;

    private Date createTime;

    private Date updateTime;

    private String extraInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Boolean getProvideClanWarReport() {
        return provideClanWarReport;
    }

    public void setProvideClanWarReport(Boolean provideClanWarReport) {
        this.provideClanWarReport = provideClanWarReport;
    }

    public Boolean getProvideLeagueWarReport() {
        return provideLeagueWarReport;
    }

    public void setProvideLeagueWarReport(Boolean provideLeagueWarReport) {
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

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo == null ? null : extraInfo.trim();
    }
}