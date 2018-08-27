package com.rokid.skill.kit4j.config;

import com.rokid.skill.kit4j.constants.ReqAttrName;
import com.rokid.skill.kit4j.exception.SkillKitException;
import com.rokid.skill.kit4j.service.SkillSecurityService;
import com.rokid.skill.protocol.utils.ReqBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 安全拦截器
 *
 * @author Bassam
 */
@Slf4j
class SecurityInterceptor extends HandlerInterceptorAdapter {

    private final SkillSecurityService skillSecurityService;

    @Autowired
    public SecurityInterceptor(SkillSecurityService skillSecurityService) {
        this.skillSecurityService = skillSecurityService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        ReqBasicInfo basicInfo = (ReqBasicInfo) request.getAttribute(ReqAttrName.REQ_BASIC_INFO);
        // 安全检查
        if (!skillSecurityService.security(request, basicInfo)) {
            throw new SkillKitException("SecurityError");
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
