package com.coc.data.model.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClanWarMember {
    private Long id;

    private String warTag;

    private String memberTag;

    private String clanTag;

    private String memberName;

    private Byte mapPosition;

    private Boolean isDeleted;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWarTag() {
        return warTag;
    }

    public void setWarTag(String warTag) {
        this.warTag = warTag == null ? null : warTag.trim();
    }

    public String getMemberTag() {
        return memberTag;
    }

    public void setMemberTag(String memberTag) {
        this.memberTag = memberTag == null ? null : memberTag.trim();
    }

    public String getClanTag() {
        return clanTag;
    }

    public void setClanTag(String clanTag) {
        this.clanTag = clanTag == null ? null : clanTag.trim();
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public Byte getMapPosition() {
        return mapPosition;
    }

    public void setMapPosition(Byte mapPosition) {
        this.mapPosition = mapPosition;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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