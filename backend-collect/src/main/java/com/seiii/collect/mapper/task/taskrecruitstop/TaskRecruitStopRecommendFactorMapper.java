package com.seiii.collect.mapper.task.taskrecruitstop;

import com.seiii.collect.model.po.task.taskrecruitstop.TaskRecruitStopRecommendFactor;
import java.util.List;

public interface TaskRecruitStopRecommendFactorMapper {
    int deleteAll();

    void insertAll(List<TaskRecruitStopRecommendFactor> records);

    TaskRecruitStopRecommendFactor selectByPrimaryKey(Integer taskid);

    List<TaskRecruitStopRecommendFactor> selectAll();

    int updateByPrimaryKey(TaskRecruitStopRecommendFactor record);
}