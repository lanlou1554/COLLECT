package com.seiii.collect.mapper.flaw;

import com.seiii.collect.model.po.flaw.EvaLike;
import java.util.List;

import com.seiii.collect.model.po.flaw.EvaLikeStatistic;
import org.apache.ibatis.annotations.Param;

public interface EvaLikeMapper {
    int deleteByPrimaryKey(@Param("evaid") Integer evaid, @Param("userid") Integer userid);

    int insert(EvaLike record);

    EvaLike selectByPrimaryKey(@Param("evaid") Integer evaid, @Param("userid") Integer userid);

    List<EvaLike> selectAll();

    int updateByPrimaryKey(EvaLike record);

    EvaLikeStatistic selectCountByEva(@Param("evaid") Integer evaid);
}