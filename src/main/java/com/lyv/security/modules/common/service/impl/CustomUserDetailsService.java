package com.lyv.security.modules.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyv.security.modules.sysrole.bean.SysRole;
import com.lyv.security.modules.sysrole.bean.SysUserRole;
import com.lyv.security.modules.sysrole.service.ISysRoleService;
import com.lyv.security.modules.sysrole.service.ISysUserRoleService;
import com.lyv.security.modules.sysuser.bean.User;
import com.lyv.security.modules.sysuser.service.IUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author youzhian
 */
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Resource
    private IUserService userService;
    @Resource
    private ISysUserRoleService sysUserRoleService;
    @Resource
    private ISysRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        User user = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getLoginName,username));
        if(user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }

        List<SysUserRole> userRoles = sysUserRoleService.list(new QueryWrapper<SysUserRole>().lambda().eq(SysUserRole::getUserId,user.getId()));
        if(userRoles != null){
            userRoles.forEach(ur->{
                SysRole role = roleService.getById(ur.getRoleId());
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
        }
        return new org.springframework.security.core.userdetails.User(user.getName(),user.getPassword(),authorities);
    }
}
