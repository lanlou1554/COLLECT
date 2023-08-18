package com.seiii.collect.mapper.recommend;

import com.seiii.collect.model.po.recommend.MultiObjectiveRecommendResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MultiObjectiveRecommendResultMapper {
    void deleteAll();
    void insertAll(List<MultiObjectiveRecommendResult> resultList);
    List<MultiObjectiveRecommendResult> selectByUserId(Integer userId);
    List<MultiObjectiveRecommendResult> selectAll();
}
