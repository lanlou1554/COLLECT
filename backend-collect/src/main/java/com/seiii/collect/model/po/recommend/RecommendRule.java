package com.seiii.collect.model.po.recommend;

import com.seiii.collect.model.dto.recommend.RecommendRuleDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@NoArgsConstructor
public class RecommendRule implements Serializable {
    @ApiModelProperty(value = "", name = "id")
    private Integer id;

    @ApiModelProperty(value = "", name = "name")
    private String name;

    @ApiModelProperty(value = "", name = "isusing")
    private Boolean isusing;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return isUsing
     */
    public Boolean getIsusing() {
        return isusing;
    }

    /**
     * @param isusing
     */
    public void setIsusing(Boolean isusing) {
        this.isusing = isusing;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", isusing=").append(isusing);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public RecommendRule(RecommendRuleDTO recommendRuleDTO){
        this.name=recommendRuleDTO.getName();
        this.isusing=false;
    }
}