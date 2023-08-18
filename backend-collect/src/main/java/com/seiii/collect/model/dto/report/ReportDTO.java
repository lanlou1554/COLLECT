package com.seiii.collect.model.dto.report;

import com.seiii.collect.model.dto.flaw.FlawDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@ApiModel(value = "ReportDTO")
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    @ApiModelProperty("任务的id，可服务于众包工人直接浏览所有测试报告")
    private Integer taskId;

    @ApiModelProperty("众包工人的id，为了让发包方看到")
    private Integer userId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("缺陷列表")
    private List<FlawDTO> flaws;


}