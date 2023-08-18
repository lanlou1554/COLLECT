package com.seiii.collect.mapper.user;

import com.seiii.collect.model.po.user.UserTag;
import org.apache.ibatis.annotations.Param;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserTagMapperTest {

    @Resource
    UserTagMapper userTagMapper;

    @Transactional
    @Rollback
    @Test
    void deleteByPrimaryKey() {
        userTagMapper.deleteByPrimaryKey(3, 2);
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
        List<UserTag> userTags = userTagMapper.selectAll();
    }

    @Transactional
    @Rollback
    @Test
    void selectByUserId() {
        userTagMapper.selectByUserId(3);
    }

}
