package com.jqh.SpringSecurityDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserService myUserService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll() //项目主路径可以直接访问  http://127.0.0.1:8080/
                .anyRequest().authenticated() //  其他所有链接都需要认证 http://127.0.0.1:8080/hello
                .and()
                .logout().permitAll() //  允许注销可以直接访问退出登录 http://127.0.0.1:8080/logout
                .and()
                .formLogin();  //  允许表单登录
        http.csrf().disable() ; // 关闭scrf认证
    }



    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置允许那些资源可以不认证
        web.ignoring().antMatchers("/js/**","/css/**","/images/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 第一种,在缓存中写死,写入一个用户以及角色
        auth.inMemoryAuthentication().withUser("admin")
                .password("123456")
                .roles("ADMIN");

        auth.inMemoryAuthentication().withUser("user")
                .password("111111")
                .roles("USER");

        // 第二中,自定义的方式,可以从数据库读取
        // 可以自定义用户管理方式,比如数据库,文件等等
       // auth.userDetailsService(myUserService)
       //         .passwordEncoder(new MyPassordEncoder());// 定义密码方式
        // 第三种,使用jdbc方式,但表结构一定要保持系统一样
       // auth.jdbcAuthentication().usersByUsernameQuery("").authoritiesByUsernameQuery("").passwordEncoder(new MyPassordEncoder());
    }
}
