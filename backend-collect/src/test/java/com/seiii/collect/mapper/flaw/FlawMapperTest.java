package com.seiii.collect.mapper.flaw;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class FlawMapperTest {

    @Resource
    FlawMapper flawMapper;

    @Transactional
    @Rollback
    @Test
    void deleteByPrimaryKey() {
    }

    @Transactional
    @Rollback
    @Test
    void insert() {
    }

    @Transactional
    @Rollback
    @Test
    void selectByPrimaryKey() {
    }

    @Transactional
    @Rollback
    @Test
    void selectAll() {
    }

    @Transactional
    @Rollback
    @Test
    void selectByReportId() {
    }

}