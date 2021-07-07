package com.coc.data.enums;

/**
 * 用来描述一个部落相对于一个玩家的状态
 * @author guokaiqiang
 * @date 2021/7/6 21:48
 */
public enum UserClanStateEnum {

	PENDING("pending", "已发送关注申请，待批准"),
	OK("ok", "关注中"),
	REJECTED("rejected", "已拒绝"),
	BLOCKED("blocked", "已屏蔽");

	public String code;
	public String msg;

	UserClanStateEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
