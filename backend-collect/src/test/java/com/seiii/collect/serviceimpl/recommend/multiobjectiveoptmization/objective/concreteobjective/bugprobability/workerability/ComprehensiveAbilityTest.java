package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.workerability;

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
public class ComprehensiveAbilityTest {

    @Autowired
    ComprehensiveAbility comprehensiveAbility;

    @Transactional
    @Rollback
    @Test
    void testCollabVal(){
        comprehensiveAbility.setAllAbilities(3);
        Double collabVal = comprehensiveAbility.getCollabVal();
        assertNotNull(collabVal);
        System.err.println("collabVal: " + collabVal);
    }

    @Transactional
    @Rollback
    @Test
    void testReviewVal(){
        comprehensiveAbility.setAllAbilities(3);
        Double reviewVal = comprehensiveAbility.getReviewVal();
        assertNotNull(reviewVal);
        System.err.println("reviewVal: " + reviewVal);
    }

    @Transactional
    @Rollback
    @Test
    void testCreatVal(){
        comprehensiveAbility.setAllAbilities(3);
        Double creatVal = comprehensiveAbility.getCreatVal();
        assertNotNull(creatVal);
        System.err.println("creatVal: " + creatVal);
    }

    @Transactional
    @Rollback
    @Test
    void testDetVal(){
        comprehensiveAbility.setAllAbilities(3);
        Double detVal = comprehensiveAbility.getDetVal();
        assertNotNull(detVal);
        System.err.println("detVal: " + detVal);
    }

    @Transactional
    @Rollback
    @Test
    void getAbilityValue(){
        Double abilityValue = comprehensiveAbility.calAbilityValue(3);
        assertNotNull(abilityValue);
        System.err.println("Comprehensive Ability: " + abilityValue);
    }

}
