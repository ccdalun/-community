/**
 * Date:    2019/4/17 19:12
 * <author>
 * 陈柏
 */
package com.huahua.spit.controller;

import com.huahua.spit.entiy.Spit;
import com.huahua.spit.service.SpitService;
import huahua.common.PageResult;
import huahua.common.Result;
import huahua.common.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/spit")
@CrossOrigin
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HttpServletRequest request;

    /**
     *查询所有
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return  new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }

    /**
     *根据id查询
     */
    @RequestMapping(method = RequestMethod.GET,value = "/{id}")
    public Result findByid(@PathVariable String id){
        return  new Result(true, StatusCode.OK,"查询成功",spitService.findById(id));
    }

    /**
     *添加
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit){
        Claims claims = (Claims) request.getAttribute("user_claims");
        if(claims==null){
            return new Result(false,StatusCode.AUTOROLES,"无权访问");
        }
        spit.setUserid(claims.getId());
        spitService.add(spit);
        return  new Result(true, StatusCode.OK,"添加成功");
    }

    /**
     *修改
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/{id}")
    public Result update(@RequestBody Spit spit,@PathVariable String id){
        spit.set_id(id);
        spitService.update(spit);
        return  new Result(true, StatusCode.OK,"修改成功");
    }


    /**
     *删除
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id){
        spitService.delete(id);
        return  new Result(true, StatusCode.OK,"删除成功");
    }

    /**
     *根据上级Id查询吐槽数据（分页）
     */
    @RequestMapping(method = RequestMethod.GET,value = "/comment/{parentid}/{page}/{size}")
    public Result findByPidList(@PathVariable("parentid") String parentid,
                                @PathVariable("page") Integer page,
                                @PathVariable("size") Integer size){
        Page<Spit> spitList = spitService.findByPidlist(parentid,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(spitList.getTotalElements(),spitList.getContent()));
    }

    /**
     * 吐槽点赞
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/thumbup/{spitId}")
    public Result updateThumbUp(@PathVariable("spitId") String spitId){
        //判断用户是否点过赞String userid="2023";// 后边我们会修改为当前登陆的用户
        String userid = "2224";
        if(null != redisTemplate.opsForValue().get("thumbup_"+userid+"_"+spitId)){
            return new Result(false,StatusCode.ERROR,"你已经点过赞了");}
        spitService.updateThumbup(spitId);
        redisTemplate.opsForValue().set( "thumbup_"+userid+"_"+ spitId,"1");
        return new Result(true,StatusCode.OK,"点赞成功");
    }

    /**
     *分享
     */
    @RequestMapping(value = "/share/{spitiI}",method = RequestMethod.PUT)
    public Result updateShare(@PathVariable String spitId){
        spitService.updateShare(spitId);
        return  new Result(true, StatusCode.OK,"分享成功");
    }
    /**
     *浏览
     */
    @RequestMapping(value = "/visits/{spitiId}",method = RequestMethod.PUT)
    public Result updateVisits(@PathVariable String spitId){
        spitService.updateVisits(spitId);
        return  new Result(true, StatusCode.OK,"浏览成功");
    }
}
