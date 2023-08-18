package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective;

import com.seiii.collect.model.po.recommend.MultiObjectiveRecommendFactor;
import com.seiii.collect.model.po.task.taskrecruitstop.TaskRecruitStopRecommendFactor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class WorkerAbilityObjectiveTest {
    @Transactional
    @Rollback
    @Test
    void calculateValue() {
        WorkerAbilityObjective workerAbilityObjective = new WorkerAbilityObjective();
        MultiObjectiveRecommendFactor factor = new MultiObjectiveRecommendFactor();
        factor.setWorkerAbility(0.8);
        workerAbilityObjective.setWeight(factor);
        workerAbilityObjective.prepareForAllCandidateUsers(Arrays.asList(3, 7, 8, 9));
        double value1 = workerAbilityObjective.calculateValue(Arrays.asList(3));
        double value2 = workerAbilityObjective.calculateValue(Arrays.asList(7));
        double value3 = workerAbilityObjective.calculateValue(Arrays.asList(8));
        double value4 = workerAbilityObjective.calculateValue(Arrays.asList(9));
        assertNotEquals(0, value1);
        assertNotEquals(0, value2);
        assertNotEquals(0, value3);
        assertNotEquals(0, value4);
        double value = workerAbilityObjective.calculateValue(Arrays.asList(3, 7, 8, 9));
        assertTrue(value > 0);
    }

    @Test
    void fillActual() {
        TaskRecruitStopRecommendFactor factor = new TaskRecruitStopRecommendFactor();
        WorkerAbilityObjective workerAbilityObjective = new WorkerAbilityObjective();
        workerAbilityObjective.fillActual(factor, 2.0);
        Assertions.assertEquals(factor.getAbilityactual(), 2.0);
    }

    @Test
    void fillExpected() {
        TaskRecruitStopRecommendFactor factor = new TaskRecruitStopRecommendFactor();
        WorkerAbilityObjective workerAbilityObjective = new WorkerAbilityObjective();
        workerAbilityObjective.fillExpected(factor, 2.0);
        Assertions.assertEquals(factor.getAbilityexpected(), 2.0);
    }
}