/**
 * Date:    2019/4/18 15:27
 * <author>
 * 陈柏
 */
package com.huahua.article.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Data
public class CommentMongoDB implements Serializable {

    private static final long serialVersionUID = 1191474727362608831L;
    @Id
    private String _id;
    //文章Id
    private String articleid;
    //评论内容
    private String content;
    //评论人ID
    private String userid;
    //评论ID 如果为0表示文章的顶级评论
    private String parentid;
    //评论日期
    private Date publishdate;
}
