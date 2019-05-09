/**
 * Date:    2019/4/28 20:08
 * <author>
 * 陈柏
 */
package com.huahua.qa.client;

import com.huahua.qa.client.impl.LabelClientImpl;
import huahua.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//feignCLient调用那个服务
@FeignClient(value = "huahua-base",fallback = LabelClientImpl.class)
public interface LabelClient {

    //PathVariable  获取地址上的值 value=写全路径
    @RequestMapping(method = RequestMethod.GET, value = "/label/{labelId}")
    public Result queryById(@PathVariable("labelId") String labelId);
}
