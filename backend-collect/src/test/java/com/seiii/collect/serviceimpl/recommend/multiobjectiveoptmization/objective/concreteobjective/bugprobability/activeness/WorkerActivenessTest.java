package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.activeness;

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
// todo 需完善
public class WorkerActivenessTest {

    @Autowired
    WorkerActiveness workerActiveness;

    @Transactional
    @Rollback
    @Test
    void getActivenessValZeroToOne() {
        Double activenessValZeroToOne = workerActiveness.getActivenessValue(3);
        assertNotNull(activenessValZeroToOne);
        System.err.println("activeness: " + activenessValZeroToOne);
    }

    @Transactional
    @Rollback
    @Test
    void getSubReportNumHalfYear() {
        workerActiveness.setAllFactors(3);
        Integer subReportNumHalfYear = workerActiveness.getSubReportNumHalfYear();
//        assertEquals(2, subReportNumHalfYear);
    }

}
