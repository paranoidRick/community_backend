package com.youchuan.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youchuan.community.common.api.ApiResult;
import com.youchuan.community.model.entity.BmsBillboard;
import com.youchuan.community.service.IBmsBillboardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/billboard")
@Api(tags = "BmsBillboardController", description = "公告牌显示")
public class BmsBillboardController extends BaseController{
    @Resource
    private IBmsBillboardService bmsBillboardService;

    @GetMapping("/show")
    @ApiOperation("获得公告信息")
    public ApiResult<BmsBillboard> getNotices(){
        List<BmsBillboard> list = bmsBillboardService.list(new
                LambdaQueryWrapper<BmsBillboard>().eq(BmsBillboard::isShow,false));
        return ApiResult.success(list.get(list.size()-1));
    }
}
