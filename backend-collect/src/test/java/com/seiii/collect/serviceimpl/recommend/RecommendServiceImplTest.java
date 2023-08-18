package com.seiii.collect.serviceimpl.recommend;

import com.seiii.collect.mapper.recommend.MultiObjectiveRecommendFactorMapper;
import com.seiii.collect.mapper.recommend.MultiObjectiveRecommendResultMapper;
import com.seiii.collect.mapper.task.TaskMapper;
import com.seiii.collect.mapper.task.TaskUserMapper;
import com.seiii.collect.mapper.task.taskrecruitstop.TaskRecruitStopRecommendFactorMapper;
import com.seiii.collect.mapper.user.UserMapper;
import com.seiii.collect.model.po.recommend.MultiObjectiveRecommendFactor;
import com.seiii.collect.model.po.recommend.MultiObjectiveRecommendResult;
import com.seiii.collect.model.po.task.TaskUser;
import com.seiii.collect.model.po.task.TaskView;
import com.seiii.collect.model.vo.task.TaskViewVO;
import com.seiii.collect.service.task.TaskService;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.MultiObjectiveSelection;
import com.seiii.collect.serviceimpl.recommend.strategyimp.multiobjective.MultiObjectiveBasedRecommendOrderStrategy;
import com.seiii.collect.serviceimpl.recommend.strategyimp.rulebased.RuleBasedRecommendOrderStrategy;
import com.seiii.collect.util.Constant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@RunWith(SpringRunner.class)
class RecommendServiceImplTest {

    @Resource
    RecommendServiceImpl recommendService;
    @MockBean
    MultiObjectiveSelection multiObjectiveSelection;

    @MockBean
    UserMapper userMapper;
    @MockBean
    MultiObjectiveRecommendResultMapper multiObjectiveRecommendResultMapper;
    @MockBean
    RuleBasedRecommendOrderStrategy ruleBasedRecommendOrderStrategy;
    @MockBean
    MultiObjectiveBasedRecommendOrderStrategy multiObjectiveBasedRecommendOrderStrategy;
    @MockBean
    CandidateTasksProvider candidateTasksProvider;
    @MockBean
    TaskService taskService;
    @MockBean
    TaskUserMapper taskUserMapper;
    @MockBean
    MultiObjectiveRecommendFactorMapper multiObjectiveRecommendFactorMapper;
    @MockBean
    TaskRecruitStopRecommendFactorMapper taskRecruitStopRecommendFactorMapper;


    @Test
    void refreshRecommendationResult() {
        MultiObjectiveSelection selection = multiObjectiveSelection;
        Mockito.when(selection.selectFromCandidateUsersAboutTask(Mockito.eq(Arrays.asList(0, 1, 2)), Mockito.eq(0), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Arrays.asList(0, 1));
        Mockito.when(selection.selectFromCandidateUsersAboutTask(Mockito.eq(Arrays.asList(0, 1, 2)), Mockito.eq(1), Mockito.any(),Mockito.any(), Mockito.any())).thenReturn(Arrays.asList(1, 2));
        Mockito.when(selection.selectFromCandidateUsersAboutTask(Mockito.eq(Arrays.asList(0, 1, 2)), Mockito.eq(2), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Arrays.asList(2, 0));

        Mockito.when(taskUserMapper.selectByTaskIds(Mockito.any())).thenReturn(new ArrayList<>());
        Mockito.when(candidateTasksProvider.provideUnfinishedTask())
                .thenReturn(Stream.of(0, 1, 2).map(id -> {
                    TaskView taskView = new TaskView();
                    taskView.setId(id);
                    return taskView;
                }).collect(Collectors.toList()));

        Mockito.when(userMapper.selectIdByType(Constant.USER_TYPE_WORKER))
                .thenReturn(Arrays.asList(0, 1, 2));


        Mockito.when(multiObjectiveRecommendFactorMapper.selectByTaskIds(Mockito.argThat(tasks -> tasks.get(0).equals(0) && tasks.get(1).equals(1) && tasks.get(2).equals(2))))
                .thenReturn(Stream.of(0, 1, 2).map(id -> {
                    MultiObjectiveRecommendFactor factor = new MultiObjectiveRecommendFactor();
                    factor.setTaskid(id);
                    return factor;
                }).collect(Collectors.toList()));


        MultiObjectiveRecommendResultMapper resultMapper = multiObjectiveRecommendResultMapper;


        recommendService.refreshRecommendationResult();

        Mockito.verify(resultMapper).insertAll(Mockito.argThat(resultList -> new HashSet<>(resultList).equals(new HashSet<>(Arrays.asList(
                new MultiObjectiveRecommendResult().setUserid(0).setTaskid(0),
                new MultiObjectiveRecommendResult().setUserid(1).setTaskid(0),
                new MultiObjectiveRecommendResult().setUserid(1).setTaskid(1),
                new MultiObjectiveRecommendResult().setUserid(2).setTaskid(1),
                new MultiObjectiveRecommendResult().setUserid(2).setTaskid(2),
                new MultiObjectiveRecommendResult().setUserid(0).setTaskid(2)
        )))));
    }

    @Test
    void getAllRecommendedTasksForUserTestCache() {
            recommendService.getAllRecommendedTasksForUser(1);

        Mockito.verify(candidateTasksProvider).provideCandidateTask(1);

    }


    @Test
    void getAllRecommendedTasksForUser() {
        List<TaskViewVO> candidateTasks = Stream.of(1, 2, 3).map(id -> {
            TaskViewVO taskViewVO = new TaskViewVO();
            taskViewVO.setId(id);
            return taskViewVO;
        }).collect(Collectors.toList());

        List<TaskViewVO> process1 = Stream.of(3, 2, 1).map(id -> {
            TaskViewVO taskViewVO = new TaskViewVO();
            taskViewVO.setId(id);
            return taskViewVO;
        }).collect(Collectors.toList());

        List<TaskViewVO> result = Stream.of(2, 1, 3).map(id -> {
            TaskViewVO taskViewVO = new TaskViewVO();
            taskViewVO.setId(id);
            return taskViewVO;
        }).collect(Collectors.toList());

        Mockito.when(candidateTasksProvider.provideCandidateTask(2))
                .thenReturn(candidateTasks);
        Mockito.when(ruleBasedRecommendOrderStrategy.recommend(2, candidateTasks))
        .thenReturn(process1);
        Mockito.when(multiObjectiveBasedRecommendOrderStrategy.recommend(2, process1))
                .thenReturn(result);

        List<TaskViewVO> actualResult = recommendService.getAllRecommendedTasksForUser(2);


        Mockito.verify(candidateTasksProvider).provideCandidateTask(2);
        Mockito.verify(ruleBasedRecommendOrderStrategy).recommend(2, candidateTasks);
        Mockito.verify(multiObjectiveBasedRecommendOrderStrategy).recommend(2, process1);
        Assertions.assertEquals(result, actualResult);

    }

}