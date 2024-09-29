package com.qq.shardingsphere.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@ApiModel(value = "返回值对象")
public class ResponseDTO<T> implements Serializable {
    @ApiModelProperty(allowableValues = "200-500", value = "200成功，非200失败")
    private String code;
    @ApiModelProperty(value = "success和其他提示")
    private String msg;
    @ApiModelProperty(value = "返回的业务数据")
    private T data;
    private Long timeStamp = System.currentTimeMillis();

    public static <T> ResponseDTO<T> success(T data) {
        return new ResponseDTO<>(data);
    }


    public ResponseDTO(T data) {
        this.code = "200";
        this.msg = "success";
        this.data = data;
    }

    public ResponseDTO() {
        this.code = "200";
    }

    public ResponseDTO(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
