package com.seiii.collect.mapper.recommend;

import com.seiii.collect.model.po.recommend.MultiObjectiveRecommendFactor;
import java.util.List;

public interface MultiObjectiveRecommendFactorMapper {
    int deleteByPrimaryKey(Integer taskid);

    int insert(MultiObjectiveRecommendFactor record);

    MultiObjectiveRecommendFactor selectByPrimaryKey(Integer taskid);

    List<MultiObjectiveRecommendFactor> selectByTaskIds(List<Integer> ids);

    List<MultiObjectiveRecommendFactor> selectAll();

    int updateByPrimaryKey(MultiObjectiveRecommendFactor record);
}