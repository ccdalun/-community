/**
 * Date:    2019/4/22 14:02
 * <author>
 * 陈柏
 */
package com.huahua.search.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

@Data
@Document(indexName = "huahua_article",type = "article")
public class ArticleEs implements Serializable {

    @Id
    private String id;

    @Field(index= true,analyzer="ik_max_word",searchAnalyzer="ik_max_word")
    private String title;

    @Field(index= true,analyzer="ik_max_word",searchAnalyzer="ik_max_word")
    private String content;

    private String state;
}
