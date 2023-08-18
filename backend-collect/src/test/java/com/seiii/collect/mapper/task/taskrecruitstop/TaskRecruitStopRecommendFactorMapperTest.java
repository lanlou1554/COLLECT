package com.seiii.collect.mapper.task.taskrecruitstop;

import com.seiii.collect.model.po.task.taskrecruitstop.TaskRecruitStopRecommendFactor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TaskRecruitStopRecommendFactorMapperTest {
    @Resource
    TaskRecruitStopRecommendFactorMapper mapper;
    @Test
    @Transactional
    @Rollback
    void insertAll() {
        TaskRecruitStopRecommendFactor factor = new TaskRecruitStopRecommendFactor();
        factor.setTaskid(8);
        //mapper.insertAll(Collections.singletonList(factor));
    }
}