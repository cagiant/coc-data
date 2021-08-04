package com.coc.data.controller.vo.war;

import lombok.Builder;
import lombok.Data;

/**
 * @author guokaiqiang
 * @date 2021/8/4 21:35
 */
@Data
@Builder
public class ClanWarMemberVO {

    private String tag;

    private String name;

    private Long mapPosition;

    private Integer remainedAttack;
}
