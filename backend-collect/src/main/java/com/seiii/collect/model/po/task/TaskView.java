package com.seiii.collect.model.po.task;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "TaskView")
@Data
@NoArgsConstructor
public class TaskView implements Serializable {
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

    private static final long serialVersionUID = 1L;

}