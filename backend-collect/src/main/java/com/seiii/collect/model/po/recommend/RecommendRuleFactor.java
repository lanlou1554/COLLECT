package com.seiii.collect.model.po.recommend;

import com.seiii.collect.model.vo.recommend.RecommendRuleFactorVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@NoArgsConstructor
public class RecommendRuleFactor implements Serializable {
    @ApiModelProperty(value = "", name = "factorname")
    private String factorname;

    @ApiModelProperty(value = "", name = "ruleid")
    private Integer ruleid;

    @ApiModelProperty(value = "", name = "factorweight")
    private Double factorweight;

    private static final long serialVersionUID = 1L;

    /**
     * @return factorName
     */
    public String getFactorname() {
        return factorname;
    }

    /**
     * @param factorname
     */
    public void setFactorname(String factorname) {
        this.factorname = factorname == null ? null : factorname.trim();
    }

    /**
     * @return ruleId
     */
    public Integer getRuleid() {
        return ruleid;
    }

    /**
     * @param ruleid
     */
    public void setRuleid(Integer ruleid) {
        this.ruleid = ruleid;
    }

    /**
     * @return factorWeight
     */
    public Double getFactorweight() {
        return factorweight;
    }

    /**
     * @param factorweight
     */
    public void setFactorweight(Double factorweight) {
        this.factorweight = factorweight;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", factorname=").append(factorname);
        sb.append(", ruleid=").append(ruleid);
        sb.append(", factorweight=").append(factorweight);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public RecommendRuleFactor(RecommendRuleFactorVO factorVO,Integer ruleid){
        this.factorname=factorVO.getName();
        this.factorweight=factorVO.getWeight();
        this.ruleid=ruleid;
    }
}