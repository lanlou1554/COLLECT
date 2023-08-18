package com.seiii.collect.model.vo.flaw;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "FlawEvaLikeVO")
public class FlawEvaLikeVO {
    @ApiModelProperty("评价的点赞数")
    private Integer likeNum;

    @ApiModelProperty("评价的点踩数")
    private Integer dislikeNum;

    @ApiModelProperty("评价的状态")
    private Integer status;
}
