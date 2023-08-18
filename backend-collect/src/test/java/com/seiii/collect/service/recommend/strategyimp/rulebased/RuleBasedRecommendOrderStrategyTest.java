package com.seiii.collect.service.recommend.strategyimp.rulebased;

import com.seiii.collect.model.vo.task.TaskVO;
import com.seiii.collect.model.vo.task.TaskViewVO;
import com.seiii.collect.serviceimpl.recommend.strategyimp.rulebased.RuleBasedRecommendOrderStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(SpringExtension.class)
class RuleBasedRecommendOrderStrategyTest {
    @Autowired
    RuleBasedRecommendOrderStrategy strategy;

    @Test
    void recommendEmpty() {
        List<TaskViewVO> result = strategy.recommend(3, new ArrayList<>());
        Assertions.assertTrue(result.isEmpty());
    }




}