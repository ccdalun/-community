/**
 * Date:    2019/4/29 9:30
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
@Table(name = "tb_nofriend")
@IdClass(NoFriend.class)
@Data
public class NoFriend implements Serializable {


    @Id
    private String userid;

    @Id
    private String friendid;



}
