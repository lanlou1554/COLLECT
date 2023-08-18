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
public class WorkerCloudVO {

    @ApiModelProperty("关键字")
    private String name;

    @ApiModelProperty("权重")
    private Integer value;
}

