package com.seiii.collect.mapper.report;

import com.seiii.collect.model.po.report.Report;
import com.seiii.collect.model.po.report.ReportView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Report record);

    Report selectByPrimaryKey(Integer id);

    List<Report> selectAll();

    int updateByPrimaryKey(Report record);

    List<ReportView> selectByUserIdAndPage(@Param("userid") Integer userid,  @Param("start") Integer start, @Param("pagesize") Integer pagesize);

    List<ReportView> selectByUserId(Integer userid);

    List<ReportView> selectByTaskId(Integer taskid);

    List<Report> selectReportsByTaskId(Integer taskid);
}