package com.huahua.spit.dao;

import com.huahua.spit.entiy.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpitDao extends MongoRepository<Spit,String> {

    Page<Spit> findByParentid(String parenid, Pageable pageable);
}
