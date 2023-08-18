package com.seiii.collect.service.recommend;

import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.task.TaskViewVO;

import java.util.List;

public interface RecommendService {
    ResponseVO<Object> refreshRecommendationResult();
    List<TaskViewVO> getAllRecommendedTasksForUser(Integer userId);
}
