package com.seiii.collect.model.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seiii.collect.model.vo.recommend.RecommendRuleFactorVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@ApiModel(value="TaskDTO")
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
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

    @ApiModelProperty("可执行文件url")
    private String exeFileName;

    @ApiModelProperty("需求文档url")
    private String reqDocName;

    @ApiModelProperty("标签组")
    private List<Integer> tagGroups;

    @ApiModelProperty("推荐影响因素及权重")
    private List<RecommendRuleFactorVO> recommendFactor;
}