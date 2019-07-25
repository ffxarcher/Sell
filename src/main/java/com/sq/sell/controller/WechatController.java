package com.sq.sell.controller;

import com.sq.sell.enums.ResultEnum;
import com.sq.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

/**
 * 7.22完成授权测试，请注意发送的网址
 * 注意openid的写法估计是需要与前段匹配
 * 否则会出现持续跳转的问题
 *
 * nginx服务器的代理地址需要跟随电脑的ip地址做转换否则无法访问
 * 报错502 bad——gateway
 *对应的host sell.com的地址务必指向当前虚拟机的地址
 *
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl")String returnUrl)
    {
        String url = "http://sqmysell.natapp1.cc/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_USER_INFO, URLEncoder.encode(returnUrl));
        return "redirect:" + redirectUrl;
    }

    @GetMapping("userInfo")
    public String userInfo(@RequestParam("code")String code,
                           @RequestParam("state")String returnUrl)
    {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try {
             wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
           log.error("【微信网页授权】{}",e);
           throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        return "redirect:" + returnUrl +"?openid=" + openId;
    }
}
