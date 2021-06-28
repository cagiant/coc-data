package com.coc.data.enums;

/**
 * @author guokaiqiang
 * @date 2021/6/28 21:15
 */
public enum MiniProgramTemplateEnum {

	/**
	 *
	 **/
	WAR_INFO("war_info", "部落战开战信息","XBAMCzW4bpOHHU-6XyWXgAZnAlVzJyWnuwGJ3tmeSko"),
	WAR_RESULT("war_result", "部落战结果信息", "QAdeMBLZLCN8TvuiVsDM-eL1hXgoU7H-nSNdS1rJIGE");

	public String code;
	public String msg;
	public String templateId;

	MiniProgramTemplateEnum(String code, String msg, String templateId) {
		this.code = code;
		this.msg = msg;
		this.templateId = templateId;
	}
}
