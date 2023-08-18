package com.seiii.collect.model.dto.flaw;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "FlawScoreDTO")
@NoArgsConstructor
@AllArgsConstructor
public class FlawScoreDTO {
    @ApiModelProperty("评分用户id")
    private Integer userId;

    @ApiModelProperty("缺陷id")
    private Integer flawId;

    @ApiModelProperty("评分")
    private Integer score;
}
