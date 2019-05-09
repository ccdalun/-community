package com.huahua.article.dao;

import com.huahua.article.pojo.CommentMongoDB;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommenMongoDBDao extends MongoRepository<CommentMongoDB,String> {

    /**
     * 根据文章ID查询评论列表
     */
    public List<CommentMongoDB> findByArticleid(String articleid);
}
