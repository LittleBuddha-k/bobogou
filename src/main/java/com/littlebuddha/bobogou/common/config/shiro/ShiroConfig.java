package com.littlebuddha.bobogou.common.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.littlebuddha.bobogou.common.config.shiro.realms.CustomerRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * shiro配置文件
 */
@Configuration
public class ShiroConfig {

    //创建ShiroFilter----负责拦截所有请求
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        Map<String, String> map = new HashMap<>();
        //配置系统公共资源
        //map.put("/data/goods/list", "anon");
        //map.put("/data/goods/data", "anon");
        //map.put("/modules/data/**", "anon");
        //map.put("/utils/**", "anon");
        map.put("/index.html", "anon");
        map.put("/system/loginPage", "anon");
        map.put("/system/login", "anon");
        map.put("/system/registerPage", "anon");
        map.put("/system/register", "anon");
        map.put("/system/logout", "anon");
        map.put("/bootstrap/**", "anon");
        map.put("/plugins/**", "anon");
        map.put("/pictures/**", "anon");
        //配置系统受限资源
        map.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        //默认认证界面路径
        shiroFilterFactoryBean.setLoginUrl("/system/loginPage");
        return shiroFilterFactoryBean;
    }

    //创建SecurityManager
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("getRealm") Realm realm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        //注入记住我管理器
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        return defaultWebSecurityManager;
    }

    //创建Realm
    @Bean
    public Realm getRealm() {
        CustomerRealm customerRealm = new CustomerRealm();
        //修改凭证匹配器-----密码匹配原则
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //设置加密算法
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        //设置散列次数
        hashedCredentialsMatcher.setHashIterations(1024);
        customerRealm.setCredentialsMatcher(hashedCredentialsMatcher);

        //开启数据缓存
        customerRealm.setCacheManager(new EhCacheManager());
        customerRealm.setCachingEnabled(true);
        customerRealm.setAuthenticationCachingEnabled(true);//开启认证缓存
        customerRealm.setAuthenticationCacheName("authenticationCache");
        customerRealm.setAuthorizationCachingEnabled(true);//开启授权缓存
        customerRealm.setAuthorizationCacheName("authorizationCache");
        return customerRealm;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * 2   * cookie对象;
     * 3   * rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。
     * 4   * @return
     * 5
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        //System.out.println("ShiroConfiguration.rememberMeCookie()");
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");

        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    /**
     * cookie管理对象;
     * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
     *
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        //System.out.println("ShiroConfiguration.rememberMeManager()");
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    /**
     * FormAuthenticationFilter 过滤器 过滤记住我
     * @return
     */
    @Bean
    public FormAuthenticationFilter formAuthenticationFilter(){
        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
        //对应前端的checkbox的name = rememberMe
        formAuthenticationFilter.setRememberMeParam("rememberMe");
        return formAuthenticationFilter;
    }
}
