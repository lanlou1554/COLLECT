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
public class ReportReviewAbilityTest {

    @Autowired
    ReportReviewAbility reportReviewAbility;

    @Transactional
    @Rollback
    @Test
    void testRatingGap() {
        reportReviewAbility.setAllFactors(3);
        Double ratingGap = reportReviewAbility.getRatingGap();
        assertNotNull(ratingGap);
        System.err.println("ratingGap: " + ratingGap);
    }

    @Transactional
    @Rollback
    @Test
    void testWilsonScore() {
        reportReviewAbility.setAllFactors(3);
        Double wilsonScore = reportReviewAbility.getWilsonScore();
        assertNotNull(wilsonScore);
        System.err.println("wilsonScore: " + wilsonScore);
    }

    @Transactional
    @Rollback
    @Test
    void getAbilityValue() {
        Double abilityValue = reportReviewAbility.calAbilityValue(8);
        assertNotNull(abilityValue);
        System.err.println("Report Review Ability: " + abilityValue);
    }

}
