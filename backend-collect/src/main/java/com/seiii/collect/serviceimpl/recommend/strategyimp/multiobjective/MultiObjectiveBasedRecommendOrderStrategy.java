package com.seiii.collect.serviceimpl.recommend.strategyimp.multiobjective;

import com.seiii.collect.mapper.recommend.MultiObjectiveRecommendResultMapper;
import com.seiii.collect.model.po.recommend.MultiObjectiveRecommendResult;
import com.seiii.collect.model.vo.task.TaskViewVO;
import com.seiii.collect.serviceimpl.recommend.strategy.RecommendStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MultiObjectiveBasedRecommendOrderStrategy implements RecommendStrategy {
    private final MultiObjectiveRecommendResultMapper multiObjectiveRecommendResultMapper;



    @Override
    public List<TaskViewVO> recommend(Integer targetUserId, List<TaskViewVO> candidateTasks) {
        List<MultiObjectiveRecommendResult> resultList = multiObjectiveRecommendResultMapper.selectByUserId(targetUserId);
        Set<Integer> recommendedTasks = resultList.stream().map(MultiObjectiveRecommendResult::getTaskid).collect(Collectors.toSet());
        List<TaskViewVO> noRecommendedTasks = new ArrayList<>();
        List<TaskViewVO> result = new ArrayList<>(candidateTasks.size());
        for (TaskViewVO task : candidateTasks) {
            if(recommendedTasks.contains(task.getId()))result.add(task);
            else noRecommendedTasks.add(task);
        }
        result.addAll(noRecommendedTasks);
        return result;
    }
}
