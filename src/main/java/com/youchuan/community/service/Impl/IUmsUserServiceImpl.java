package com.youchuan.community.service.Impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youchuan.community.common.api.ApiResult;
import com.youchuan.community.common.exception.ApiAsserts;
import com.youchuan.community.mapper.UmsUserMapper;
import com.youchuan.community.model.dto.RegisterDTO;
import com.youchuan.community.model.entity.UmsUser;
import com.youchuan.community.service.IUmsUserService;
import com.youchuan.community.service.RedisService;
import com.youchuan.community.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

@Service
public class IUmsUserServiceImpl extends ServiceImpl<UmsUserMapper,UmsUser> implements IUmsUserService {

    @Autowired
    private RedisService redisService;
    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;

    @Override
    public UmsUser executeRegister(RegisterDTO dto) {
        // 1. 先查询是否有相同的用户名或相同的邮箱
        LambdaQueryWrapper<UmsUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UmsUser::getUsername,dto.getName()).or().eq(UmsUser::getEmail,dto.getEmail());
        // 2. 根据条件查询语句 查询数据库
        UmsUser umsUser = baseMapper.selectOne(wrapper);
        // 3. 如果user查出来不为空
        if(!ObjectUtil.isEmpty(umsUser))
            ApiAsserts.fail("账号或邮箱已存在");
        // 4. 如果为空 则使用builder方式新建
        UmsUser addUser = UmsUser.builder()
                .username(dto.getName())
                .alias(dto.getName())
                // password 这儿应该使用MD5 加密
                .password(MD5Utils.getPwd(dto.getPass()))
                .email(dto.getEmail())
                .modifyTime(new Date())
                .createTime(new Date())
                .status(true)
                .build();
        // 5. 将新建的addUser 执行到数据库
        baseMapper.insert(addUser);

        // 6. 返回插入的用户
        return addUser;
    }

    /**
     * 生成 验证码
     * @param telephone 电话号码
     * @return
     */
    @Override
    public ApiResult generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        //验证码绑定手机号并存储到redis
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + telephone, sb.toString());
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + telephone, AUTH_CODE_EXPIRE_SECONDS);
        return ApiResult.success(sb.toString(), "获取验证码成功");
    }

    /**
     * 验证 是否正确
     * @param telephone
     * @param authCode
     * @return
     */
    @Override
    public ApiResult verifyAuthCode(String telephone, String authCode) {
        if (StringUtils.isEmpty(authCode)) {
            return ApiResult.failed("请输入验证码");
        }
        String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        boolean result = authCode.equals(realAuthCode);
        if (result) {
            return ApiResult.success(null, "验证码校验成功");
        } else {
            return ApiResult.failed("验证码不正确");
        }
    }
}
