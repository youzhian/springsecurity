package com.lyv.security.modules.sysuser.mapper;

import com.lyv.security.modules.sysuser.bean.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author youzhian
 * @since 2021-08-19
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
