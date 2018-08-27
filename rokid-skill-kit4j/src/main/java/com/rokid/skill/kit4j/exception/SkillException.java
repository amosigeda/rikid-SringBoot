package com.rokid.skill.kit4j.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 语音业务异常情况
 *
 * @author Bassam
 */
public class SkillException extends RuntimeException {

    private static final long serialVersionUID = 2006382839104348426L;

    @Getter
    @Setter
    private int errorCode;
    @Getter
    @Setter
    private String actionType;
    @Getter
    @Setter
    private String actionName;

    public SkillException(int errorCode, String message, Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
    }

    public SkillException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public SkillException(int errorCode, String message, String actionType,
        String actionName) {
        super(message);
        this.errorCode = errorCode;
        this.actionType = actionType;
        this.actionName = actionName;
    }

    public SkillException(int errorCode, String message, Throwable throwable,
        String actionType,
        String actionName) {
        super(message, throwable);
        this.errorCode = errorCode;
        this.actionType = actionType;
        this.actionName = actionName;
    }
}
