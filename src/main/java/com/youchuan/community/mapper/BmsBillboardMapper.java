package com.youchuan.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youchuan.community.model.entity.BmsBillboard;
import org.springframework.stereotype.Repository;

//@Mapper是Mybatis的注解
//@Repository是Spring的注解
@Repository
public interface BmsBillboardMapper extends BaseMapper<BmsBillboard> {
}
