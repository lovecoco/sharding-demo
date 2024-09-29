package com.qq.shardingsphere.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tbl_student_city")
@ApiModel
public class AddressModel {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String city;
    private Long cityId;
    private Long userId;

}
