package com.seiii.collect.model.vo.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seiii.collect.model.po.task.TaskView;
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
@ApiModel(value = "TaskViewVO")
public class TaskViewVO {
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

    @ApiModelProperty("工人数量")
    private Integer workerNum;

    @ApiModelProperty("已选工人数量")
    private Integer pickedWorkerNum;

    @ApiModelProperty("标签组")
    private List<Integer> tagGroups;

    public TaskViewVO(TaskView taskView, Integer pickedWorkerNum) {
        if (taskView == null) return;
        this.id = taskView.getId();
        this.userId = taskView.getUserid();
        this.type = taskView.getType();
        this.title = taskView.getTitle();
        this.description = taskView.getDescription();
        this.startTime = taskView.getStarttime();
        this.endTime = taskView.getEndtime();
        this.workerNum = taskView.getWorkernum();
        this.pickedWorkerNum = pickedWorkerNum;
    }

    public TaskViewVO(TaskView taskView, Integer pickedWorkerNum, List<Integer> tagGroups) {
        if (taskView == null) return;
        this.id = taskView.getId();
        this.userId = taskView.getUserid();
        this.type = taskView.getType();
        this.title = taskView.getTitle();
        this.description = taskView.getDescription();
        this.startTime = taskView.getStarttime();
        this.endTime = taskView.getEndtime();
        this.workerNum = taskView.getWorkernum();
        this.pickedWorkerNum = pickedWorkerNum;
        this.tagGroups = tagGroups;
    }

    public TaskViewVO(TaskView taskView, List<Integer> tagGroups) {
        if (taskView == null) return;
        this.id = taskView.getId();
        this.userId = taskView.getUserid();
        this.type = taskView.getType();
        this.title = taskView.getTitle();
        this.description = taskView.getDescription();
        this.startTime = taskView.getStarttime();
        this.endTime = taskView.getEndtime();
        this.workerNum = taskView.getWorkernum();
        this.tagGroups = tagGroups;
    }

}