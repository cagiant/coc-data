package com.coc.data.model.base;

import lombok.Builder;

import java.util.Date;

@Builder
public class ClanWarLog {
    private Long id;

    private String warTag;

    private String attackerTag;

    private String defenderTag;

    private Short star;

    private String destructionPercentage;

    private Integer attackOrder;

    private Date createTime;

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

    public String getAttackerTag() {
        return attackerTag;
    }

    public void setAttackerTag(String attackerTag) {
        this.attackerTag = attackerTag == null ? null : attackerTag.trim();
    }

    public String getDefenderTag() {
        return defenderTag;
    }

    public void setDefenderTag(String defenderTag) {
        this.defenderTag = defenderTag == null ? null : defenderTag.trim();
    }

    public Short getStar() {
        return star;
    }

    public void setStar(Short star) {
        this.star = star;
    }

    public String getDestructionPercentage() {
        return destructionPercentage;
    }

    public void setDestructionPercentage(String destructionPercentage) {
        this.destructionPercentage = destructionPercentage == null ? null : destructionPercentage.trim();
    }

    public Integer getAttackOrder() {
        return attackOrder;
    }

    public void setAttackOrder(Integer attackOrder) {
        this.attackOrder = attackOrder;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}