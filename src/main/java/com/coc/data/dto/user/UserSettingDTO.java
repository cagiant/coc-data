package com.coc.data.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guokaiqiang
 * @date 2021/6/28 21:28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSettingDTO {

	private Boolean warInfoMessage;

	private Boolean warResultMessage;
}
