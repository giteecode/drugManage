package com.drug.config.secure;

import com.drug.config.filter.MyWebFilter;
import com.drug.entity.user.User;
import com.drug.entity.user.UserLoginLog;
import com.drug.service.user.UserLoginLogService;
import com.drug.service.user.UserService;
import com.drug.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@Configuration//配置文件注解
@EnableWebSecurity//禁用Boot的默认Security配置，配合@Configuration启用自定义配置（需要扩展WebSecurityConfigurerAdapter）
@EnableGlobalMethodSecurity(prePostEnabled = true)//启用Security注解，例如最常用的@PreAuthorize 本项目用的 RBAC此注解暂时不用
@Order(2)
@Component
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    protected MyUserDetailsService myDetailService;
	@Autowired
    protected UserService userService;
	@Autowired
    protected UserLoginLogService userLoginLogService;

	/**
	 * 身份验证配置，用于注入自定义身份验证Bean和密码校验规则
	 */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(myDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * Request层面的配置，对应XML Configuration中的<http>元素
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 获取当前用户ID
        http
//        		.httpBasic().authenticationEntryPoint(new UnauthorizedEntryPoint()) //定义出错后的异常信息
//		.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS).permitAll()    //所有的预检查默认通过
		        .antMatchers("/user/get/*/").permitAll()                 //登陆后根据当前session获取用户信息默认通过
                .antMatchers("/user/info/*").permitAll()                 //登陆后根据当前session获取用户信息默认通过
		        .antMatchers("/**/charts/").permitAll()                  //图表
		        .antMatchers("/swagger-ui.html","/webjars/**","/swagger**/**","/null**/**","/v2/**","/csrf/**").permitAll() //swagger
		        .antMatchers("/**").access("@rbacService.hasPermission(request,authentication)") //其余任何方法需要RBAC过滤URL
	            .anyRequest().authenticated()   //其余需要权限验证（因为RBAC过滤全局，其实此句已无意义）
        .and()
//        		.anonymous().disable() //禁止匿名用户
	            .headers().frameOptions().disable() //预防iframe跨域问题
        .and()
                .formLogin().loginPage("http://localhost:8889/login")//设置的登陆页面 默认 /login
        .and()
                .cors()
        .and()
        		.csrf().disable()                                          //禁用CSRF验证
        		.formLogin().successHandler(new AjaxAuthSuccessHandler())  //自定义登陆成功返回数据，否则会默认跳转成功页面
                .failureHandler(new AjaxAuthFailHandler())                 //自定义登陆失败返回数据，否则会默认跳转ERROR页面
                .loginProcessingUrl("/user/login/")                        //设置登陆的方法
        .and()
        	.sessionManagement()
        	.invalidSessionStrategy(new AjaxSessionAuthenticationFailureHandler())  //session验证失败比如超时处理
        .and()
            .logout().logoutSuccessHandler(new AjaxLogoutSuccessHandler())  //自定义退出登陆返回数据，否则会默认跳转登陆页面
            .logoutUrl("/user/logout/");                                          //自定义退出登陆URL （方法中设置销毁SESSION等逻辑）

        http.addFilterBefore(new MyWebFilter(), UsernamePasswordAuthenticationFilter.class);//将常用的WEB FILTER 在用户密码验证之前执行
    }

    //定义登陆成功返回信息及处理机制 如日志登陆时间等
    private class AjaxAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        	User currentUser = UserUtil.getCurrentPrincipal();
            //更新最后登录时间
            UserLoginLog ul = new UserLoginLog();
            ul.setDes("登陆");
            ul.setLoginTime(new Date());
            ul.setUserName(currentUser.getUsername());
            userLoginLogService.save(ul);

        	response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpStatus.OK.value());
            PrintWriter out = response.getWriter();
            out.write("{\"status\":\"ok\",\"msg\":\"登录成功\"}");
            out.flush();
            out.close();
        }
    }

    //定义SESSION验证失败成功返回信息
    private class AjaxSessionAuthenticationFailureHandler implements InvalidSessionStrategy {
		@Override
		public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {
    		 System.out.println("----------------AjaxSessionAuthenticationFailureHandler");
    		 response.setContentType("application/json;charset=utf-8");
             response.setStatus(HttpStatus.UNAUTHORIZED.value());
             PrintWriter out = response.getWriter();
             out.write("{\"status\":\"error\",\"msg\":\"REQUEST_UNAUTHORIZED\"}");
             out.flush();
             out.close();
         }
    }

    //定义登陆失败返回信息
    private class AjaxAuthFailHandler extends SimpleUrlAuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            PrintWriter out = response.getWriter();
            out.write("{\"status\":\"error\",\"msg\":\"请检查用户名、密码是否正确及账号是否停用"+exception.getMessage()+"\"}");
            out.flush();
            out.close();
        }
    }

    //定义异常返回信息
    public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
            response.sendError(HttpStatus.FORBIDDEN.value(), authException.getMessage());
        }
    }

    //定义登出成功返回信息
    private class AjaxLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler  {

        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                Authentication authentication) throws IOException, ServletException {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpStatus.OK.value());
            PrintWriter out = response.getWriter();
            out.write("{\"status\":\"ok\",\"msg\":\"登出成功\"}");
            out.flush();
            out.close();
        }
    }
}
