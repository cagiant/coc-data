package com.coc.data.model.base;

import lombok.Builder;

import java.util.Date;
import java.util.Objects;

@Builder
public class ClanWarLog implements Comparable<ClanWarLog>{
    private Long id;

    private String warTag;

    private String attackerTag;

    private String defenderTag;

    private Short star;

    private String destructionPercentage;

    private Integer attackOrder;

    private Integer duration;

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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public int compareTo(ClanWarLog o) {
        if (Objects.equals(this.getStar(), o.getStar())) {
            if (Objects.equals(this.getDestructionPercentage(), o.getDestructionPercentage())) {
                return this.getDuration().compareTo(o.getDuration());
            } else {
                return this.getDestructionPercentage().compareTo(o.getDestructionPercentage());
            }
        }
        return this.getStar().compareTo(o.getStar());
    }
}