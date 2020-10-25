package com.coc.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClanWarMembers {
    private Long id;

    private String memberTag;

    private String memberName;

    private String warTag;

    private String clanTag;

    /**
     * 是否联赛
     **/
    private Boolean leagueWar;

    /**
     * 是否月度报表
     **/
    private Boolean seasonallyReport;

    /**
     * 三星次数
     **/
    private Long attackThreeStarTime;

    /**
     * 二星次数
     **/
    private Long attackTwoStarTime;

    /**
     * 一星次数
     **/
    private Long attackOneStarTime;

    /**
     * 0星次数
     **/
    private Long attackNoStarTime;

    /**
     * 总进攻百分比
     **/
    private Long totalAttackPercentage;

    /**
     * 已使用进攻次数
     **/
    private Long attackTimeUsed;

    /**
     * 剩余进攻次数
     **/
    private Long attackTimeLeft;

    /**
     * 防守成功次数
     **/
    private Long defenseNoStarTime;

    /**
     * 防守一星次数
     **/
    private Long defenseOneStarTime;

    /**
     * 防守二星次数
     **/
    private Long defenseTwoStarTime;

    /**
     * 防守三星次数
     **/
    private Long defenseThreeStarTime;

    /**
     * 总防守百分比
     **/
    private Long totalDefensePercentage;

    /**
     * 总防守次数
     **/
    private Long totalDefenseTime;

    /**
     * 总进攻星星
     **/
    private Long totalAttackStar;

    /**
     * 总防守星星
     **/
    private Long totalDefenseStar;

    /**
     * 所属赛季
     **/
    private String season;

    private Boolean deleted;

    private Date createTime;

    private Date updateTime;
}