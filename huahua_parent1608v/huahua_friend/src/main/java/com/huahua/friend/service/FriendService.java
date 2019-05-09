/**
 * Date:    2019/4/28 21:39
 * <author>
 * 陈柏
 */
package com.huahua.friend.service;

import com.huahua.friend.client.UserClient;
import com.huahua.friend.dao.FriendDao;
import com.huahua.friend.dao.NoFriendDao;
import com.huahua.friend.pojo.Friend;
import com.huahua.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    @Autowired
    private  UserClient userClient;



    public int addFriend(String userid,String friendid){
        //判断如果用户已经添加了这个好友，则不进行任何操作,返回0
        if(friendDao.selectCount(userid,friendid)>0){
            return 0;
        }
        //向喜欢表中添加记录
        Friend friend=new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);
        //判断对方是否喜欢你，如果喜欢，将islike设置为1
        if(friendDao.selectCount( friendid,userid)>0){
            friendDao.updateLike(userid,friendid,"1");
            friendDao.updateLike(friendid,userid,"1");
        }
        //调用Spring cloud 微服务修改用户表的关注数以及粉丝数
        //首先是关注数user1 +1 粉丝数就是user2+1
        userClient.incFanscount(userid,1);
        userClient.incFollowcount(userid,1);
        return 1;
    }

    /**
     * 向不喜欢列表中添加记录
     * @param userid
     * @param friendid
     */
    public void addNoFriend(String userid,String friendid){
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
        /*
            friendDao.deleteByuseridAndFriendid(userid,friendid);
            if(friendDao.selectByUserCount(friendid,userid)>0){
                frienddao.updateLike(friendid,userid,islike:"0");
            }
        */
    }

    /**
     * 删除好友
     * @param userid
     * @param friendid
     */
    public void deleteFriend(String userid,String friendid){
        //根据userid，friendid删除好友
      friendDao.deleteFriend(userid,friendid);
      //修改互粉为0
      friendDao.updateLike(friendid,userid,"0");
      //像不喜欢表中添加记录
      addNoFriend(userid,friendid);
        userClient.incFanscount(friendid,-1);
        userClient.incFollowcount(userid,-1);
    }
}
