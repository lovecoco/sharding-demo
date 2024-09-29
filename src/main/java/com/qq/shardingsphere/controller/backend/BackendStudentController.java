package com.qq.shardingsphere.controller.backend;

import com.qq.shardingsphere.mapper.AddressMapper;
import com.qq.shardingsphere.mapper.CityMapper;
import com.qq.shardingsphere.mapper.StudentMapper;
import com.qq.shardingsphere.model.CityModel;
import com.qq.shardingsphere.model.StudentModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"测试"})
@RequestMapping(value = "/backend/v1")
public class BackendStudentController {
    @Autowired
    private StudentMapper studentMapper;

//    @Value("${auto.k}")
//    private String kk;


    @GetMapping(value = "/getById/{id}")
    @ApiOperation("获取学生信息")
    public StudentModel getById(@ApiParam @PathVariable Integer id){
        StudentModel studentModel = studentMapper.selectById(id);
        return studentModel;
    }
    @GetMapping(value = "/success")
    @ApiOperation("获取success字符串")
    public String success(){
        return "success 1111";
    }
    @GetMapping(value = "/exception/{num}")
    @ApiOperation("异常处理测试")
    public int exception(@ApiParam @PathVariable Integer num){
        return 10/num;
    }
    @GetMapping(value = "/objNullTest")
    @ApiOperation("空值测试")
    public StudentModel nullTest(){
        return null;
    }

    @GetMapping(value = "/stringNullTest")
    @ApiOperation("空值测试")
    public String emptyTest(){
        return null;
    }

    @GetMapping(value = "/voidTest")
    @ApiOperation("空值测试")
    public void voidTest(){

    }

//    @GetMapping(value = "/autoBuildTest")
//    @ApiOperation("热部署测试")
//    public String autoBuild(){
//        return kk;
//    }

}
