package com.seiii.collect.model.po.flaw;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@NoArgsConstructor
public class Score implements Serializable {
    @ApiModelProperty(value = "", name = "userid")
    private Integer userid;

    @ApiModelProperty(value = "", name = "flawid")
    private Integer flawid;

    @ApiModelProperty(value = "", name = "socre")
    private Integer socre;

    private static final long serialVersionUID = 1L;

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
     * @return flawId
     */
    public Integer getFlawid() {
        return flawid;
    }

    /**
     * @param flawid
     */
    public void setFlawid(Integer flawid) {
        this.flawid = flawid;
    }

    /**
     * @return socre
     */
    public Integer getSocre() {
        return socre;
    }

    /**
     * @param socre
     */
    public void setSocre(Integer socre) {
        this.socre = socre;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userid=").append(userid);
        sb.append(", flawid=").append(flawid);
        sb.append(", socre=").append(socre);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}