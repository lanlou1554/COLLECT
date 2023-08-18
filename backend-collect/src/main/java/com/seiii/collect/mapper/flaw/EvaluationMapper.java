package com.seiii.collect.mapper.flaw;

import com.seiii.collect.model.po.flaw.Evaluation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EvaluationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Evaluation record);

    Evaluation selectByPrimaryKey(Integer id);

    List<Evaluation> selectAll();

    int updateByPrimaryKey(Evaluation record);

    List<Evaluation> selectByFlawId(Integer flawid);

    List<Evaluation> selectByUserId(Integer userid);
}