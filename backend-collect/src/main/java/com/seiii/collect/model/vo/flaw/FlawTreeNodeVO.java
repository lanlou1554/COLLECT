package com.seiii.collect.model.vo.flaw;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="FlawTreeNodeVO")
public class FlawTreeNodeVO {
    @ApiModelProperty("缺陷树子节点")
    private List<FlawTreeNodeVO> children;

    @ApiModelProperty("当前缺陷")
    private FlawVO current;
}
