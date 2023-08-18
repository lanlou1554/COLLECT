package com.seiii.collect.model.dto.flaw;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@ApiModel(value = "FlawDTO")
@NoArgsConstructor
@AllArgsConstructor
public class FlawDTO {
    @ApiModelProperty("错误截图")
    private List<String> pictureURLs;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("复现步骤描述")
    private String stepDes;

    @ApiModelProperty("设备信息")
    private String deviceInfo;

}
