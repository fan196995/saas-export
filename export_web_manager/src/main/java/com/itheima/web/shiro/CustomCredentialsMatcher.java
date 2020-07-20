package com.itheima.web.shiro;

import com.itheima.common.utils.Encrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @author fanbo
 * @date 2020/7/7 20:05
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    /**
     * 密码比较的方法
     * @param token  //upToken
     * @param info   //身份认证的info，安全数据（User），用户的数据库密码
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //转化token，取用户输入的密码
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String userPassword = String.valueOf(upToken.getPassword());
//        userPassword= "123456";
        //盐
        String email = upToken.getUsername();

        //加密
        String md5Password = Encrypt.md5(userPassword, email);

        //通过AuthenticationInfo取数据库密码
        String dbPassword = String.valueOf(info.getCredentials()) ;

        //比较两个密码
        return md5Password.equals(dbPassword);
    }
}
