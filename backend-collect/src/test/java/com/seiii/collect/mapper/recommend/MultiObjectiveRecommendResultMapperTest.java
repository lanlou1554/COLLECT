package com.seiii.collect.mapper.recommend;

import com.seiii.collect.mapper.task.TaskMapper;
import com.seiii.collect.mapper.user.UserMapper;
import com.seiii.collect.model.po.recommend.MultiObjectiveRecommendResult;
import com.seiii.collect.model.po.task.Task;
import com.seiii.collect.model.po.user.User;
import com.seiii.collect.util.Constant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(SpringExtension.class)
class MultiObjectiveRecommendResultMapperTest {
    @Resource
    MultiObjectiveRecommendResultMapper multiObjectiveRecommendResultMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    TaskMapper taskMapper;



    @Transactional
    @Rollback
    @Test
    void insertAll() {

        List<MultiObjectiveRecommendResult> resultList = Collections.singletonList(
                new MultiObjectiveRecommendResult().setTaskid(4).setUserid(1)
        );
        multiObjectiveRecommendResultMapper.deleteAll();
        multiObjectiveRecommendResultMapper.insertAll(resultList);
        List<MultiObjectiveRecommendResult> actualResultList = multiObjectiveRecommendResultMapper.selectAll();
        Assertions.assertEquals(resultList, actualResultList);

    }

    @Test
    @Transactional
    @Rollback
    void selectByUserId() {
        List<MultiObjectiveRecommendResult> resultList = Collections.singletonList(
                new MultiObjectiveRecommendResult().setTaskid(4).setUserid(1)
        );
        multiObjectiveRecommendResultMapper.deleteAll();
        multiObjectiveRecommendResultMapper.insertAll(resultList);
        List<MultiObjectiveRecommendResult> actualResultList = multiObjectiveRecommendResultMapper.selectByUserId(1);
        Assertions.assertEquals(resultList, actualResultList);
    }
}