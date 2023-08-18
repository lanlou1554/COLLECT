package com.seiii.collect.model.dto.recommend;

import com.seiii.collect.model.vo.flaw.FlawVO;
import com.seiii.collect.model.vo.recommend.RecommendRuleFactorVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@ApiModel(value="RecommendRuleDTO")
@NoArgsConstructor
@AllArgsConstructor
public class RecommendRuleDTO {
    @ApiModelProperty("推荐规则名称")
    private String name;

    @ApiModelProperty("推荐影响因素列表")
    private List<RecommendRuleFactorVO> factors;
}
