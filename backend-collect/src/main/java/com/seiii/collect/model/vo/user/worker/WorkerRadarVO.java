package com.seiii.collect.model.vo.user.worker;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@ApiModel(value = "WorkerRadarVO")
public class WorkerRadarVO {

    @ApiModelProperty("报告协作能力")
    private Integer cooperateAbility;

    @ApiModelProperty("报告审查能力")
    private Integer reviewAbility;

    @ApiModelProperty("创新能力")
    private Integer creativeAbility;

    @ApiModelProperty("寻找bug的能力")
    private Integer findBugAbility;

    @ApiModelProperty("语言表达能力")
    private Integer languageAbility;

    @ApiModelProperty("综合能力")
    private Integer avgAbility;

    public WorkerRadarVO(List<Integer> abilities) {
        this.cooperateAbility = abilities.get(0);
        this.reviewAbility = abilities.get(1);
        this.creativeAbility = abilities.get(2);
        this.findBugAbility = abilities.get(3);
        this.languageAbility = abilities.get(4);
        this.avgAbility = abilities.get(5);
    }

}
