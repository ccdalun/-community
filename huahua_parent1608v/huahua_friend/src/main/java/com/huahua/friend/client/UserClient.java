/**
 * Date:    2019/4/29 10:34
 * <author>
 * 陈柏
 */
package com.huahua.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//从花花User微服务调用功能 不能使用下划线
@FeignClient("huahua-user")
public interface UserClient {

    //地址映射要添加被调用微服务地址
    @RequestMapping(value = "/user/incfans/{userid}/{x}", method = RequestMethod.POST)
    public void incFanscount(@PathVariable("userid") String userid,
                             //pathvariable必须制定参数名称 否则会报错
                             @PathVariable("x") int x);

    @RequestMapping(value = "/user/incfollow/{userid}/{x}",method = RequestMethod.POST)
    public void incFollowcount(@PathVariable("userid") String userid,
                               @PathVariable("x") int x);
}
