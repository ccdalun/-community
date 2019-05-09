package com.huahua.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.huahua.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

	User findOneById(String id);

	User findByMobile(String mobile);

	/**
	 * 更新粉丝数
	 * @param userid
	 * @param x 粉丝数
	 */
	@Modifying
	@Query(nativeQuery = true,value = "update tb_user set fanscount = fanscount+?2 where id= ?1")
	public void incFanscount(String userid,int x);

	/**
	 * 更新关注数
	 * @param x 更煮熟
	 */
	@Modifying
	@Query(nativeQuery = true,value = "update tb_user set followcount= followcount+?2 where id= ?1")
	public void incFollowcount(String userid,int x);

}
