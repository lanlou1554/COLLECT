package com.seiii.collect.model.vo.report;

import com.seiii.collect.model.po.report.ReportView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ReportViewVO")
public class ReportViewVO {
    @ApiModelProperty("报告唯一标识码")
    private Integer id;

    @ApiModelProperty("任务的id，可服务于众包工人直接浏览所有测试报告")
    private Integer taskId;

    @ApiModelProperty("众包工人的id，为了让发包方看到")
    private Integer userId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("报告评分")
    private Double score;

    @ApiModelProperty("缺陷个数")
    private Integer flawNum;

    public ReportViewVO(ReportView reportView, Double score, Integer flawNum) {
        if (reportView == null) return;
        this.id = reportView.getId();
        this.taskId = reportView.getTaskid();
        this.userId = reportView.getUserid();
        this.title = reportView.getTitle();
        this.score = score;
        this.flawNum = flawNum;
    }

}