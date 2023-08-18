package com.seiii.collect.model.vo.task;

import com.seiii.collect.model.po.task.TaskView;
import com.seiii.collect.model.po.task.taskrecruitstop.TaskRecruitStopRecommendFactor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TaskRadarVO")
public class TaskRadarVO {
    @ApiModelProperty("能力")
    private Integer ability;

    @ApiModelProperty("活跃度")
    private Integer activation;

    @ApiModelProperty("相关性")
    private Integer revelance;

    @ApiModelProperty("多样性")
    private Integer diversity;

    @ApiModelProperty("平均目标值")
    private Integer avgTarget;

    public TaskRadarVO(double ability, double activation, double revelance, double diversity, double avgTarget){
        this.ability = toScore(ability);
        this.activation = toScore(activation);
        this.diversity = toScore(diversity);
        this.revelance = toScore(revelance);
        this.avgTarget = toScore(avgTarget);
    }

    private Integer toScore(double val){
        int score = (int) Math.round(Math.abs(val) * 100);

        return score;
    }

}