package com.seiii.collect.model.po.recommend;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@ApiModel
@Data
@NoArgsConstructor
public class MultiObjectiveRecommendResult {
    @ApiModelProperty(value = "", name = "userid")
    private Integer userid;

    @ApiModelProperty(value = "", name = "taskid")
    private Integer taskid;

    public Integer getUserid() {
        return userid;
    }

    public MultiObjectiveRecommendResult setUserid(Integer userid) {
        this.userid = userid;
        return this;
    }

    public Integer getTaskid() {
        return taskid;
    }

    public MultiObjectiveRecommendResult setTaskid(Integer taskid) {
        this.taskid = taskid;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiObjectiveRecommendResult that = (MultiObjectiveRecommendResult) o;
        return Objects.equals(userid, that.userid) && Objects.equals(taskid, that.taskid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, taskid);
    }
}
