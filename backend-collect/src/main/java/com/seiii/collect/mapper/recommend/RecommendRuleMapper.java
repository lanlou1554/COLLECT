package com.seiii.collect.mapper.recommend;

import com.seiii.collect.model.po.recommend.RecommendRule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecommendRuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RecommendRule record);

    RecommendRule selectByPrimaryKey(Integer id);

    List<RecommendRule> selectAll();

    int updateByPrimaryKey(RecommendRule record);

    RecommendRule selectByName(String name);

    int updateAllOff();

    int updateOneOn(Integer id);
}