package com.seiii.collect.model.vo.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TaskFlawDetectionCurveVO")
public class TaskFlawDetectionCurveVO {
    @ApiModelProperty("当前时间（以天为单位）")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate time;

    @ApiModelProperty("截止当天发现缺陷总数")
    private Integer num;
}
