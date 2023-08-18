package com.seiii.collect.model.vo.task;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TaskFlawDetectionPredictionVO")
public class TaskFlawDetectionPredictionVO {
    @ApiModelProperty("任务预测的flaw数")
    private Integer numPredicted;

    @ApiModelProperty("任务已发现缺陷曲线列表")
    private List<TaskFlawDetectionCurveVO> curves;
}
