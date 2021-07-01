package com.coc.data.enums;

/**
 * @author guokaiqiang
 * @date 2021/7/1 13:03
 */
public enum MiniprogramMessageSenderEnum {

	SYSTEM("system", "系统");

	public String code;
	public String msg;

	MiniprogramMessageSenderEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
