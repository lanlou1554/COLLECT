package com.seiii.collect.mapper.user;

import com.seiii.collect.model.po.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    List<Integer> selectIdByType(Integer type);

    int updateByPrimaryKey(User record);

    User selectByUsername(String username);
}