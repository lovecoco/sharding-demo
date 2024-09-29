package com.qq.shardingsphere.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qq.shardingsphere.model.StudentModel;
import com.qq.shardingsphere.vo.StudentVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface StudentMapper extends BaseMapper<StudentModel> {


    List<StudentVO> selectUserById(@Param("userIds") List<Long> userIds);
}
