package com.qq.shardingsphere.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qq.shardingsphere.mapper.AddressMapper;
import com.qq.shardingsphere.mapper.CityMapper;
import com.qq.shardingsphere.mapper.StudentMapper;
import com.qq.shardingsphere.model.AddressModel;
import com.qq.shardingsphere.model.CityModel;
import com.qq.shardingsphere.model.StudentModel;
import com.qq.shardingsphere.vo.StudentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Api(tags = {"测试"})
@RequestMapping(value = "/v1")
public class StudentController {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private CityMapper cityMapper;
    @Autowired
    private AddressMapper addressMapper;


    @GetMapping(value = "/insertCity/{city}")
    @ApiOperation("插入city")
    public CityModel insertCity(@ApiParam @PathVariable String city){
        CityModel cityModel = new CityModel();
        cityModel.setCity(city);
        cityMapper.insert(cityModel);
        return cityModel;
    }



    @GetMapping(value = "/insert/{name}/{age}/{city}")
    @ApiOperation("插入学生信息")
    public StudentModel inserst(@ApiParam @PathVariable String  name,@ApiParam @PathVariable Integer  age,@ApiParam @PathVariable String city){

        StudentModel studentModel = new StudentModel();
        studentModel.setStudentAge(age);
        studentModel.setStudentName(name);
        studentMapper.insert(studentModel);


        LambdaQueryWrapper<CityModel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(true,CityModel::getCity,city);
        CityModel cityModel = cityMapper.selectOne(queryWrapper);

        AddressModel addressModel = new AddressModel();
        addressModel.setCity(city);
        addressModel.setCityId(cityModel.getId());
        addressModel.setUserId(studentModel.getId());
        addressMapper.insert(addressModel);


        return studentModel;
    }







    @GetMapping(value = "/getById/{id}")
    @ApiOperation("获取学生信息")
    public List<StudentVO> getById(@ApiParam @PathVariable String id){
        String[] ids = id.split(",");
        Set<Long> set = Arrays.stream(ids).map(Long::parseLong).collect(Collectors.toSet());
        return studentMapper.selectUserById(new ArrayList<>(set));
    }





    @GetMapping(value = "/readWrite/{cityId}")
    @ApiOperation("切换下env rw  读写分离 s0 s1 相同该数据city些许不同，验证查询city")
    public CityModel getCity(@ApiParam @PathVariable Long cityId){
        return cityMapper.selectById(cityId);
    }




    @GetMapping(value = "/insert/{name}/{age}/{city}")
    @ApiOperation("王姓学生强制插入到s0  tbl_student_0 tbl_student_city_0 信息")
    public StudentModel ins(@ApiParam @PathVariable String  name,@ApiParam @PathVariable Integer  age,@ApiParam @PathVariable String city){

        HintManager hintManager = HintManager.getInstance();



        StudentModel studentModel = new StudentModel();
        studentModel.setStudentAge(age);
        studentModel.setStudentName(name);
        studentMapper.insert(studentModel);


        LambdaQueryWrapper<CityModel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(true,CityModel::getCity,city);
        CityModel cityModel = cityMapper.selectOne(queryWrapper);

        AddressModel addressModel = new AddressModel();
        addressModel.setCity(city);
        addressModel.setCityId(cityModel.getId());
        addressModel.setUserId(studentModel.getId());
        addressMapper.insert(addressModel);


        return studentModel;
    }






}
