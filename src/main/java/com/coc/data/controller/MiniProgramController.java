package com.coc.data.controller;

import com.coc.data.service.MiniProgramMessageService;
import com.coc.data.util.CheckUtil;
import com.coc.data.util.FormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author guokaiqiang
 * @date 2020/10/4 09:27
 */
@RestController
@Slf4j
@RequestMapping("/miniProgram")
public class MiniProgramController {

    @Resource
    private MiniProgramMessageService miniProgramMessageService;

    @GetMapping("/test")
    void test() {
        miniProgramMessageService.sendWarResultMessage("三星通知", "戴尔笔记本进攻对方4号位，获得三星", null,
            "oabXk5OGOt_oR0yw3PRGLahy_pwQ");
    }

    @GetMapping("/msgCallback")
    public String getMsgCallback(HttpServletRequest request) {
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }

        return "";
    }

    @PostMapping("/msgCallback")
    public String postMsgCallback(HttpServletRequest request) {
//        log.info(FormatUtil.serializeObject2JsonStr(request));

        return "success";
    }
}
