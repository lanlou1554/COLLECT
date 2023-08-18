package com.seiii.collect.model.po.recommend;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVectorComponent {
    @ApiModelProperty(value = "", name = "userid")
    private Integer userid;
    @ApiModelProperty(value = "", name = "tag")
    private Integer tag;
    @ApiModelProperty(value = "", name = "score")
    private Double score;


    public Integer getUserId() {
        return userid;
    }

    public UserVectorComponent setUserId(Integer userId) {
        this.userid = userId;
        return this;
    }

    public Integer getTag() {
        return tag;
    }

    public UserVectorComponent setTag(Integer tag) {
        this.tag = tag;
        return this;
    }

    public Double getScore() {
        return score;
    }

    public UserVectorComponent setScore(Double score) {
        this.score = score;
        return this;
    }

    @Override
    public String toString() {
        return "UserVectorComponent{" +
                "userId=" + userid +
                ", tag=" + tag +
                ", score=" + score +
                '}';
    }
}
