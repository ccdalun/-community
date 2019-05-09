/**
 * Date:    2019/4/18 15:43
 * <author>
 * 陈柏
 */
package com.huahua.article.service;

import com.huahua.article.dao.CommenMongoDBDao;
import com.huahua.article.pojo.CommentMongoDB;
import huahua.common.utils.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommenMongoDBService {

    @Autowired
    private CommenMongoDBDao commenMongoDBDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 新增
     * @param commentMongoDB
     */
    public void add(CommentMongoDB commentMongoDB){
        commentMongoDB.set_id(idWorker.nextId()+"");
        commentMongoDB.setPublishdate(new Date());
        commenMongoDBDao.save(commentMongoDB);
    }


    /**
     * 根据id来查询文章
     * @param articleid
     * @return
     */
    public List<CommentMongoDB> findByArticleid(String articleid){
        return commenMongoDBDao.findByArticleid(articleid);
    }

    /**
     * 删除
     */
    public void delete(String ids) {
        if(StringUtils.isNotEmpty(ids)){
          String[] split = ids.split(",");
            for (String s : split){
                commenMongoDBDao.deleteById(s);
            }
         }
    }
}
