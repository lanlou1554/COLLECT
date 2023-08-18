package com.seiii.collect.serviceimpl.recommend.strategy;


import com.seiii.collect.model.vo.task.TaskViewVO;

import java.util.List;

public interface RecommendStrategy {
    List<TaskViewVO> recommend(Integer targetUserId, List<TaskViewVO> candidateTasks);
}
