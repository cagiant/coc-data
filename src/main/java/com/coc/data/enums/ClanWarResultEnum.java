package com.coc.data.enums;

/**
 * @author guokaiqiang
 * @date 2021/7/28 12:46
 */
public enum ClanWarResultEnum {

	WIN("win", "胜利"),
	LOSE("lose", "失败"),
	UNKNOWN("unknown", "未知"),
	DRAW("draw", "平局");

	public String code;
	public String msg;

	ClanWarResultEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
