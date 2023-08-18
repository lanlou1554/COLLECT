package com.seiii.collect.model.vo.user;

import com.seiii.collect.model.po.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="UserViewVO")
public class UserViewVO {
    @ApiModelProperty("用户唯一标识码")
    private Integer id;

    @ApiModelProperty(value = "用户类型，0对应发包方，1对应众包工人，2对应管理员")
    private Integer type;

    @ApiModelProperty("用户名")
    private String username;

    public UserViewVO(User user){
        id = user.getId();
        type = user.getType();
        username = user.getUsername();
    }

}
