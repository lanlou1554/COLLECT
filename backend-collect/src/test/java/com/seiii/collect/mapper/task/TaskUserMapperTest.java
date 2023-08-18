package com.seiii.collect.mapper.task;

import com.seiii.collect.model.po.task.TaskUser;
import com.seiii.collect.model.po.task.TaskView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TaskUserMapperTest {

    @Resource
    TaskUserMapper taskUserMapper;

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
    void selectAll() {
    }

    @Transactional
    @Rollback
    @Test
    void selectByUserId() {
    }

    @Transactional
    @Rollback
    @Test
    void selectByTaskId() {
    }

    @Transactional
    @Rollback
    @Test
    void selectByTaskIds() {
        List<TaskUser> result = taskUserMapper.selectByTaskIds(Arrays.asList(1, 3, 6));
        System.out.println(result);
    }

}