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
@ApiModel(value="TBAFlawVO")
public class TBAFlawVO {
    @ApiModelProperty("有待完善的缺陷")
    private FlawVO tbaFlaw;

    @ApiModelProperty("相似缺陷列表")
    private List<SimilarFlawVO> similarFlaws;
}
