package com.seiii.collect.mapper.recommend;

import com.seiii.collect.model.po.recommend.UserVectorComponent;
import com.seiii.collect.model.po.user.JaccardUserSimilarity;
import org.apache.ibatis.annotations.Param;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserSimilarityMapper {

    List<UserVectorComponent> selectSimilarUserVectorInfo(@Param("userid") Integer userId, @Param("limit") Integer limit);

    List<JaccardUserSimilarity> selectJaccardSimilarity(@Param("userid") Integer userId, @Param("limit") Integer limit);
}
