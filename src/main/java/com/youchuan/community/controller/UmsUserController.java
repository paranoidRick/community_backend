package com.youchuan.community.controller;

import cn.hutool.core.util.ObjectUtil;
import com.youchuan.community.common.api.ApiResult;
import com.youchuan.community.model.dto.RegisterDTO;
import com.youchuan.community.model.entity.UmsUser;
import com.youchuan.community.service.IUmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@Api(tags = "UmsUserController",description = "用户管理")
@RequestMapping("/user")
public class UmsUserController extends BaseController{

    @Resource
    private IUmsUserService umsUserService;

    // 用于验证注解是否符合要求，在变量中添加验证信息的要求，当不符合要求时就会在方法中返回message 的错误提示信息。
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public ApiResult<Map<String,Object>> executeRegister(@Valid @RequestBody RegisterDTO dto){
        // 1. 执行注册
        UmsUser user = umsUserService.executeRegister(dto);
        // 2. 判断 如果注册为空
        if(ObjectUtil.isEmpty(user)){
            return ApiResult.failed("账户注册失败");
        }
        // 3. 注册成功 返回
        Map<String,Object> map = new HashMap<>();
        map.put("user",user);
        return ApiResult.success(map);
    }

}
