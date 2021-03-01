package com.youchuan.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youchuan.community.common.api.ApiResult;
import com.youchuan.community.model.dto.RegisterDTO;
import com.youchuan.community.model.entity.UmsUser;

public interface IUmsUserService extends IService<UmsUser> {

    /**
     * 注册
     * @param dto
     * @return 注册对象
     */
    UmsUser executeRegister(RegisterDTO dto);

    /**
     * 生成验证码
     */
    ApiResult generateAuthCode(String telephone);

    /**
     * 判断验证码和手机号码是否匹配
     */
    ApiResult verifyAuthCode(String telephone, String authCode);
}
