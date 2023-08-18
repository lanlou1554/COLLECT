package com.seiii.collect.model.po.recommend;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@NoArgsConstructor
public class MultiObjectiveRecommendFactor implements Serializable {
    @ApiModelProperty(value = "", name = "taskid")
    private Integer taskid;

    @ApiModelProperty(value = "", name = "workerAbility")
    private Double workerAbility;

    @ApiModelProperty(value = "", name = "activeness")
    private Double activeness;

    @ApiModelProperty(value = "", name = "workerDiversity")
    private Double workerDiversity;

    @ApiModelProperty(value = "", name = "taskRelevance")
    private Double taskRelevance;

    @ApiModelProperty(value = "", name = "workerCost")
    private Double workerCost;

    private static final long serialVersionUID = 1L;

    /**
     * @return taskId
     */
    public Integer getTaskid() {
        return taskid;
    }

    /**
     * @param taskid
     */
    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    /**
     * @return worker_ability
     */
    public Double getWorkerAbility() {
        return workerAbility;
    }

    /**
     * @param workerAbility
     */
    public void setWorkerAbility(Double workerAbility) {
        this.workerAbility = workerAbility;
    }

    /**
     * @return activeness
     */
    public Double getActiveness() {
        return activeness;
    }

    /**
     * @param activeness
     */
    public void setActiveness(Double activeness) {
        this.activeness = activeness;
    }

    /**
     * @return worker_diversity
     */
    public Double getWorkerDiversity() {
        return workerDiversity;
    }

    /**
     * @param workerDiversity
     */
    public void setWorkerDiversity(Double workerDiversity) {
        this.workerDiversity = workerDiversity;
    }

    /**
     * @return task_relevance
     */
    public Double getTaskRelevance() {
        return taskRelevance;
    }

    /**
     * @param taskRelevance
     */
    public void setTaskRelevance(Double taskRelevance) {
        this.taskRelevance = taskRelevance;
    }

    /**
     * @return worker_cost
     */
    public Double getWorkerCost() {
        return workerCost;
    }

    /**
     * @param workerCost
     */
    public void setWorkerCost(Double workerCost) {
        this.workerCost = workerCost;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", taskid=").append(taskid);
        sb.append(", workerAbility=").append(workerAbility);
        sb.append(", activeness=").append(activeness);
        sb.append(", workerDiversity=").append(workerDiversity);
        sb.append(", taskRelevance=").append(taskRelevance);
        sb.append(", workerCost=").append(workerCost);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}