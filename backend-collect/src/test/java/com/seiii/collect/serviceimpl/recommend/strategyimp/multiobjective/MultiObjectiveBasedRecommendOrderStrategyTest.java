package com.seiii.collect.serviceimpl.recommend.strategyimp.multiobjective;

import com.seiii.collect.mapper.recommend.MultiObjectiveRecommendResultMapper;
import com.seiii.collect.model.po.recommend.MultiObjectiveRecommendResult;
import com.seiii.collect.model.vo.task.TaskViewVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class MultiObjectiveBasedRecommendOrderStrategyTest {
    @Resource
    MultiObjectiveBasedRecommendOrderStrategy multiObjectiveBasedRecommendOrderStrategy;
    @MockBean
    MultiObjectiveRecommendResultMapper multiObjectiveRecommendResultMapper;

    @Test
    void recommend() {
        List<TaskViewVO> candidateTasks = Stream.of(5, 1, 6, 2, 7, 8, 9, 3, 10, 4).map(id -> {
            TaskViewVO taskViewVO = new TaskViewVO();
            taskViewVO.setId(id);
            return taskViewVO;
        }).collect(Collectors.toList());
        Mockito.when(multiObjectiveRecommendResultMapper.selectByUserId(1))
                .thenReturn(Stream.of(1, 2, 3, 4)
                        .map(taskId -> new MultiObjectiveRecommendResult().setTaskid(taskId).setUserid(1))
                        .collect(Collectors.toList()));
        List<Integer> actualResult = multiObjectiveBasedRecommendOrderStrategy.recommend(1, candidateTasks)
                .stream().map(TaskViewVO::getId).collect(Collectors.toList());
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Mockito.verify(multiObjectiveRecommendResultMapper).selectByUserId(1);
        Assertions.assertEquals(expected, actualResult);

    }
}