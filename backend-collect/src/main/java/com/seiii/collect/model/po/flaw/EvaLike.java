package com.seiii.collect.model.po.flaw;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@NoArgsConstructor
public class EvaLike implements Serializable {
    @ApiModelProperty(value = "", name = "evaid")
    private Integer evaid;

    @ApiModelProperty(value = "", name = "userid")
    private Integer userid;

    @ApiModelProperty(value = "", name = "support")
    private Integer support;

    @ApiModelProperty(value = "", name = "oppose")
    private Integer oppose;

    private static final long serialVersionUID = 1L;

    /**
     * @return evaId
     */
    public Integer getEvaid() {
        return evaid;
    }

    /**
     * @param evaid
     */
    public void setEvaid(Integer evaid) {
        this.evaid = evaid;
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
     * @return support
     */
    public Integer getSupport() {
        return support;
    }

    /**
     * @param support
     */
    public void setSupport(Integer support) {
        this.support = support;
    }

    /**
     * @return oppose
     */
    public Integer getOppose() {
        return oppose;
    }

    /**
     * @param oppose
     */
    public void setOppose(Integer oppose) {
        this.oppose = oppose;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", evaid=").append(evaid);
        sb.append(", userid=").append(userid);
        sb.append(", support=").append(support);
        sb.append(", oppose=").append(oppose);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}