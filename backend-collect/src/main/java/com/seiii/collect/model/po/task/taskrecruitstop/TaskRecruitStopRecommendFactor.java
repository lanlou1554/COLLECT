package com.seiii.collect.model.po.task.taskrecruitstop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@NoArgsConstructor
public class TaskRecruitStopRecommendFactor implements Serializable {
    @ApiModelProperty(value = "", name = "taskid")
    private Integer taskid;

    @ApiModelProperty(value = "", name = "abilityactual")
    private Double abilityactual;

    @ApiModelProperty(value = "", name = "abilityexpected")
    private Double abilityexpected;

    @ApiModelProperty(value = "", name = "activenessactual")
    private Double activenessactual;

    @ApiModelProperty(value = "", name = "activenessexpected")
    private Double activenessexpected;

    @ApiModelProperty(value = "", name = "relevanceactual")
    private Double relevanceactual;

    @ApiModelProperty(value = "", name = "relevanceexpected")
    private Double relevanceexpected;

    @ApiModelProperty(value = "", name = "diversityactual")
    private Double diversityactual;

    @ApiModelProperty(value = "", name = "diversityexpected")
    private Double diversityexpected;

    @ApiModelProperty(value = "", name = "avgtargetactual")
    private Double avgtargetactual;

    @ApiModelProperty(value = "", name = "avgtargetexpected")
    private Double avgtargetexpected;

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
     * @return abilityActual
     */
    public Double getAbilityactual() {
        return abilityactual;
    }

    /**
     * @param abilityactual
     */
    public void setAbilityactual(Double abilityactual) {
        this.abilityactual = abilityactual;
    }

    /**
     * @return abilityExpected
     */
    public Double getAbilityexpected() {
        return abilityexpected;
    }

    /**
     * @param abilityexpected
     */
    public void setAbilityexpected(Double abilityexpected) {
        this.abilityexpected = abilityexpected;
    }

    /**
     * @return activenessActual
     */
    public Double getActivenessactual() {
        return activenessactual;
    }

    /**
     * @param activenessactual
     */
    public void setActivenessactual(Double activenessactual) {
        this.activenessactual = activenessactual;
    }

    /**
     * @return activenessExpected
     */
    public Double getActivenessexpected() {
        return activenessexpected;
    }

    /**
     * @param activenessexpected
     */
    public void setActivenessexpected(Double activenessexpected) {
        this.activenessexpected = activenessexpected;
    }

    /**
     * @return relevanceActual
     */
    public Double getRelevanceactual() {
        return relevanceactual;
    }

    /**
     * @param relevanceactual
     */
    public void setRelevanceactual(Double relevanceactual) {
        this.relevanceactual = relevanceactual;
    }

    /**
     * @return relevanceExpected
     */
    public Double getRelevanceexpected() {
        return relevanceexpected;
    }

    /**
     * @param relevanceexpected
     */
    public void setRelevanceexpected(Double relevanceexpected) {
        this.relevanceexpected = relevanceexpected;
    }

    /**
     * @return diversityActual
     */
    public Double getDiversityactual() {
        return diversityactual;
    }

    /**
     * @param diversityactual
     */
    public void setDiversityactual(Double diversityactual) {
        this.diversityactual = diversityactual;
    }

    /**
     * @return diversityExpected
     */
    public Double getDiversityexpected() {
        return diversityexpected;
    }

    /**
     * @param diversityexpected
     */
    public void setDiversityexpected(Double diversityexpected) {
        this.diversityexpected = diversityexpected;
    }

    /**
     * @return avgTargetActual
     */
    public Double getAvgtargetactual() {
        return avgtargetactual;
    }

    /**
     * @param avgtargetactual
     */
    public void setAvgtargetactual(Double avgtargetactual) {
        this.avgtargetactual = avgtargetactual;
    }

    /**
     * @return avgTargetExpected
     */
    public Double getAvgtargetexpected() {
        return avgtargetexpected;
    }

    /**
     * @param avgtargetexpected
     */
    public void setAvgtargetexpected(Double avgtargetexpected) {
        this.avgtargetexpected = avgtargetexpected;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", taskid=").append(taskid);
        sb.append(", abilityactual=").append(abilityactual);
        sb.append(", abilityexpected=").append(abilityexpected);
        sb.append(", activenessactual=").append(activenessactual);
        sb.append(", activenessexpected=").append(activenessexpected);
        sb.append(", relevanceactual=").append(relevanceactual);
        sb.append(", relevanceexpected=").append(relevanceexpected);
        sb.append(", diversityactual=").append(diversityactual);
        sb.append(", diversityexpected=").append(diversityexpected);
        sb.append(", avgtargetactual=").append(avgtargetactual);
        sb.append(", avgtargetexpected=").append(avgtargetexpected);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}