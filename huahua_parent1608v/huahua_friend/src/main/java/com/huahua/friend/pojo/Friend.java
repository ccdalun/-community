/**
 * Date:    2019/4/28 21:26
 * <author>
 * 陈柏
 */
package com.huahua.friend.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="tb_friend")
@IdClass(Friend.class)
@Data
public class Friend implements Serializable {

    @Id
    private String userid;
    @Id
    private String friendid;

    private String islike;
}
