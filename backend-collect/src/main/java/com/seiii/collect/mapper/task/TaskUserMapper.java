package com.seiii.collect.mapper.task;

import com.seiii.collect.model.po.task.TaskUser;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskUserMapper {
    int deleteByPrimaryKey(@Param("taskid") Integer taskid, @Param("userid") Integer userid);

    int insert(TaskUser record);

    List<TaskUser> selectAll();

    List<TaskUser> selectByTaskIds(List<Integer> idList);

    List<Integer> selectByUserId(Integer userid);

    List<Integer> selectByTaskId(Integer taskid);
}