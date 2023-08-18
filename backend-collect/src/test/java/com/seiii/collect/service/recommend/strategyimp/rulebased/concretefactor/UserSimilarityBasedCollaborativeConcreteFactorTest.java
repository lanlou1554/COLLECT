package com.seiii.collect.service.recommend.strategyimp.rulebased.concretefactor;

import com.seiii.collect.mapper.recommend.UserSimilarityMapper;
import com.seiii.collect.mapper.task.TaskUserMapper;
import com.seiii.collect.model.po.recommend.UserVectorComponent;
import com.seiii.collect.model.po.task.TaskUser;
import com.seiii.collect.model.po.user.JaccardUserSimilarity;
import com.seiii.collect.model.vo.task.TaskViewVO;
import com.seiii.collect.service.user.UserService;
import com.seiii.collect.serviceimpl.recommend.strategyimp.rulebased.concretfactor.UserSimilarityBasedCollaborativeConcreteFactor;
import com.seiii.collect.util.Constant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@SpringBootTest
class UserSimilarityBasedCollaborativeConcreteFactorTest {


    @MockBean
    private UserSimilarityMapper userSimilarityMapper;
    @MockBean
    private TaskUserMapper taskUserMapper;

    @Test
    public void calculateWithAllEmptyResult() throws Exception {//表示没有任何用户有标签，也没有任何的推荐的任务被任何用户完成，用户也从未完成过任何任务

        List<Integer> taskIdList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<TaskViewVO> candidateTasks = taskIdList.stream().map(id -> {
            TaskViewVO task = new TaskViewVO();
            task.setId(id);
            return task;
        }).collect(Collectors.toList());

        UserSimilarityBasedCollaborativeConcreteFactor factor =
                new UserSimilarityBasedCollaborativeConcreteFactor(userSimilarityMapper, taskUserMapper);

        factor.setWeight(1);

        Mockito.when(userSimilarityMapper.selectSimilarUserVectorInfo(3, Constant.SIMILAR_USER_LIMIT))
                .thenReturn(Collections.emptyList());

        Mockito.when(userSimilarityMapper.selectJaccardSimilarity(3, Constant.SIMILAR_USER_LIMIT))
                .thenReturn(Collections.emptyList());

        Mockito.when(taskUserMapper.selectByTaskIds(taskIdList))
                .thenReturn(Collections.emptyList());


        List<Double> factorValues = factor.calculateWaitedFactorValues(3, candidateTasks);
        List<Double> expected = Arrays.stream(new double[candidateTasks.size()]).boxed().collect(Collectors.toList());
        Assertions.assertEquals(expected, factorValues);


        Mockito.verify(
                userSimilarityMapper,
                Mockito.times(1))
                .selectJaccardSimilarity(3, Constant.SIMILAR_USER_LIMIT);

        Mockito.verify(
                userSimilarityMapper,
                Mockito.times(1))
                .selectSimilarUserVectorInfo(3, Constant.SIMILAR_USER_LIMIT);

        Mockito.verify(
                taskUserMapper,
                Mockito.times(1))
                .selectByTaskIds(taskIdList);


    }

    @Test
    public void calculateWithEmptyJaccardSimilarityAndEqualFactorVal(){
        List<Integer> taskIdList = Arrays.asList(1, 2);
        List<TaskViewVO> candidateTasks = taskIdList.stream().map(id -> {
            TaskViewVO task = new TaskViewVO();
            task.setId(id);
            return task;
        }).collect(Collectors.toList());
        List<TaskUser> resultTaskUser = Arrays.asList(
                new TaskUser(1, 4),new TaskUser(2, 5)
        );
        List<UserVectorComponent> userVectorComponents = Arrays.asList(
                new UserVectorComponent(3, 1, 1.0),
                new UserVectorComponent(3, 1, 2.0),
                new UserVectorComponent(3, 2, 3.0),
                new UserVectorComponent(3, null, 5.0), //用户3的向量应为（0, 3, 3, 0, ..., 0, 5）

                new UserVectorComponent(4, 1, 1.0),
                new UserVectorComponent(4, null, 2.0),
                new UserVectorComponent(4, 0, 5.0),   //用户4的向量应为（10, 1, 0, 0, ..., 0, 2）
                new UserVectorComponent(4, 0, 5.0),

                new UserVectorComponent(5, 1, 1.0),
                new UserVectorComponent(5, null, 2.0),
                new UserVectorComponent(5, 0, 5.0),   //用户5的向量应为（10, 1, 0, 0, ..., 0, 2）
                new UserVectorComponent(5, 0, 5.0)

        );

        //由于用户4和用户5的向量是相同的，所以他们与目标用户的相似度相同，又因为杰卡距离为0，所以两个任务的因素值应该相同

        UserSimilarityBasedCollaborativeConcreteFactor factor =
                new UserSimilarityBasedCollaborativeConcreteFactor(userSimilarityMapper, taskUserMapper);

        factor.setWeight(1);

        Mockito.when(userSimilarityMapper.selectSimilarUserVectorInfo(3, Constant.SIMILAR_USER_LIMIT))
                .thenReturn(userVectorComponents);

        Mockito.when(userSimilarityMapper.selectJaccardSimilarity(3, Constant.SIMILAR_USER_LIMIT))
                .thenReturn(Collections.emptyList());

        Mockito.when(taskUserMapper.selectByTaskIds(taskIdList))
                .thenReturn(resultTaskUser);

        List<Double> result = factor.calculateWaitedFactorValues(3, candidateTasks);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(result.get(0), result.get(1));

        Mockito.verify(
                userSimilarityMapper,
                Mockito.times(1))
                .selectJaccardSimilarity(3, Constant.SIMILAR_USER_LIMIT);

        Mockito.verify(
                userSimilarityMapper,
                Mockito.times(1))
                .selectSimilarUserVectorInfo(3, Constant.SIMILAR_USER_LIMIT);

        Mockito.verify(
                taskUserMapper,
                Mockito.times(1))
                .selectByTaskIds(taskIdList);

    }

