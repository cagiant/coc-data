package com.coc.data.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ClanMembers {
    private Integer id;

    private String tag;

    private String clanTag;

    private String name;

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

    public String getClanTag() {
        return clanTag;
    }

    public void setClanTag(String clanTag) {
        this.clanTag = clanTag == null ? null : clanTag.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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