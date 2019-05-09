/**
 * Date:    2019/4/18 15:46
 * <author>
 * 陈柏
 */
package com.huahua.article.controller;

import com.huahua.article.pojo.CommentMongoDB;
import com.huahua.article.service.CommenMongoDBService;
import huahua.common.Result;
import huahua.common.StatusCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentMongoDBController {

    @Autowired
    private CommenMongoDBService commenMongoDBService;

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody CommentMongoDB commentMongoDB){
        if(StringUtils.isEmpty(commentMongoDB.getArticleid())){
            return new Result(true, StatusCode.OK,"提交失败");
        }
        commenMongoDBService.add(commentMongoDB);
        return new Result(true, StatusCode.OK,"提交成功");
    }

    @RequestMapping(value = "/article/{articleid}",method = RequestMethod.POST)
    public Result findByarticleid(@PathVariable String articleid){
        return new Result(true, StatusCode.OK,"查询成功",commenMongoDBService.findByArticleid(articleid));
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/{ids}")
    public Result delete(@PathVariable String ids){
        commenMongoDBService.delete(ids);
        return new Result(true, StatusCode.OK,"删除成功");
    }
}
