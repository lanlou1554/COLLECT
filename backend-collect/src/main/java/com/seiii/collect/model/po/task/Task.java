package com.seiii.collect.model.po.task;

import com.seiii.collect.model.dto.task.TaskDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@NoArgsConstructor
public class Task implements Serializable {
    @ApiModelProperty(value = "", name = "id")
    private Integer id;

    @ApiModelProperty(value = "", name = "userid")
    private Integer userid;

    @ApiModelProperty(value = "", name = "type")
    private Integer type;

    @ApiModelProperty(value = "", name = "title")
    private String title;

    @ApiModelProperty(value = "", name = "description")
    private String description;

    @ApiModelProperty(value = "", name = "starttime")
    private Date starttime;

    @ApiModelProperty(value = "", name = "endtime")
    private Date endtime;

    @ApiModelProperty(value = "", name = "workernum")
    private Integer workernum;

    @ApiModelProperty(value = "", name = "exefilename")
    private String exefilename;

    @ApiModelProperty(value = "", name = "reqdocname")
    private String reqdocname;

    @ApiModelProperty(value = "", name = "createtime")
    private Date createtime;

    @ApiModelProperty(value = "",name = "recruitstop")
    private boolean recruitstop;

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
     * @return type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
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
     * @return startTime
     */
    public Date getStarttime() {
        return starttime;
    }

    /**
     * @param starttime
     */
    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    /**
     * @return endTime
     */
    public Date getEndtime() {
        return endtime;
    }

    /**
     * @param endtime
     */
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    /**
     * @return workerNum
     */
    public Integer getWorkernum() {
        return workernum;
    }

    /**
     * @param workernum
     */
    public void setWorkernum(Integer workernum) {
        this.workernum = workernum;
    }

    /**
     * @return exeFileName
     */
    public String getExefilename() {
        return exefilename;
    }

    /**
     * @param exefilename
     */
    public void setExefilename(String exefilename) {
        this.exefilename = exefilename == null ? null : exefilename.trim();
    }

    /**
     * @return reqDocName
     */
    public String getReqdocname() {
        return reqdocname;
    }

    /**
     * @param reqdocname
     */
    public void setReqdocname(String reqdocname) {
        this.reqdocname = reqdocname == null ? null : reqdocname.trim();
    }

    /**
     * @return createTime
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * @return recruitStop
     */
    public boolean getRecruitstop(){ return recruitstop;}

    /**
     * @param recruitstop
     */
    public void setRecruitstop(boolean recruitstop){this.recruitstop=recruitstop;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userid=").append(userid);
        sb.append(", type=").append(type);
        sb.append(", title=").append(title);
        sb.append(", description=").append(description);
        sb.append(", starttime=").append(starttime);
        sb.append(", endtime=").append(endtime);
        sb.append(", workernum=").append(workernum);
        sb.append(", exefilename=").append(exefilename);
        sb.append(", reqdocname=").append(reqdocname);
        sb.append(", createtime=").append(createtime);
        sb.append(",recruitstop=").append(recruitstop);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public Task(TaskDTO taskDTO) {
        userid = taskDTO.getUserId();
        type = taskDTO.getType();
        title = taskDTO.getTitle();
        description = taskDTO.getDescription();
        starttime = taskDTO.getStartTime();
        endtime = taskDTO.getEndTime();
        workernum = taskDTO.getWorkerNum();
        exefilename = taskDTO.getExeFileName();
        reqdocname = taskDTO.getReqDocName();
        createtime = new Timestamp(System.currentTimeMillis());
    }
}