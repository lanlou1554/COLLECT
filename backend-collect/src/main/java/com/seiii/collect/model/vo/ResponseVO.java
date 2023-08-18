package com.seiii.collect.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseVO<T> implements Serializable {
    @ApiModelProperty("标识代码, 1表示成功，0表示出错")
    public int code;

    @ApiModelProperty("提示信息")
    public String msg;

    @ApiModelProperty("返回的数据")
    public T data;

    public ResponseVO(){

    }

    public ResponseVO(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public ResponseVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
