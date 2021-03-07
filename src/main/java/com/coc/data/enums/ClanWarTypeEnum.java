package com.coc.data.enums;

/**
 * @author guokaiqiang
 * @date 2021/3/7 17:03
 */
public enum ClanWarTypeEnum {
    /**
     * 战争类型
     **/
    NORMAL("normal", "日常部落战"),
    LEAGUE("league", "部落对战联赛");

    public String code;
    public String msg;

    ClanWarTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
