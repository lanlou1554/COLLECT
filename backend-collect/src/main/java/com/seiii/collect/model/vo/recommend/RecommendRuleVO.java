package com.seiii.collect.model.vo.recommend;

import com.seiii.collect.model.po.recommend.RecommendRule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="RecommendRuleVO")
public class RecommendRuleVO {
    @ApiModelProperty("推荐规则唯一标志码")
    private Integer id;

    @ApiModelProperty("推荐规则名字")
    private String name;

    @ApiModelProperty("推荐规则是否启用")
    private boolean using;

    @ApiModelProperty("推荐规则影响因子")
    private List<RecommendRuleFactorVO> factors;

    public RecommendRuleVO(RecommendRule recommendRule,List<RecommendRuleFactorVO> factors){
        if(recommendRule==null) return;
        this.id=recommendRule.getId();
        this.name=recommendRule.getName();
        this.using =recommendRule.getIsusing();
        this.factors=factors;

    }
}
