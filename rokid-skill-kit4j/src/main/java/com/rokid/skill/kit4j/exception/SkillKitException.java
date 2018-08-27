package com.rokid.skill.kit4j.exception;

/**
 * 通用异常情况
 *
 * @author Bassam
 */
public class SkillKitException extends RuntimeException {

    private static final long serialVersionUID = -2586492750892355169L;

    public SkillKitException(String message) {
        super(message);
    }

    public SkillKitException(String message, Throwable cause) {
        super(message, cause);
    }

}
