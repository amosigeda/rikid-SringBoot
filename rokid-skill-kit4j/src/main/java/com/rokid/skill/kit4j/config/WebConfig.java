package com.rokid.skill.kit4j.config;

import com.rokid.skill.kit4j.log.LogRecorder;
import com.rokid.skill.kit4j.service.SkillSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 引入 kit4j 中的spring配置上下文等
 *
 * @author wuyukai
 */
@Configuration
@EnableWebMvc
@AutoConfigureAfter(value = WebMvcAutoConfiguration.class)
public class WebConfig implements WebMvcConfigurer {

    private final SkillSecurityService skillSecurityService;
    private final LogRecorder logRecorder;

    @Autowired
    public WebConfig(LogRecorder logRecorder,
        SkillSecurityService skillSecurityService) {
        this.logRecorder = logRecorder;
        this.skillSecurityService = skillSecurityService;
    }

    /**
     * 拦截器添加，拦截器的定义在 skill-kit4j 中
     *
     * @param registry 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityInterceptor(skillSecurityService))
            .addPathPatterns("/security");
        registry.addInterceptor(new ControllerInterceptor(logRecorder))
            .addPathPatterns("/speechlet");
    }

}
