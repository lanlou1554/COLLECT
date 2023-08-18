package com.seiii.collect.model.dto.flaw;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "FlawEvaluationDTO")
@NoArgsConstructor
@AllArgsConstructor
public class FlawEvaluationDTO {
    @ApiModelProperty("评论的用户id")
    private Integer userId;

    @ApiModelProperty("评价内容")
    private String content;

}
