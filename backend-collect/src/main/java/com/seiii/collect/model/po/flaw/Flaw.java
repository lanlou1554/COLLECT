package com.seiii.collect.model.po.flaw;

import com.seiii.collect.model.dto.flaw.FlawDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@NoArgsConstructor
public class Flaw implements Serializable {
    @ApiModelProperty(value = "", name = "id")
    private Integer id;

    @ApiModelProperty(value = "", name = "reportid")
    private Integer reportid;

    @ApiModelProperty(value = "", name = "description")
    private String description;

    @ApiModelProperty(value = "", name = "stepdes")
    private String stepdes;

    @ApiModelProperty(value = "", name = "deviceinfo")
    private String deviceinfo;

    @ApiModelProperty(value = "", name = "score")
    private Double score;

    @ApiModelProperty(value = "", name = "state")
    private boolean state;

    @ApiModelProperty(value = "", name = "path")
    private String path;

    @ApiModelProperty(value = "", name = "scorenum")
    private Integer scorenum;

    @ApiModelProperty(value = "", name = "taskid")
    private Integer taskid;

    @ApiModelProperty(value = "", name = "appendcontent")
    private String appendcontent;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return reportId
     */
    public Integer getReportid() {
        return reportid;
    }

    /**
     * @param reportid
     */
    public void setReportid(Integer reportid) {
        this.reportid = reportid;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * @return stepDes
     */
    public String getStepdes() {
        return stepdes;
    }

    /**
     * @param stepdes
     */
    public void setStepdes(String stepdes) {
        this.stepdes = stepdes == null ? null : stepdes.trim();
    }

    /**
     * @return deviceInfo
     */
    public String getDeviceinfo() {
        return deviceinfo;
    }

    /**
     * @param deviceinfo
     */
    public void setDeviceinfo(String deviceinfo) {
        this.deviceinfo = deviceinfo == null ? null : deviceinfo.trim();
    }

    /**
     * @return score
     */
    public Double getScore() {
        return score;
    }

    /**
     * @param score
     */
    public void setScore(Double score) {
        this.score = score;
    }

    /**
     * @return state
     */
    public boolean getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * @return path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * @return scorenum
     */
    public Integer getScorenum() {
        return scorenum;
    }

    /**
     * @param scorenum
     */
    public void setScorenum(Integer scorenum) {
        this.scorenum = scorenum;
    }

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
     * @return appendcontent
     */
    public String getAppendcontent() {
        return appendcontent;
    }

    /**
     * @param appendcontent
     */
    public void setAppendcontent(String appendcontent) {
        this.appendcontent = appendcontent == null ? null : appendcontent.trim();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", reportid=").append(reportid);
        sb.append(", description=").append(description);
        sb.append(", stepdes=").append(stepdes);
        sb.append(", deviceinfo=").append(deviceinfo);
        sb.append(", score=").append(score);
        sb.append(", state=").append(state);
        sb.append(", path=").append(path);
        sb.append(", scorenum=").append(scorenum);
        sb.append(", taskid=").append(taskid);
        sb.append(", appendcontent=").append(appendcontent);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    // todo: path 和 state 需要后续在三种情况下分别都更新
    public Flaw(FlawDTO flawDTO, Integer reportid, Integer taskid) {
        this.reportid = reportid;
        this.taskid = taskid;
        description = flawDTO.getDescription();
        deviceinfo = flawDTO.getDeviceInfo();
        stepdes = flawDTO.getStepDes();
        score = -1.0;
        scorenum = 0;
        state = false;
        path = "";
        appendcontent = "";
    }
}