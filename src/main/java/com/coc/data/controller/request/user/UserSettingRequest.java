package com.coc.data.controller.request.user;

import com.coc.data.dto.user.UserSettingDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guokaiqiang
 * @date 2021/6/28 21:38
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSettingRequest {

	private String openId;

	private UserSettingDTO userOption;
}
