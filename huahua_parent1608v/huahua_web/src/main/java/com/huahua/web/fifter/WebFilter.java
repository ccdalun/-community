/**
 * Date:    2019/5/5 10:21
 * <author>
 * 陈柏
 */
package com.huahua.web.fifter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebFilter extends ZuulFilter {

    /**
     * pre ：可以在请求被路由之前调用
     * route ：在路由请求时候被调用
     * post ：在route和error过滤器之后被调用
     * error ：处理请求时发生错误时被调用
     * @return
     */
    @Override
    public String filterType() {
        //前置过滤器
        return "pre";
    }

    //通过int值来定义过滤器的执行顺序
    @Override
    public int filterOrder() {
        //优先级为0，数字越大，优先级越低
        return 0;
    }

    /**
     * 返回一个boolean类型来判断该过滤器是否要执行
     * 所以通过此函数可实现过滤器的开关
     * @return
     */
    @Override
    public boolean shouldFilter() {
        //是否执行该过滤器,此处为true,说明需要过滤器
        return true;
    }

    //过滤器的具体逻辑
    @Override
    public Object run() throws ZuulException {
        System.out.println("欢迎来到zuul过滤器");
        //向heade中添加鉴权令牌
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取header
        HttpServletRequest request = requestContext.getRequest();
        String authorization = request.getHeader("Authorization");
        if(null != authorization){
            requestContext.addZuulRequestHeader("authorization",authorization);
        }
        return null;
    }
}
