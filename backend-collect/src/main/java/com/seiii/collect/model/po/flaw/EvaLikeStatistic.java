package com.seiii.collect.model.po.flaw;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@NoArgsConstructor
public class EvaLikeStatistic implements Serializable  {
    @ApiModelProperty(value = "", name = "supportcnt")
    private Integer supportcnt;

    @ApiModelProperty(value = "", name = "opposecnt")
    private Integer opposecnt;

    private static final long serialVersionUID = 1L;

    /**
     * @return supportcnt
     */
    public Integer getSupportcnt() {
        return supportcnt;
    }

    /**
     * @param supportcnt
     */
    public void setSupportcnt(Integer supportcnt) {
        this.supportcnt = supportcnt;
    }

    /**
     * @return opposecnt
     */
    public Integer getOpposecnt() {
        return opposecnt;
    }

    /**
     * @param opposecnt
     */
    public void setUserid(Integer opposecnt) {
        this.opposecnt = opposecnt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", supportcnt=").append(supportcnt);
        sb.append(", opposecnt=").append(opposecnt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
