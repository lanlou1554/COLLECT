package com.seiii.collect.mapper.recommend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(SpringExtension.class)
class MultiObjectiveRecommendFactorMapperTest {

    @Resource
    MultiObjectiveRecommendFactorMapper multiObjectiveRecommendFactorMapper;


    @Test
    void selectByTaskIds() {
        /*System.out.println(multiObjectiveRecommendFactorMapper.selectByTaskIds(Arrays.asList(4, 7, 8, 9, 10)));*/
    }
}