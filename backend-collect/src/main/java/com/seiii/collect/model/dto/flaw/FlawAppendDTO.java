package com.seiii.collect.model.dto.flaw;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@ApiModel(value = "FlawAppendDTO")
@NoArgsConstructor
@AllArgsConstructor
public class FlawAppendDTO {
    @ApiModelProperty("缺陷id")
    private Integer flawId;

    @ApiModelProperty("补充内容")
    private String appendContent;
}
