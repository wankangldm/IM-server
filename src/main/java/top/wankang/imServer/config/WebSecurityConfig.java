/*
package top.wankang.imServer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    // 指定密码的加密方式
    @SuppressWarnings("deprecation")
    @Bean
    PasswordEncoder passwordEncoder(){
        // 不对密码进行加密
        return NoOpPasswordEncoder.getInstance();
    }

    // 配置用户及其对应的角色
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("123").roles("ADMIN","USER")
                .and()
                .withUser("hangge").password("123").roles("USER");
    }

    // 配置 URL 访问权限
    @Override
    protected  void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // 开启 HttpSecurity 配置
                .anyRequest().authenticated() // 用户访问所有地址都必须登录认证后访问
                .and().formLogin().permitAll(); // 开启表单登录
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //设置不拦截规则
        web.ignoring().antMatchers("/resources/static/**");
    }
}*/
