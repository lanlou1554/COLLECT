package com.seiii.collect.serviceimpl.recommend;


import com.seiii.collect.mapper.recommend.MultiObjectiveRecommendFactorMapper;
import com.seiii.collect.mapper.recommend.MultiObjectiveRecommendResultMapper;
import com.seiii.collect.mapper.task.TaskUserMapper;
import com.seiii.collect.mapper.task.taskrecruitstop.TaskRecruitStopRecommendFactorMapper;
import com.seiii.collect.mapper.user.UserMapper;
import com.seiii.collect.model.po.recommend.MultiObjectiveRecommendFactor;
import com.seiii.collect.model.po.recommend.MultiObjectiveRecommendResult;
import com.seiii.collect.model.po.task.TaskUser;
import com.seiii.collect.model.po.task.TaskView;
import com.seiii.collect.model.po.task.taskrecruitstop.TaskRecruitStopRecommendFactor;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.task.TaskViewVO;
import com.seiii.collect.service.recommend.RecommendService;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.MultiObjectiveSelection;
import com.seiii.collect.serviceimpl.recommend.strategyimp.multiobjective.MultiObjectiveBasedRecommendOrderStrategy;
import com.seiii.collect.serviceimpl.recommend.strategyimp.rulebased.RuleBasedRecommendOrderStrategy;
import com.seiii.collect.util.Constant;
import org.springframework.beans.BeansException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendServiceImpl implements RecommendService{
    @Resource
    private MultiObjectiveSelection multiObjectiveSelection;

    @Resource
    private UserMapper userMapper;
    @Resource
    private MultiObjectiveRecommendResultMapper multiObjectiveRecommendResultMapper;
    @Resource
    private RuleBasedRecommendOrderStrategy ruleBasedRecommendOrderStrategy;
    @Resource
    private MultiObjectiveBasedRecommendOrderStrategy multiObjectiveBasedRecommendOrderStrategy;

    @Resource
    private CandidateTasksProvider candidateTasksProvider;
    @Resource
    private MultiObjectiveRecommendFactorMapper multiObjectiveRecommendFactorMapper;
    @Resource
    private TaskRecruitStopRecommendFactorMapper taskRecruitStopRecommendFactorMapper;
    @Resource
    private TaskUserMapper taskUserMapper;




    @CacheEvict(cacheNames = {
            "getAllRecommendedTasksForUser",
            "getDomainKnowledge",
            "calSingleWorkerFactorValues",
            "WorkerContext-selectByPrimaryKey"
    })
    @Override
    public ResponseVO<Object> refreshRecommendationResult() {
        List<TaskView> unfinishedTask = candidateTasksProvider.provideUnfinishedTask();

        List<MultiObjectiveRecommendFactor> recommendFactors =
                multiObjectiveRecommendFactorMapper.selectByTaskIds(unfinishedTask
                        .stream()
                        .map(TaskView::getId).collect(Collectors.toList()));

        Map<Integer, List<TaskUser>> taskUserMap = taskUserMapper
                .selectByTaskIds(recommendFactors
                        .stream()
                        .map(MultiObjectiveRecommendFactor::getTaskid)
                        .collect(Collectors.toList()))
                .stream()
                .collect(Collectors.groupingBy(TaskUser::getTaskid));

        //HashSet<Integer> allWorkerIdList = new HashSet<>(userMapper.selectIdByType(Constant.USER_TYPE_WORKER));
        List<Integer> candidateUserIds = userMapper.selectIdByType(Constant.USER_TYPE_WORKER);

        List<TaskRecruitStopRecommendFactor> taskRecruitStopRecommendFactors = new ArrayList<>();
        List<MultiObjectiveRecommendResult> resultList = new ArrayList<>();
        recommendFactors.forEach(recommendFactor -> {
            TaskRecruitStopRecommendFactor taskRecruitStopRecommendFactor = new TaskRecruitStopRecommendFactor();
            taskRecruitStopRecommendFactor.setTaskid(recommendFactor.getTaskid());



            List<Integer> taskUsers = taskUserMap.getOrDefault(recommendFactor.getTaskid(), new ArrayList<>())
                    .stream()
                    .map(TaskUser::getUserid)
                    .collect(Collectors.toList());


            //Set<Integer> candidateUserIds = (Set<Integer>)allWorkerIdList.clone();

            //taskUsers.forEach(candidateUserIds::remove);
            
            
            List<Integer> selectedUsers = multiObjectiveSelection.selectFromCandidateUsersAboutTask(
                    candidateUserIds,
                    recommendFactor.getTaskid(),
                    recommendFactor,
                    taskRecruitStopRecommendFactor,
                    taskUsers);

            taskRecruitStopRecommendFactors.add(taskRecruitStopRecommendFactor);
            resultList.addAll(selectedUsers.stream()
                    .map(uid -> new MultiObjectiveRecommendResult()
                            .setUserid(uid)
                            .setTaskid(recommendFactor.getTaskid()))
                    .collect(Collectors.toList()));
        });
        taskRecruitStopRecommendFactorMapper.deleteAll();
        taskRecruitStopRecommendFactorMapper.insertAll(taskRecruitStopRecommendFactors);
        multiObjectiveRecommendResultMapper.deleteAll();
        multiObjectiveRecommendResultMapper.insertAll(resultList);
        return new ResponseVO<>(1, "刷新成功：" +taskRecruitStopRecommendFactors.toString() + " " + resultList.toString());
    }

    @Override
    @Cacheable(cacheNames = "getAllRecommendedTasksForUser")
    public List<TaskViewVO> getAllRecommendedTasksForUser(Integer userId){
        List<TaskViewVO> result = candidateTasksProvider.provideCandidateTask(userId);
        result = ruleBasedRecommendOrderStrategy.recommend(userId, result);
        result = multiObjectiveBasedRecommendOrderStrategy.recommend(userId, result);
        return  result;
    }
}