    @Test
    public void calculateWithEmptyJaccardSimilarityAndDifferentFactorVal(){
        List<Integer> taskIdList = Arrays.asList(1, 2);
        List<TaskViewVO> candidateTasks = taskIdList.stream().map(id -> {
            TaskViewVO task = new TaskViewVO();
            task.setId(id);
            return task;
        }).collect(Collectors.toList());
        List<TaskUser> resultTaskUser = Arrays.asList(
                new TaskUser(1, 4), new TaskUser(2, 5)
        );
        List<UserVectorComponent> userVectorComponents = Arrays.asList(
                new UserVectorComponent(3, 1, 1.0),
                new UserVectorComponent(3, 1, 2.0),
                new UserVectorComponent(3, 2, 3.0),
                new UserVectorComponent(3, null, 5.0), //用户3的向量应为（0, 3, 3, 0, ..., 0, 5）

                new UserVectorComponent(4, 1, 1.0),
                new UserVectorComponent(4, null, 2.0),
                new UserVectorComponent(4, 0, 5.0),   //用户4的向量应为（10, 1, 0, 0, ..., 0, 2）
                new UserVectorComponent(4, 0, 5.0),

                new UserVectorComponent(5, 1, 1.0),
                new UserVectorComponent(5, null, 5.0),
                new UserVectorComponent(5, 0, 5.0),   //用户5的向量应为（10, 1, 0, 0, ..., 0, 5）
                new UserVectorComponent(5, 0, 5.0)

        );

        //由于用户4和用户5的向量是相同的，所以他们与目标用户的相似度在最后一个维度有差异，又因为杰卡距离为0，
        // 应该是用户5和用户3更加相似，因为用户5完成了任务2，所以任务2的因素值更高

        UserSimilarityBasedCollaborativeConcreteFactor factor =
                new UserSimilarityBasedCollaborativeConcreteFactor(userSimilarityMapper, taskUserMapper);

        factor.setWeight(1);

        Mockito.when(userSimilarityMapper.selectSimilarUserVectorInfo(3, Constant.SIMILAR_USER_LIMIT))
                .thenReturn(userVectorComponents);

        Mockito.when(userSimilarityMapper.selectJaccardSimilarity(3, Constant.SIMILAR_USER_LIMIT))
                .thenReturn(Collections.emptyList());

        Mockito.when(taskUserMapper.selectByTaskIds(taskIdList))
                .thenReturn(resultTaskUser);

        List<Double> result = factor.calculateWaitedFactorValues(3, candidateTasks);
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.get(0) < result.get(1));

        Mockito.verify(
                userSimilarityMapper,
                Mockito.times(1))
                .selectJaccardSimilarity(3, Constant.SIMILAR_USER_LIMIT);

        Mockito.verify(
                userSimilarityMapper,
                Mockito.times(1))
                .selectSimilarUserVectorInfo(3, Constant.SIMILAR_USER_LIMIT);

        Mockito.verify(
                taskUserMapper,
                Mockito.times(1))
                .selectByTaskIds(taskIdList);

    }

    @Test
    public void calculateWithEmptyVectorComponentsAndDifferentJaccard(){
        List<Integer> taskIdList = Arrays.asList(1, 2);
        List<TaskViewVO> candidateTasks = taskIdList.stream().map(id -> {
            TaskViewVO task = new TaskViewVO();
            task.setId(id);
            return task;
        }).collect(Collectors.toList());
        List<TaskUser> resultTaskUser = Arrays.asList(
                new TaskUser(1, 4), new TaskUser(2, 5)
        );
        List<JaccardUserSimilarity> jaccardUserSimilarities = Arrays.asList(
                new JaccardUserSimilarity(3, 4, 0.7),
                new JaccardUserSimilarity(3, 5, 0.3)
        );


        //用户4和用户3的标签更接近，所以任务1的推荐值更高
        UserSimilarityBasedCollaborativeConcreteFactor factor =
                new UserSimilarityBasedCollaborativeConcreteFactor(userSimilarityMapper, taskUserMapper);

        factor.setWeight(1);

        Mockito.when(userSimilarityMapper.selectSimilarUserVectorInfo(3, Constant.SIMILAR_USER_LIMIT))
                .thenReturn(Collections.emptyList());

        Mockito.when(userSimilarityMapper.selectJaccardSimilarity(3, Constant.SIMILAR_USER_LIMIT))
                .thenReturn(jaccardUserSimilarities);

        Mockito.when(taskUserMapper.selectByTaskIds(taskIdList))
                .thenReturn(resultTaskUser);

        List<Double> result = factor.calculateWaitedFactorValues(3, candidateTasks);
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.get(0) > result.get(1));

        Mockito.verify(
                userSimilarityMapper,
                Mockito.times(1))
                .selectJaccardSimilarity(3, Constant.SIMILAR_USER_LIMIT);

        Mockito.verify(
                userSimilarityMapper,
                Mockito.times(1))
                .selectSimilarUserVectorInfo(3, Constant.SIMILAR_USER_LIMIT);

        Mockito.verify(
                taskUserMapper,
                Mockito.times(1))
                .selectByTaskIds(taskIdList);

    }


}