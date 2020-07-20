package com.itheima.web.shiro;

import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;
import com.itheima.service.system.ModuleService;
import com.itheima.service.system.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author fanbo
 * @date 2020/7/7 20:05
 */
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private ModuleService moduleService;

    /**
     * 授权
     * @param principalCollection  安全数据集合
     * @return  告诉shiro这个用户有哪些权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取安全数据
        User user = (User) principalCollection.getPrimaryPrincipal();

        //通过user对象拿到菜单
        List<Module> moduleList = moduleService.findModuleByUser(user);

        //构造set集合，加入菜单信息
        Set<String> set = new HashSet<String>();

        for (Module module : moduleList) {
            set.add(module.getName());
        }
        // protected Set<String> stringPermissions;
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(set);
        return info;
    }

    /**
     * 身份认证
     * @param authenticationToken
     * @return  告诉shiro哪些用户可以登录
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //authenticationToken转换为upToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;

        //通过upToken取用户名，email
        String email = upToken.getUsername();

        //查找用户实体类，放入安全数据
        User user = userService.findByEmail(email);

        // Object principal 安全数据，User
        // Object credentials 用户的数据库密码
        // String realmName 可以随意取名，但是我们一般用类名
        AuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
        return info;
    }


}
