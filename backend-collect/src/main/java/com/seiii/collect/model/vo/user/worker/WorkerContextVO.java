package com.seiii.collect.model.vo.user.worker;


import com.seiii.collect.model.po.user.User;
import com.seiii.collect.model.po.user.worker.WorkerContext;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "WorkerContextVO")
public class WorkerContextVO {
    @ApiModelProperty("工人id")
    private Integer id;

    @ApiModelProperty("设备类型")
    private String deviceType;

    @ApiModelProperty("os信息")
    private String osInfo;

    @ApiModelProperty("内存大小")
    private String ramSize;

    @ApiModelProperty("网络环境")
    private String netEnv;

    public WorkerContextVO(WorkerContext workerContext) {
        if (workerContext == null) {
            return;
        }
        id = workerContext.getWorkerid();
        deviceType = workerContext.getDevicetype();
        osInfo = workerContext.getOsinfo();
        ramSize=workerContext.getRamsize();
        netEnv = workerContext.getNetenv();
    }
}