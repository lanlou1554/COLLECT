package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.workerability.abilities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CollaborationAbilityTest {

    @Autowired
    CollaborationAbility collaborationAbility;

    @Transactional
    @Rollback
    @Test
    void testForkingFlawRate() {
        collaborationAbility.setAllFactors(3);
        Double forkingFlawRate = collaborationAbility.getForkingFlawRate();
        assertNotNull(forkingFlawRate);
        System.err.println("forkingFlawRate: " + forkingFlawRate);
    }

    @Transactional
    @Rollback
    @Test
    void testForkRatingGap() {
        collaborationAbility.setAllFactors(3);
        Double forkRatingGap = collaborationAbility.getForkRatingGap();
        assertNotNull(forkRatingGap);
        System.err.println("forkRatingGap: " + forkRatingGap);
    }

    @Transactional
    @Rollback
    @Test
    void getAbilityValue() {
        Double abilityValue = collaborationAbility.calAbilityValue(3);
        assertNotNull(abilityValue);
        System.err.println("Collaboration Ability: " + abilityValue);
    }

}
