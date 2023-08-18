package com.seiii.collect.mapper.task;

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
class TaskMapperTest {

    @Resource
    TaskMapper taskMapper;

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
    void selectByIds() {
        List<Integer> ids = new LinkedList<>();
        ids.add(3);
        ids.add(5);
        List<TaskView> taskViews = taskMapper.selectByIds(ids);
    }

    @Transactional
    @Rollback
    @Test
    void selectAllByPageId() {
    }

    @Transactional
    @Rollback
    @Test
    void selectByUserIdAndAfterEndTime() {
    }

    @Transactional
    @Rollback
    @Test
    void selectByUserIdAndBeforeEndTime() {
    }

    @Transactional
    @Rollback
    @Test
    void selectBeforeEndTimeAndAfterStartTime() {
    }

}