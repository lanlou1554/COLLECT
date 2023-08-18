package com.seiii.collect.service.report;

import com.seiii.collect.model.dto.report.ReportDTO;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.flaw.TBAFlawVO;
import com.seiii.collect.model.vo.report.ReportVO;

import java.util.List;

public interface ReportService {
    // 查看报告详细信息
    ResponseVO<ReportVO> viewReportDetails(Integer reportId);

    // 通过请求体提交报告
    ResponseVO<ReportVO> createReport(ReportDTO reportDTO);

    // 获取报告中待修改的缺陷列表
    ResponseVO<List<TBAFlawVO>> getToBeRefinedFlawLists(Integer reportId);
}
