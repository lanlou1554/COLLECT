package com.seiii.collect.mapper.user.worker;

import com.seiii.collect.model.po.user.worker.WorkerContext;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Mapper
public interface WorkerContextMapper {
    int deleteByPrimaryKey(Integer workerid);

    int insert(WorkerContext record);
    @Cacheable(cacheNames = "WorkerContext-selectByPrimaryKey")
    WorkerContext selectByPrimaryKey(Integer workerid);

    List<WorkerContext> selectAll();
    @CacheEvict(cacheNames = "WorkerContext-selectByPrimaryKey",key = "#p0.getWorkerid()")
    int updateByPrimaryKey(WorkerContext record);
}