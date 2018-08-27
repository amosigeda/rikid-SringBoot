package com.rokid.skill.kit4j.exception;

import com.rokid.skill.protocol.response.ResResponse;
import com.rokid.skill.protocol.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 异常处理
 *
 * @author Bassam
 */
@Slf4j
@Component
public class ExceptionDispatcher {

    public ResResponse skillExceptionDisPatcher(SkillException ex) {
        return ResponseUtils.buildIngoreEventResponse();
    }

    public ResResponse skillKitExceptionDisPatcher(SkillKitException ex) {
        return ResponseUtils.buildIngoreEventResponse();
    }

    public ResResponse exceptionDisPatcher(Exception ex) {
        return ResponseUtils.buildIngoreEventResponse();
    }
}
