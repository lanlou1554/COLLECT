package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkerCostTest {

    @Test
    void calculateValue() {
        List<Integer> solutionUserIds = Arrays.asList(1, 2, 3, 4);
        WorkerCost workerCost = new WorkerCost(8.0);
        Assertions.assertEquals(workerCost.calculateValue(solutionUserIds), 0.5);
    }
}