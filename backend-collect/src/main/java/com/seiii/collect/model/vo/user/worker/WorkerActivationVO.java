package com.seiii.collect.model.vo.user.worker;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "WorkerActivationVO")
public class WorkerActivationVO {

    @ApiModelProperty("最后一次提交时间")
    private Date lastSubmitTime;

    @ApiModelProperty("近三天提交的报告数")
    private Integer lastThreeDaySubmitNum;

    @ApiModelProperty("近两周提交的报告数")
    private Integer lastTwoWeekSubmitNum;

    @ApiModelProperty("近一个月提交的报告数")
    private Integer lastOneMonthSubmitNum;

    @ApiModelProperty("近半年提交的报告数")
    private Integer lastHalfYearSubmitNum;

    @ApiModelProperty("活跃度评分")
    private Integer score;

}
