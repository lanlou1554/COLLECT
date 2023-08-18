package com.seiii.collect.mapper.flaw;

import com.seiii.collect.model.po.flaw.Flaw;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FlawMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Flaw record);

    Flaw selectByPrimaryKey(Integer id);

    List<Flaw> selectAll();

    int updateByPrimaryKey(Flaw record);

    List<Flaw> selectByReportId(Integer reportid);
    //取得同一任务下所有缺陷
    List<Flaw> selectByTaskId(Integer taskid);
}