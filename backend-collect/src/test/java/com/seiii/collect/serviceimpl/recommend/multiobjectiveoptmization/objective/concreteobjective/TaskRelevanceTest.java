package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective;


import com.seiii.collect.model.po.task.taskrecruitstop.TaskRecruitStopRecommendFactor;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskRelevanceTest {
    @Resource
    ApplicationContext applicationContext;

    @Transactional
    @Rollback
    @Test
    public void taskRelevanceTest1() {
        TaskRelevance taskRelevance = new TaskRelevance(8,applicationContext);
        Assertions.assertTrue(taskRelevance.calculateTaskRelatedValue(Arrays.asList(7,8,9),8)>0.5);
    }


    @Test
    void fillActual() {
        TaskRecruitStopRecommendFactor factor = new TaskRecruitStopRecommendFactor();
        TaskRelevance taskRelevance = new TaskRelevance(8, applicationContext);
        taskRelevance.fillActual(factor, 2.0);
        Assertions.assertEquals(factor.getRelevanceactual(), 2.0);
    }

    @Test
    void fillExpected() {
        TaskRecruitStopRecommendFactor factor = new TaskRecruitStopRecommendFactor();
        TaskRelevance taskRelevance = new TaskRelevance(8, applicationContext);
        taskRelevance.fillExpected(factor, 2.0);
        Assertions.assertEquals(factor.getRelevanceexpected(), 2.0);
    }
}

