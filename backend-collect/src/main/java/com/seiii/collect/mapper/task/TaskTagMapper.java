package com.seiii.collect.mapper.task;

import com.seiii.collect.model.po.task.TaskTag;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskTagMapper {
    int deleteByPrimaryKey(@Param("taskid") Integer taskid, @Param("tag") Integer tag);

    int insert(TaskTag record);

    List<TaskTag> selectAll();

    List<Integer> selectByTaskId(Integer taskid);
}