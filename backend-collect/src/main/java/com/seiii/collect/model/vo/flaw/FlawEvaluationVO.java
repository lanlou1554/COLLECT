package com.seiii.collect.model.vo.flaw;

import com.seiii.collect.model.vo.user.UserViewVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "FlawEvaluationVO")
public class FlawEvaluationVO {
    @ApiModelProperty("评价的id")
    private Integer evaluationId;

    @ApiModelProperty("评价用户的简略信息")
    private UserViewVO user;

    @ApiModelProperty("评价内容")
    private String content;

    @ApiModelProperty("评价的id")
    private FlawEvaLikeVO flawEvaLike;
}
