package com.seiii.collect.mapper.user;

import com.seiii.collect.model.po.user.User;
import com.seiii.collect.util.Constant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserMapperTest {

    @Resource
    UserMapper userMapper;

    @Transactional
    @Rollback
    @Test
    void deleteByPrimaryKey() {
        userMapper.deleteByPrimaryKey(2);
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
        userMapper.selectByPrimaryKey(3);
    }

    @Transactional
    @Rollback
    @Test
    void selectAll() {
        List<User> users = userMapper.selectAll();
    }

    @Transactional
    @Rollback
    @Test
    void updateByPrimaryKey() {
    }

    @Transactional
    @Rollback
    @Test
    void selectByUsername() {
        userMapper.selectByUsername("worker");
    }

    @Transactional
    @Rollback
    @Test
    void selectIdByType() {
        userMapper.selectIdByType(Constant.USER_TYPE_WORKER);
    }
}