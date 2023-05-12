package com.asleepyfish.controller;

import com.alibaba.fastjson2.JSON;
import com.asleepyfish.constants.WXConstants;
import com.asleepyfish.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class WXController {
    @RequestMapping("/wx")
    public String getWxQRCodeParam() {
        String QRUrl = null;
        try {
            // 第一步：发送请求获取access_token
            String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
                    "&appid=" + WXConstants.APP_ID +
                    "&secret=" + WXConstants.APP_SECRET;
            String accessTokenRes = HttpClientUtils.doGet(getAccessTokenUrl);
            log.info("accessTokenRes=>" + accessTokenRes);
            String accessToken = (String) JSON.parseObject(accessTokenRes).get("access_token"); // 获取到access_token

            // 第二步：通过access_token和一些参数发送post请求获取二维码Ticket
            String getTicketUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken;
            // 封装参数
            Map<String, Object> ticketInfo = new HashMap<>();
            ticketInfo.put("expire_seconds", 604800); // 二维码超时时间
            ticketInfo.put("action_name", "QR_SCENE");
            String ticketJsonInfo = JSON.toJSON(ticketInfo).toString();
            String ticketRes = HttpClientUtils.doPostJson(getTicketUrl, ticketJsonInfo);
            log.info("ticketRes=>" + ticketRes);
            String ticket = (String) JSON.parseObject(ticketRes).get("ticket");

            // 第三步：通过ticket获取二维码url
            String encodeTicket = URLEncoder.encode(ticket, "utf-8"); // 编码ticket
            QRUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + encodeTicket; // 二维码url
        } catch (Exception e) {
            e.printStackTrace();
        }
        return QRUrl;
    }
}
