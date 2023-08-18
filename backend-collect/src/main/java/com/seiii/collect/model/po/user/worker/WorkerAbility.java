package com.seiii.collect.model.po.user.worker;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerAbility implements Serializable {
    @ApiModelProperty(value = "", name = "userid")
    private Integer userid;

    @ApiModelProperty(value = "", name = "collabval")
    private Double collabval;

    @ApiModelProperty(value = "", name = "reviewval")
    private Double reviewval;

    @ApiModelProperty(value = "", name = "creatval")
    private Double creatval;

    @ApiModelProperty(value = "", name = "detval")
    private Double detval;

    @ApiModelProperty(value = "", name = "expval")
    private Double expval;

    @ApiModelProperty(value = "", name = "comprehensiveval")
    private Double comprehensiveval;

    private static final long serialVersionUID = 1L;

    /**
     * @return userId
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * @return collabVal
     */
    public Double getCollabval() {
        return collabval;
    }

    /**
     * @param collabval
     */
    public void setCollabval(Double collabval) {
        this.collabval = collabval;
    }

    /**
     * @return reviewVal
     */
    public Double getReviewval() {
        return reviewval;
    }

    /**
     * @param reviewval
     */
    public void setReviewval(Double reviewval) {
        this.reviewval = reviewval;
    }

    /**
     * @return creatVal
     */
    public Double getCreatval() {
        return creatval;
    }

    /**
     * @param creatval
     */
    public void setCreatval(Double creatval) {
        this.creatval = creatval;
    }

    /**
     * @return detVal
     */
    public Double getDetval() {
        return detval;
    }

    /**
     * @param detval
     */
    public void setDetval(Double detval) {
        this.detval = detval;
    }

    /**
     * @return expVal
     */
    public Double getExpval() {
        return expval;
    }

    /**
     * @param expval
     */
    public void setExpval(Double expval) {
        this.expval = expval;
    }

    /**
     * @return comprehensiveVal
     */
    public Double getComprehensiveval() {
        return comprehensiveval;
    }

    /**
     * @param comprehensiveval
     */
    public void setComprehensiveval(Double comprehensiveval) {
        this.comprehensiveval = comprehensiveval;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userid=").append(userid);
        sb.append(", collabval=").append(collabval);
        sb.append(", reviewval=").append(reviewval);
        sb.append(", creatval=").append(creatval);
        sb.append(", detval=").append(detval);
        sb.append(", expval=").append(expval);
        sb.append(", comprehensiveval=").append(comprehensiveval);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public WorkerAbility(Integer userId, List<Double> ability) {
        this.userid = userId;
        this.collabval = ability.get(0);
        this.reviewval = ability.get(1);
        this.creatval = ability.get(2);
        this.detval = ability.get(3);
        this.expval = ability.get(4);
        this.comprehensiveval = ability.get(5);
    }

}