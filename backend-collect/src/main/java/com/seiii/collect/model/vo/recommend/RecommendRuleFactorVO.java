package com.seiii.collect.model.vo.recommend;


import com.seiii.collect.model.po.recommend.RecommendRuleFactor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="RecommendRuleFactorVO")
public class RecommendRuleFactorVO {
    @ApiModelProperty("推荐影响因素名称")
    private String name;

    @ApiModelProperty("推荐影响因素权重")
    private Double weight;

    public RecommendRuleFactorVO(RecommendRuleFactor recommendRuleFactor){
        this.name=recommendRuleFactor.getFactorname();
        this.weight=recommendRuleFactor.getFactorweight();
    }
}
