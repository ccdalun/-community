/**
 * Date:    2019/4/24 19:47
 * <author>
 * 陈柏
 */
package com.huahua.sms.smslistener;

import com.huahua.sms.send.SendCodeUtils;
import com.huahua.sms.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    /**
     * 发送短信
     */
    @RabbitHandler
    public void sendSms(Map<String,String> map){
        System.out.println("手机号："+map.get("mobile"));
        System.out.println("验证码："+map.get("code"));
        SmsListener.sendCode(map.get("mobile"),map.get("code"));
    }

    public static void sendCode(String moblie, String code) {
        String host = "https://dxyzm.market.alicloudapi.com";
        String path = "/chuangxin/dxjk";
        String method = "POST";
        String appcode = "78766b41599b42dc8e7be8a11d5edc16";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("content", "【创信】你的验证码是："+code+"，3分钟内有效！");
        querys.put("mobile", moblie);
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
