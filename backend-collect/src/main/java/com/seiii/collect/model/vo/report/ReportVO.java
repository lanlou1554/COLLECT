package com.seiii.collect.model.vo.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seiii.collect.model.po.report.Report;
import com.seiii.collect.model.vo.flaw.FlawVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ReportVO")
public class ReportVO {
    @ApiModelProperty("报告唯一标识码")
    private Integer id;

    @ApiModelProperty("任务的id，可服务于众包工人直接浏览所有测试报告")
    private Integer taskId;

    @ApiModelProperty("众包工人的id，为了让发包方看到")
    private Integer userId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("创建日期")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("报告评分")
    private Double score;

    @ApiModelProperty("缺陷列表")
    private List<FlawVO> flaws;

    public ReportVO(Report report, List<FlawVO> flaws, Double score) {
        if (report == null) return;
        this.id = report.getId();
        this.taskId = report.getTaskid();
        this.userId = report.getUserid();
        this.title = report.getTitle();
        this.createTime = report.getCreatetime();
        this.score = score;
        this.flaws = flaws;
    }

}