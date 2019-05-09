/**
 * Date:    2019/4/28 21:47
 * <author>
 * 陈柏
 */
package com.huahua.friend.controller;

import com.huahua.friend.service.FriendService;
import huahua.common.Result;
import huahua.common.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 添加好友
     * @param friendid 对方用户ID
     * @param type 1：喜欢 0：不喜欢
     * @return
     */
    @RequestMapping(value="/like/{friendid}/{type}",method= RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid , @PathVariable
            String type){
        Claims claims=(Claims)request.getAttribute("user_claims");
        if(claims==null){
            return new Result(false, StatusCode.AUTOROLES,"无权访问");
        }
        //如果是喜欢
        if(type.equals("1")){
            if(friendService.addFriend(claims.getId(),friendid)==0){
                return new Result(false, StatusCode.ERROR,"已经添加此好友");
            }
        }else{
        //不喜欢
            friendService.addNoFriend(claims.getId(),friendid);
        }
        return new Result(true, StatusCode.OK, "操作成功");
    }

    @RequestMapping(value = "/{friendid}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String friendid){
        Claims claims = (Claims) request.getAttribute("user_claims");
        if(claims == null){
            return new Result(false,StatusCode.AUTOROLES,"无权删除");
        }
        //通过token穿过来的userid。
        friendService.deleteFriend(claims.getId(),friendid);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
