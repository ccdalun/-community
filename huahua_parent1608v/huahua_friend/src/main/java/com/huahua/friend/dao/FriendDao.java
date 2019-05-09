/**
 * Date:    2019/4/28 21:27
 * <author>
 * 陈柏
 */
package com.huahua.friend.dao;

import com.huahua.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String>,JpaSpecificationExecutor<Friend>{

    /**
     * 更新为互相喜欢
     * @param userid
     * @param friendid
     * @param islike
     */
    @Modifying
    @Query(value = "update tb_friend set islike=?3 where userid = ?1 and friendid=?2",nativeQuery = true)
    public void updateLike(String userid,String friendid,String islike);

    /**
     * 根据用户ID与被关注用户ID查询记录个数
     * @param userid
     * @param friendid
     * @return
     */
    @Query(nativeQuery = true,value = "select count(1) from tb_friend where friendid = ?2 and userid =?1 ")
    public int selectCount(String userid,String friendid);

    /**
     * 删除好友
     * @param userid
     * @param friendid
     */
    @Modifying
    @Query(nativeQuery = true,value = "delete from tb_friend where userid=?1 and friendid = ?2")
    public void deleteFriend(String userid,String friendid);
}
