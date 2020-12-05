package com.darian.schoolmanager.login.configuration;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;

import java.util.List;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/18  1:55
 */
@SpringBootTest
public class WebSecurityConfigTest {

    @Test
    public void passwordEncoder() {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }

    @Test
    public void configure() {
        //
        PatternsRequestCondition patternsCondition = new PatternsRequestCondition("/**/*.html", "/**");
        List<String> matchingPatterns = patternsCondition.getMatchingPatterns("/start/index.html");
        System.out.println(matchingPatterns);
    }
}