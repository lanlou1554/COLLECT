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
class WorkerActivenessObjectiveTest {
    @Transactional
    @Rollback
    @Test
    void calculateValue() {
        WorkerActivenessObjective workerActivenessObjective = new WorkerActivenessObjective();
        MultiObjectiveRecommendFactor factor = new MultiObjectiveRecommendFactor();
        factor.setActiveness(0.2);
        workerActivenessObjective.setWeight(factor);
        workerActivenessObjective.prepareForAllCandidateUsers(Arrays.asList(3, 7, 8, 9));
        double value1 = workerActivenessObjective.calculateValue(Arrays.asList(3));
        double value2 = workerActivenessObjective.calculateValue(Arrays.asList(7));
        double value3 = workerActivenessObjective.calculateValue(Arrays.asList(8));
        double value4 = workerActivenessObjective.calculateValue(Arrays.asList(9));
        assertNotEquals(0, value1);
        assertNotEquals(0, value2);
        assertNotEquals(0, value3);
        assertNotEquals(0, value4);
        double value = workerActivenessObjective.calculateValue(Arrays.asList(3, 7, 8, 9));
        assertTrue(value > 0);
    }

    @Test
    void fillActual() {
        TaskRecruitStopRecommendFactor factor = new TaskRecruitStopRecommendFactor();
        WorkerActivenessObjective workerActivenessObjective = new WorkerActivenessObjective();
        workerActivenessObjective.fillActual(factor, 2.0);
        Assertions.assertEquals(factor.getActivenessactual(), 2.0);
    }

    @Test
    void fillExpected() {
        TaskRecruitStopRecommendFactor factor = new TaskRecruitStopRecommendFactor();
        WorkerActivenessObjective workerActivenessObjective = new WorkerActivenessObjective();
        workerActivenessObjective.fillExpected(factor, 2.0);
        Assertions.assertEquals(factor.getActivenessexpected(), 2.0);
    }
}