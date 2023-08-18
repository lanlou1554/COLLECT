package com.seiii.collect.model.vo.user;

import com.seiii.collect.model.po.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@ApiModel(value = "UserVO")
public class UserVO {
    @ApiModelProperty("用户唯一标识码")
    private Integer id;

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

    public UserVO(User user) {
        if (user == null) {
            return;
        }
        id = user.getId();
        type = user.getType();
        username = user.getUsername();
        password = user.getPassword();
        email = user.getEmail();
        phone = user.getPhone();
    }

    public UserVO(User user, List<Integer> tagGroups) {
        if (user == null) {
            return;
        }
        id = user.getId();
        type = user.getType();
        username = user.getUsername();
        password = user.getPassword();
        email = user.getEmail();
        phone = user.getPhone();
        this.tagGroups = tagGroups;
    }
}
