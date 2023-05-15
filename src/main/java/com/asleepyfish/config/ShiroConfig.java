//package com.asleepyfish.config;
//
//import com.asleepyfish.reaml.MyRealm;
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@Configuration
//public class ShiroConfig {
//    @Bean(name = "myRealm")
//    public MyRealm myRealm(){
//        return new MyRealm();
//    }
//
//    @Bean
//    public DefaultWebSecurityManager securityManager(MyRealm realm) {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setRealm(realm);
//        return securityManager;
//    }
//
//    @Bean
//    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
//        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
//        shiroFilter.setSecurityManager(securityManager);
//        //shiroFilter.setSuccessUrl("/toLogin");
//        //shiroFilter.setLoginUrl("/toLogin");
//        //shiroFilter.setUnauthorizedUrl("/toLogin");
//        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        //filterChainDefinitionMap.put("/login", "anon");
//        //filterChainDefinitionMap.put("/login.html", "anon");
//        //filterChainDefinitionMap.put("/logout", "logout");
//        //filterChainDefinitionMap.put("/static/**", "anon");
//        //filterChainDefinitionMap.put("/**", "anon");
//        filterChainDefinitionMap.put("/**", "anon");
//        //filterChainDefinitionMap.put("/login", "anon");
//        //filterChainDefinitionMap.put("/**", "redirect:/login.html");
//
//        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
//        return shiroFilter;
//    }
//}
