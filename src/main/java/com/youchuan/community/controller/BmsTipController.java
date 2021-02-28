package com.youchuan.community.controller;

import com.youchuan.community.common.api.ApiResult;
import com.youchuan.community.model.entity.BmsTip;
import com.youchuan.community.service.IBmsTipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/tip")
@Api(tags = "BmsTipController",description = "每日一句")
public class BmsTipController {

    @Resource
    private IBmsTipService bmsTipService;

    @GetMapping("/today")
    @ApiOperation("随机获得每日一句")
    public ApiResult<BmsTip> getRandomTip(){
        BmsTip tip = bmsTipService.getRandomTip();
        return ApiResult.success(tip);
    }

}
