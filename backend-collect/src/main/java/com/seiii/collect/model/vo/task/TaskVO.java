package com.seiii.collect.model.vo.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seiii.collect.model.po.task.Task;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@ApiModel(value = "TaskVO")
public class TaskVO {
    @ApiModelProperty("任务唯一标识码")
    private Integer id;

    @ApiModelProperty("发包方的id")
    private Integer userId;

    @ApiModelProperty(value = "0代表功能测试，1代表性能测试")
    private Integer type;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("开始日期")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty("结束日期")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty("创建日期")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("工人数量")
    private Integer workerNum;

    @ApiModelProperty("已选工人数量")
    private Integer pickedWorkerNum;

    @ApiModelProperty("可执行文件url")
    private String exeFileName;

    @ApiModelProperty("需求文档url")
    private String reqDocName;

    @ApiModelProperty(value = "用来描述任务的状态。0表示未领取或者请求该任务详情的用户不是众包工人，1表示进行中，2表示已完成")
    private Integer state;

    @ApiModelProperty("标签组")
    private List<Integer> tagGroups;

    public TaskVO(Task task, Integer state, Integer pickedWorkerNum) {
        if (task == null) return;
        this.id = task.getId();
        this.userId = task.getUserid();
        this.type = task.getType();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.startTime = task.getStarttime();
        this.endTime = task.getEndtime();
        this.createTime = task.getCreatetime();
        this.workerNum = task.getWorkernum();
        this.exeFileName = task.getExefilename();
        this.reqDocName = task.getReqdocname();
        this.state = state;
        this.pickedWorkerNum = pickedWorkerNum;
    }

    public TaskVO(Task task, Integer state, Integer pickedWorkerNum, List<Integer> tagGroups) {
        if (task == null) return;
        this.id = task.getId();
        this.userId = task.getUserid();
        this.type = task.getType();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.startTime = task.getStarttime();
        this.endTime = task.getEndtime();
        this.createTime = task.getCreatetime();
        this.workerNum = task.getWorkernum();
        this.exeFileName = task.getExefilename();
        this.reqDocName = task.getReqdocname();
        this.state = state;
        this.pickedWorkerNum = pickedWorkerNum;
        this.tagGroups = tagGroups;
    }

    public TaskVO(Task task, List<Integer> tagGroups){
        if (task == null) return;
        this.id = task.getId();
        this.userId = task.getUserid();
        this.type = task.getType();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.startTime = task.getStarttime();
        this.endTime = task.getEndtime();
        this.createTime = task.getCreatetime();
        this.workerNum = task.getWorkernum();
        this.exeFileName = task.getExefilename();
        this.reqDocName = task.getReqdocname();
        this.tagGroups = tagGroups;
    }

}
