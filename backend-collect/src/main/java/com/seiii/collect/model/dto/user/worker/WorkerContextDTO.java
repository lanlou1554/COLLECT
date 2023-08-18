package com.seiii.collect.model.dto.user.worker;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="WorkerContextDTO")
@NoArgsConstructor
@AllArgsConstructor
public class WorkerContextDTO {
    @ApiModelProperty("设备类型")
    private String deviceType;

    @ApiModelProperty("os信息")
    private String osInfo;

    @ApiModelProperty("内存大小")
    private String ramSize;

    @ApiModelProperty("网络环境")
    private String netEnv;
}
