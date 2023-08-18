package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class FormatUtilTest {

    @Transactional
    @Rollback
    @Test
    void formatAbility() {
        int val1 = FormatUtil.formatAbility(0.34467);
        assertEquals(34, val1);
        int val2 = FormatUtil.formatAbility(0.666666666666666);
        assertEquals(67, val2);
    }

    @Transactional
    @Rollback
    @Test
    void getAbility() {
        List<Integer> ability = FormatUtil.getAbility(3);
        assertNotNull(ability);
        assertNotEquals(0, ability.size());
        assertEquals(6, ability.size());
        System.err.println(ability);
    }

    @Transactional
    @Rollback
    @Test
    void getAvgAbility() {
        List<Integer> avgAbility = FormatUtil.getAvgAbility();
        assertNotNull(avgAbility);
        assertNotEquals(0, avgAbility.size());
        assertEquals(6, avgAbility.size());
        System.err.println(avgAbility);
    }

}
