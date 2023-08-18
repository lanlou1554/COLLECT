package com.seiii.collect.mapper.flaw;

import com.seiii.collect.model.po.flaw.Score;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ScoreMapper {
    int deleteByPrimaryKey(@Param("userid") Integer userid, @Param("flawid") Integer flawid);

    int insert(Score record);

    Score selectByPrimaryKey(@Param("userid") Integer userid, @Param("flawid") Integer flawid);

    List<Score> selectAll();

    int updateByPrimaryKey(Score record);

    List<Score> selectByUserId(Integer userid);
}