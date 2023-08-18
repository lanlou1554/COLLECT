package com.seiii.collect.mapper.user;

import com.seiii.collect.model.po.user.UserTag;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserTagMapper {
    int deleteByPrimaryKey(@Param("userid") Integer userid, @Param("tag") Integer tag);

    int insert(UserTag record);

    List<UserTag> selectAll();

    List<Integer> selectByUserId(Integer userid);
}