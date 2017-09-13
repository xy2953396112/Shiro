package com.xzh.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

import junit.framework.Assert;

public class AuthenticatorTest {
  
    private void login(String configFile) {  
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager  
        Factory<org.apache.shiro.mgt.SecurityManager> factory =  
                new IniSecurityManagerFactory(configFile);  
      
        //2、得到SecurityManager实例 并绑定给SecurityUtils  
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();  
        SecurityUtils.setSecurityManager(securityManager);  
      
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）  
        Subject subject = SecurityUtils.getSubject();  
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");  
      
        subject.login(token);  
    }  
    
    //FirstSuccessfulStrategy：只要有一个Realm验证成功即可，只返回第一个Realm身份验证成功的认证信息，其他的忽略；

    //AtLeastOneSuccessfulStrategy：只要有一个Realm验证成功即可，和FirstSuccessfulStrategy不同，返回所有Realm身份验证成功的认证信息；

    //AllSuccessfulStrategy：所有Realm验证成功才算成功，且返回所有Realm身份验证成功的认证信息，如果有一个失败就失败了。
    
    @Test  
    public void testAllSuccessfulStrategyWithSuccess() {  
    	//调用login方法
        login("classpath:shiro-authenticator-all-success.ini");  
        Subject subject = SecurityUtils.getSubject();  
      
        //得到一个身份集合，其包含了Realm验证成功的身份信息  
        PrincipalCollection principalCollection = subject.getPrincipals();  
        Assert.assertEquals(2, principalCollection.asList().size());  
    }   
    
    
}
