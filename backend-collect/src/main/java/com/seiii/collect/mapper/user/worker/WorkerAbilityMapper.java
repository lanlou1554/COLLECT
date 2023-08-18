package com.seiii.collect.mapper.user.worker;

import com.seiii.collect.model.po.user.worker.WorkerAbility;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WorkerAbilityMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(WorkerAbility record);

    WorkerAbility selectByPrimaryKey(Integer userid);

    List<WorkerAbility> selectAll();

    int updateByPrimaryKey(WorkerAbility record);
}