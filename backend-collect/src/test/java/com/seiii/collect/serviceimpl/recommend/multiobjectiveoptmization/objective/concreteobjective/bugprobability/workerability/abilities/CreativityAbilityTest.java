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
public class CreativityAbilityTest {
    @Autowired
    CreativityAbility creativityAbility;

    @Transactional
    @Rollback
    @Test
    void testRootProbability() {
        creativityAbility.setAllFactors(3);
        Double rootProbability = creativityAbility.getRootProbability();
        assertNotNull(rootProbability);
        System.err.println("rootProbability: " + rootProbability);
    }

    @Transactional
    @Rollback
    @Test
    void testForkedFlawNum() {
        creativityAbility.setAllFactors(3);
        Double forkedFlawNum = creativityAbility.getForkedFlawNum();
        assertNotNull(forkedFlawNum);
        System.err.println("forkedFlawNum: " + forkedFlawNum);
    }

    @Transactional
    @Rollback
    @Test
    void teatDuplicateDegree() {
        creativityAbility.setAllFactors(3);
        Double duplicateDegree = creativityAbility.getDuplicateDegree();
        assertNotNull(duplicateDegree);
        System.err.println("duplicateDegree: " + duplicateDegree);
    }

    @Transactional
    @Rollback
    @Test
    void testFlawSimilarity() {
        creativityAbility.setAllFactors(3);
        Double flawSimilarity = creativityAbility.getFlawSimilarity();
        assertNotNull(flawSimilarity);
        System.err.println("flawSimilarity: " + flawSimilarity);
    }

    @Transactional
    @Rollback
    @Test
    void getAbilityValue() {
        Double abilityValue = creativityAbility.calAbilityValue(3);
        assertNotNull(abilityValue);
        System.err.println("Creativity Ability: " + abilityValue);
    }
}
