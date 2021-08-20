package com.lyv.security.modules.sysuser.service.impl;

import com.lyv.security.modules.sysuser.bean.User;
import com.lyv.security.modules.sysuser.mapper.UserMapper;
import com.lyv.security.modules.sysuser.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author youzhian
 * @since 2021-08-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
