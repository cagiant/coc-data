package com.coc.data.enums;

/**
 * @author guokaiqiang
 * @date 2021/2/12 08:44
 */
public enum  ClanWarStateEnum {
    /**
     * 战争状态
     **/
    NO_IN("notInWar", "未开战"),
    PREPARATION("preparation", "准备日"),
    IN_WAR("inWar", "战斗日"),
    WAR_ENDED("warEnded", "战争已结束");

    public String code;
    public String msg;

    ClanWarStateEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ClanWarStateEnum getEnumByCode(String code) {
        for(ClanWarStateEnum enumTmp : ClanWarStateEnum.values()) {
            if (enumTmp.code.equals(code)) {
                return enumTmp;
            }
        }
        throw new EnumConstantNotPresentException(ClanWarStateEnum.class, "code=" + code);
    }
}
