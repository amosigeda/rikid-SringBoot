package com.rokid.skill.kit4j.log;

import lombok.Data;

/**
 * 兼容老服务日志，不关注日志打印服务可去除此类
 *
 * @author wuyukai
 */
@Data
public class ServiceLog {

    private String id;
    private String masterId;
    private String requestId;
    private String sessionId;

    private String requestIp;
    private String appCode;
    private String appVersion;
    private String deviceId;
    private String deviceType;

    private String speechletId;


    private String serverIp;
    private String serviceName;
    private String serviceVersion;

    private String actionName;
    private String methodName;


    private String status;

    private Long gmtCreated;

    private Long costsTime;

    private String requestHeader;

    private String requestBody;

    private String result;

    private String exception;
}