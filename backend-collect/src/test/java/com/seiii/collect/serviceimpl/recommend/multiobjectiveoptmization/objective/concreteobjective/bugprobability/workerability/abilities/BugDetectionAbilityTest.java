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
public class BugDetectionAbilityTest {
    @Autowired
    BugDetectionAbility bugDetectionAbility;

    @Transactional
    @Rollback
    @Test
    void testPartiTaskNum() {
        bugDetectionAbility.setAllFactors(3);
        Double partiTaskNum = bugDetectionAbility.getPartiTaskNum();
        assertNotNull(partiTaskNum);
        System.err.println("partiTaskNum: " + partiTaskNum);
    }

    @Transactional
    @Rollback
    @Test
    void testSubReportNum() {
        bugDetectionAbility.setAllFactors(3);
        Double subReportNum = bugDetectionAbility.getSubReportNum();
        assertNotNull(subReportNum);
        System.err.println("subReportNum: " + subReportNum);
    }

    @Transactional
    @Rollback
    @Test
    void testSubFlawNum() {
        bugDetectionAbility.setAllFactors(3);
        Double subFlawNum = bugDetectionAbility.getSubFlawNum();
        assertNotNull(subFlawNum);
        System.err.println("subFlawNum: " + subFlawNum);
    }

    @Transactional
    @Rollback
    @Test
    void testDetectFlawRate() {
        bugDetectionAbility.setAllFactors(3);
        Double detectFlawRate = bugDetectionAbility.getDetectFlawRate();
        assertNotNull(detectFlawRate);
        System.err.println("detectFlawRate: " + detectFlawRate);
    }

    @Transactional
    @Rollback
    @Test
    void getAbilityValue() {
        Double abilityValue = bugDetectionAbility.calAbilityValue(3);
        assertNotNull(abilityValue);
        System.err.println("Bug Detection Ability: " + abilityValue);
    }
}
