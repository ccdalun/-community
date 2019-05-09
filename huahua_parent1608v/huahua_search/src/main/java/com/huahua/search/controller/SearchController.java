/**
 * Date:    2019/4/22 14:03
 * <author>
 * 陈柏
 */
package com.huahua.search.controller;

import com.huahua.search.pojo.ArticleEs;
import com.huahua.search.service.SearchService;
import huahua.common.PageResult;
import huahua.common.Result;
import huahua.common.StatusCode;
import jdk.internal.org.objectweb.asm.commons.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin
@RequestMapping("search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/search/{keywords}/{page}/{size}",method = RequestMethod.GET)
    public Result searchArticle(@PathVariable("keywords")String keywords,
                                @PathVariable("page")Integer page,
                                @PathVariable("size")Integer size){
        Page<ArticleEs> list = searchService.searchArticle(keywords,page,size);
        return new Result(true, StatusCode.OK,"查询成功",new PageResult<>(list.getTotalElements(),list.getContent()));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Result add(@RequestBody ArticleEs articleEs){
        searchService.add(articleEs);
        return  new Result(true,StatusCode.OK,"添加成功");
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable("id")String id){
        searchService.delete(id);
        return  new Result(true,StatusCode.OK,"删除成功");
    }

    @RequestMapping(method = RequestMethod.PUT,value = "/{id}")
    public Result update(@RequestBody ArticleEs articleEs,@PathVariable String id){
        articleEs.setId(id);
        searchService.update(articleEs);
        return  new Result(true, StatusCode.OK,"修改成功");
    }

    /**
     *根据id查询
     */
    @RequestMapping(method = RequestMethod.GET,value = "/{id}")
    public Result findByid(@PathVariable String id){
        return  new Result(true, StatusCode.OK,"查询成功",searchService.findById(id));
    }
}

