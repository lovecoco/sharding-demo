package com.qq.shardingsphere.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentVO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String studentName;
    private Integer studentAge;
    private String city;
}
