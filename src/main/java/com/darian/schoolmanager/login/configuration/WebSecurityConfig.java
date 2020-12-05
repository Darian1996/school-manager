package com.darian.schoolmanager.login.configuration;

import com.darian.schoolmanager.common.modle.CustomerResponse;
import com.darian.schoolmanager.common.utils.JSONUtils;
import com.darian.schoolmanager.login.service.AuthUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import java.io.IOException;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2020/9/16  23:05
 */
@Configuration
@EnableWebSecurity // 注解开启Spring Security的功能
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Resource
    private AuthUserService authUserService;

    @Resource(name = "passwordEncoder")
    private PasswordEncoder passwordEncoder;

    @Resource(name = "restAuthenticationEntryPoint")
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Resource
    private CustomizeLogoutSuccessHandler customizeLogoutSuccessHandler;

    @Resource
    private CustomerAccessDeniedHandler customerAccessDeniedHandler;


    @Resource
    private CustomerSecurityMetadataSource customerSecurityMetadataSource;

    @Resource
    private CustomerAccessDecisionManager customAccessDecisionManager;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * user Details Service验证
         */
        auth.userDetailsService(authUserService)
                .passwordEncoder(passwordEncoder)
        ;
    }

    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/start/**",
                "/start/layui/font/**", "/src/**", "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LoginSuccessHandler loginSuccessHandler = new LoginSuccessHandler();
        LoginFailedHandler loginFailedHandler = new LoginFailedHandler();
        /*
         *TODO   http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
         */
        // 会话管理
        http.sessionManagement()
                .maximumSessions(1) //同一账号同时登录最大用户数
                .maxSessionsPreventsLogin(false) // 当session达到最大后，阻止后登录的行为
                .expiredSessionStrategy(new CustomExpiredSessionStrategy())
        ;
        // ”Maximum sessions of 1 for this principal exceeded”

        // 这个是查询的所有的，不会动态变更
        //List<SecurityDO> securityDOList = securityService.selectEnableAllAndOrdered();
        //for (SecurityDO securityDO : securityDOList) {
        //    List<PermissionDO> permissionDOList = permissionSecurityService.selectPermissionListMySecurityId(securityDO.getId());
        //    try {
        //        http.authorizeRequests()
        //                .antMatchers(securityDO.getUrlPattern())
        //                .hasAnyAuthority(permissionDOList.stream()
        //                        .map(permissionDO -> permissionDO.getName()).collect(Collectors.toList())
        //                        .toArray(new String[0]));
        //    } catch (Exception e) {
        //        LOGGER.error("configure ---- {}", e.getMessage());
        //        e.printStackTrace();
        //    }
        //}

        // 配置数据库权限组管理
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(customerSecurityMetadataSource);
                        o.setAccessDecisionManager(customAccessDecisionManager);
                        return o;
                    }
                });

        http.authorizeRequests()
                //                .antMatchers("/manage/**").authenticated()
                //                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                //                .loginPage("/login")

                .permitAll()
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailedHandler)
                .usernameParameter("username")
                .passwordParameter("password")
                //登出
                .and()
                .logout()
                .permitAll()  //允许所有用户
                .logoutSuccessHandler(customizeLogoutSuccessHandler)//登出成功处理逻辑
                .deleteCookies("JSESSIONID")//登出之后删除cookie
                .and().csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler(customerAccessDeniedHandler) // 权限拒绝处理逻辑
                .authenticationEntryPoint(restAuthenticationEntryPoint) //匿名用户访问无权限资源时的异常处理
        ;

    }

    public static class CustomExpiredSessionStrategy implements SessionInformationExpiredStrategy {

        @Override
        public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
            CustomerResponse result = CustomerResponse.error("已经另一台机器登录，您被迫下线。" + event.getSessionInformation().getLastRequest());
            // Map -> Json
            String json = JSONUtils.beanToJson(result);

            event.getResponse().setContentType("application/json;charset=UTF-8");
            event.getResponse().getWriter().write(json);

            // 如果是跳转html页面，url代表跳转的地址
            // redirectStrategy.sendRedirect(event.getRequest(), event.getResponse(), "url");
        }
    }
}
