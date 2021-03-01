package com.youchuan.community.controller;

import com.youchuan.community.common.api.ApiResult;
import com.youchuan.community.service.IUmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
@Controller
@Api(tags = "UmsTelephoneController",description = "手机号验证")
@RequestMapping("/phone")
public class UmsTelephoneController {

    @Resource
    private IUmsUserService umsUserService;

    @ApiOperation("获取验证码")
    @GetMapping("/getAuthCode")
    @ResponseBody
    public ApiResult getAuthCode(@RequestParam String telephone) {
        return umsUserService.generateAuthCode(telephone);
    }

    @ApiOperation("判断验证码是否正确")
    @PostMapping("/verifyAuthCode")
    @ResponseBody
    public ApiResult updatePassword(@RequestParam String telephone,
                                    @RequestParam String authCode) {
        return umsUserService.verifyAuthCode(telephone,authCode);
    }
}
