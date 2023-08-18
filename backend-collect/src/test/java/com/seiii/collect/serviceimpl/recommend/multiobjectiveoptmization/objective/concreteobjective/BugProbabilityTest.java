package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective;


import com.seiii.collect.model.po.recommend.MultiObjectiveRecommendFactor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Deprecated
public class BugProbabilityTest {

    @Transactional
    @Rollback
    @Test
    void testBugProbability() {
        BugProbability bugProbability = new BugProbability();
        MultiObjectiveRecommendFactor factor = new MultiObjectiveRecommendFactor();
        factor.setActiveness(0.2);
        factor.setWorkerAbility(0.8);
        bugProbability.setWeight(factor);
        double value1 = bugProbability.calculateValue(Arrays.asList(3));
        double value2 = bugProbability.calculateValue(Arrays.asList(7));
        double value3 = bugProbability.calculateValue(Arrays.asList(8));
        double value4 = bugProbability.calculateValue(Arrays.asList(9));
        assertNotEquals(0, value1);
        assertNotEquals(0, value2);
        assertNotEquals(0, value3);
        assertNotEquals(0, value4);
//        System.err.println(value1);
//        System.err.println(value2);
//        System.err.println(value3);
//        System.err.println(value4);
        double value = bugProbability.calculateValue(Arrays.asList(3, 7, 8, 9));
        assertTrue(value > 0);
    }
}
