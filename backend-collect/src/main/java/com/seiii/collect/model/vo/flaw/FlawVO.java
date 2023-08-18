package com.seiii.collect.model.vo.flaw;

import com.seiii.collect.model.po.flaw.Flaw;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="FlawVO")
public class FlawVO implements Cloneable{
    @ApiModelProperty("缺陷唯一标识码")
    private Integer id;

    @ApiModelProperty("报告id")
    private Integer reportId;

    @ApiModelProperty("错误截图")
    private List<String> pictureURLs;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("复现步骤描述")
    private String stepDes;

    @ApiModelProperty("设备信息")
    private String deviceInfo;

    @ApiModelProperty("缺陷平均分")
    private Double score;

    @ApiModelProperty("评分人数")
    private Integer scoreNum;

    @ApiModelProperty("缺陷是否已被处理")
    private boolean state;

    @ApiModelProperty("对已提交的报告的补充内容")
    private String appendcontent;

    public FlawVO(Flaw flaw, List<String> pictureURLs) {
        if (flaw == null) {
            return;
        }
        this.id = flaw.getId();
        this.reportId = flaw.getReportid();
        this.description = flaw.getDescription();
        this.stepDes = flaw.getStepdes();
        this.deviceInfo = flaw.getDeviceinfo();
        this.pictureURLs = pictureURLs;
        this.score = flaw.getScore();
        this.state = flaw.getState();
        this.scoreNum = flaw.getScorenum();
        this.appendcontent = flaw.getAppendcontent();
    }

    @Override
    public FlawVO clone() {
        FlawVO clone = null;
        try {
            clone = (FlawVO) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
        return clone;
    }
}
