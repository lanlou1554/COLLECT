package com.seiii.collect.mapper.flaw;

import com.seiii.collect.mapper.task.TaskMapper;
import com.seiii.collect.model.po.task.TaskView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class FlawPicMapperTest {

    @Resource
    FlawPicMapper flawPicMapper;

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

}