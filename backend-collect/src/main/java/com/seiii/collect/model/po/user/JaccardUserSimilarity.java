package com.seiii.collect.model.po.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class JaccardUserSimilarity {
    @ApiModelProperty(value = "", name = "userid1")
    Integer userid1;
    @ApiModelProperty(value = "", name = "userid2")
    Integer userid2;
    @ApiModelProperty(value = "", name = "jaccardsim")
    Double jaccardsim;

}
