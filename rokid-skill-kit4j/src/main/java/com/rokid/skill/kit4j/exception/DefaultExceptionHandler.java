package com.rokid.skill.kit4j.exception;

import com.rokid.skill.kit4j.constants.ReqAttrName;
import com.rokid.skill.kit4j.log.LogRecorder;
import com.rokid.skill.kit4j.log.LogUtils;
import com.rokid.skill.kit4j.util.JacksonUtil;
import com.rokid.skill.protocol.response.ResResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author wuyukai on 2018/6/16.
 */
@Slf4j
@Component
public class DefaultExceptionHandler {

    private final LogRecorder logRecorder;
    private final ExceptionDispatcher exceptionDispatcher;

    @Autowired
    public DefaultExceptionHandler(ExceptionDispatcher exceptionDispatcher,
        LogRecorder logRecorder) {
        this.exceptionDispatcher = exceptionDispatcher;
        this.logRecorder = logRecorder;
    }

    public ResResponse errorHandler(
        HttpServletRequest request, HttpServletResponse response, Exception ex) {
        ResResponse resResponse;
        if (ex instanceof SkillException) {
            SkillException skillException = (SkillException) ex;
            resResponse = exceptionDispatcher
                .skillExceptionDisPatcher(skillException);
        } else if (ex instanceof SkillKitException) {
            SkillKitException kitException = (SkillKitException) ex;
            resResponse = exceptionDispatcher.skillKitExceptionDisPatcher(kitException);
        } else {
            resResponse = exceptionDispatcher.exceptionDisPatcher(ex);
        }
        log.info("RESPONSE : {}", resResponse);
        // 虽然是异常，但以正确的http状态返回
        response.setStatus(HttpStatus.OK.value());
        log.error(ex.getMessage(), ex);
        String result = JacksonUtil.toJson(resResponse);
        request.setAttribute(ReqAttrName.RESULT, result);
        String sl = LogUtils.buildServiceLog(request, ExceptionUtils.getStackTrace(ex));
        logRecorder.createServiceLog(sl);
        String mrl = LogUtils.buildSpeechletLog(request, ExceptionUtils.getStackTrace(ex));
        logRecorder.createMasterRequestLog(mrl);
        MDC.clear();
        return resResponse;
    }
}
