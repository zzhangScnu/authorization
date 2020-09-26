package com.lihua.authorization.persistent.repository;

import com.lihua.authorization.persistent.pojo.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
