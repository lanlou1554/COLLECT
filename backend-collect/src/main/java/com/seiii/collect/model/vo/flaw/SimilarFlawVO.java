package com.seiii.collect.model.vo.flaw;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SimilarFlawVO")
public class SimilarFlawVO implements Comparable<SimilarFlawVO>, Cloneable {
    @ApiModelProperty("缺陷相似度")
    private double similarity;

    @ApiModelProperty("缺陷")
    private FlawVO flaw;

    @Override
    public int compareTo(SimilarFlawVO sf) {
        return sf.similarity > this.similarity ? 1 : -1;
    }

    @Override
    public SimilarFlawVO clone() {
        SimilarFlawVO clone = null;
        try {
            clone = (SimilarFlawVO) super.clone(); // 浅复制
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
        clone.flaw = (FlawVO) flaw.clone(); // 深复制
        return clone;
    }
}
