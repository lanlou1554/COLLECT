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
@ApiModel(value="FlawMapVO")
public class FlawMapVO {
    @ApiModelProperty("所有根节点的flawId")
    private List<Integer> flawIds;

    @ApiModelProperty("矩阵中i行j列的元素代表flawIds中索引为i和j的缺陷的相似度")
    private List<List<Double>> similarityMatrix;
}
