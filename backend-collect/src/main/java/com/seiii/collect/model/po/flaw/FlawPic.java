package com.seiii.collect.model.po.flaw;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@NoArgsConstructor
public class FlawPic implements Serializable {
    @ApiModelProperty(value = "", name = "flawid")
    private Integer flawid;

    @ApiModelProperty(value = "", name = "pictureurl")
    private String pictureurl;

    private static final long serialVersionUID = 1L;

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
     * @return pictureURL
     */
    public String getPictureurl() {
        return pictureurl;
    }

    /**
     * @param pictureurl
     */
    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl == null ? null : pictureurl.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", flawid=").append(flawid);
        sb.append(", pictureurl=").append(pictureurl);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public FlawPic(Integer flawid, String pictureurl){
        this.flawid = flawid;
        this.pictureurl = pictureurl;
    }
}