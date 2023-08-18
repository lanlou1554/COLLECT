package com.seiii.collect.controller.report;

import com.seiii.collect.model.dto.report.ReportDTO;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.flaw.TBAFlawVO;
import com.seiii.collect.model.vo.report.ReportVO;
import com.seiii.collect.service.report.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "ReportController")
@RestController
@RequestMapping("/report")
public class ReportController {
    @Resource
    private ReportService reportService;

    @ApiOperation("查看报告详细信息")
    @GetMapping("/detail/{reportId}")
    public ResponseVO<ReportVO> viewReportDetails(@PathVariable Integer reportId) {
        return reportService.viewReportDetails(reportId);
    }

    @ApiOperation("通过请求体提交报告")
    @PostMapping("/issue")
    public ResponseVO<ReportVO> createReport(@RequestBody ReportDTO reportDTO) {
        return reportService.createReport(reportDTO);
    }

    @ApiOperation("获取报告中待修改的缺陷列表")
    @GetMapping("/toBeRefined")
    public ResponseVO<List<TBAFlawVO>> getToBeRefinedFlawLists(@RequestParam Integer reportId){
        return reportService.getToBeRefinedFlawLists(reportId);
    }

}
