/**
 * Date:    2019/4/17 19:28
 * <author>
 * 陈柏
 */
package com.huahua.spit.service;

import com.huahua.spit.dao.SpitDao;
import com.huahua.spit.entiy.Spit;
import huahua.common.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SpitService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SpitDao spitDao;

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 查询全部记录
     * @return
     */
    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    /**
     * 通过Id主键查询实体
     */
    public Spit findById(String id){
        Spit spit = spitDao.findById(id).get();
        return  spit;
    }

    /**
     * 增加
     */
    public void add(Spit spit){
        spit.set_id(idWorker.nextId()+"");
        spit.setPublishtime(new Date());
        spit.setVisits(0);
        spit.setShare(0);
        spit.setThumbup(0);
        spit.setComment(0);
        spit.setState("1");
        //如果不为空，则修改parentId用户数据的Comment+1
        if(spit.getParentid()!=null && !"".equals(spit.getParentid())){
            //如果存在上级ID,评论
            Query query=new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update=new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
        spitDao.save(spit);
    }

    /**
     * 修改
     */
    public void update(Spit spit){
        spitDao.save(spit);
    }

    /**
     * 删除
     */
    public void delete(String id){
        spitDao.deleteById(id);
    }

    /**
     * 根据上级id查询吐槽列表
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> findByPidlist(String parentid, Integer page, Integer size) {
        PageRequest of = PageRequest.of(page-1,size);
        return   spitDao.findByParentid(parentid,of);
    }

    public void updateThumbup(String spitId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }

    public void updateShare(String spitId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("share",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }

    public void updateVisits(String spitId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("visits",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }
}
