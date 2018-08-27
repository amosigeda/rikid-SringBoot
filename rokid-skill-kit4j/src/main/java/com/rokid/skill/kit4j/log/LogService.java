package com.rokid.skill.kit4j.log;

/**
 * @author wuyukai on 2018/7/21.
 */
public interface LogService {

    /**
     * 是否执行
     *
     * @return true执行服务中的方法
     */
    boolean support();

    /**
     * 记录服务日志
     */
    void recordServiceLog();

    /**
     * 记录语音请求日志
     */
    void recordMasterRequestLog();
}
