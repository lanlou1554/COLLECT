package com.seiii.collect.serviceimpl.recommend;

import com.seiii.collect.model.po.task.TaskView;
import com.seiii.collect.model.vo.task.TaskViewVO;

import java.util.List;

public interface CandidateTasksProvider {
    List<TaskViewVO> provideCandidateTask(Integer userId);
    List<TaskView> provideUnfinishedTask();
}
