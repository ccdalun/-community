package com.huahua.search.dao;

import com.huahua.search.pojo.ArticleEs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchDao extends ElasticsearchRepository<ArticleEs,String> {

    Page<ArticleEs> findAllByTitleLikeOrContentLike(String title,String content,Pageable pageable);
}
