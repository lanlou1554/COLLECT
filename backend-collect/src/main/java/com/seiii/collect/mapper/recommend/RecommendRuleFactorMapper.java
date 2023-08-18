package com.seiii.collect.mapper.recommend;

import com.seiii.collect.model.po.recommend.RecommendRuleFactor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecommendRuleFactorMapper {
    int deleteByPrimaryKey(String factorname);

    int insert(RecommendRuleFactor record);

    RecommendRuleFactor selectByPrimaryKey(String factorname);

    List<RecommendRuleFactor> selectAll();

    int updateByPrimaryKey(RecommendRuleFactor record);

    List<RecommendRuleFactor> selectByRuleId(int ruleId);

    int deleteByRuleId(int ruleId);

    List<RecommendRuleFactor> selectByUsingRule();
}