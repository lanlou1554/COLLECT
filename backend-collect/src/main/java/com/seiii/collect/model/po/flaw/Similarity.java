package com.seiii.collect.model.po.flaw;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Similarity implements Serializable, Comparable<Similarity> {
    @ApiModelProperty(value = "", name = "flawid1")
    private Integer flawid1;

    @ApiModelProperty(value = "", name = "flawid2")
    private Integer flawid2;

    @ApiModelProperty(value = "", name = "similarity")
    private Double similarity;

    private static final long serialVersionUID = 1L;

    /**
     * @return flawId1
     */
    public Integer getFlawid1() {
        return flawid1;
    }

    /**
     * @param flawid1
     */
    public void setFlawid1(Integer flawid1) {
        this.flawid1 = flawid1;
    }

    /**
     * @return flawId2
     */
    public Integer getFlawid2() {
        return flawid2;
    }

    /**
     * @param flawid2
     */
    public void setFlawid2(Integer flawid2) {
        this.flawid2 = flawid2;
    }

    /**
     * @return similarity
     */
    public Double getSimilarity() {
        return similarity;
    }

    /**
     * @param similarity
     */
    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }

    @Override
    public int compareTo(Similarity s1) {
        return s1.similarity > this.similarity ? 1 : -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", flawid1=").append(flawid1);
        sb.append(", flawid2=").append(flawid2);
        sb.append(", similarity=").append(similarity);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}