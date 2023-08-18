package com.seiii.collect.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@ApiModel(value="UserDTO")
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @ApiModelProperty(value = "用户类型，0对应发包方，1对应众包工人，2对应管理员")
    private Integer type;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("标签组")
    private List<Integer> tagGroups;

}
