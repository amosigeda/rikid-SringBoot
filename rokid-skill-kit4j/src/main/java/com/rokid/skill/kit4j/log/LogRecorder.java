package com.rokid.skill.kit4j.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author wuyukai on 2018/7/21.
 */
@Slf4j
@Component
public class LogRecorder {

    @Autowired(required = false)
    private LogService logService;

    /**
     * 创建服务请求日志
     *
     * @param serviceLog 服务日志
     */
    @Async("logTaskExecutor")
    public void createServiceLog(String serviceLog) {
        log.info("\n=====>ServiceLog<=====\n{}", serviceLog);
        if (logService != null && logService.support()) {
            logService.recordServiceLog();
        }
    }

    /**
     * 创建语音请求日志
     *
     * @param masterRequestLog 日志对象
     */
    @Async("logTaskExecutor")
    public void createMasterRequestLog(String masterRequestLog) {
        log.info("\n=====>MasterRequestLog<=====\n{}", masterRequestLog);
        if (logService != null && logService.support()) {
            logService.recordMasterRequestLog();
        }
    }

}
