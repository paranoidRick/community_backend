package com.youchuan.community.controller;

import com.youchuan.community.common.api.ApiResult;
import com.youchuan.community.model.entity.BmsPromotion;
import com.youchuan.community.service.IBmsPromotionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/promotion")
@Api(tags = "BmsPromotionController",description = "推广链接")
public class BmsPromotionController extends BaseController{
    @Resource
    private IBmsPromotionService bmsPromotionService;

    @GetMapping("/list")
    @ApiOperation("获取推广链接")
    public ApiResult getPromotion(){
        List<BmsPromotion> list = bmsPromotionService.list();
        return ApiResult.success(list);
    }
}
