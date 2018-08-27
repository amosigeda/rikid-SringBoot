package com.rokid.skill.kit4j.constants;

/**
 * 宏定义
 *
 * @author Bassam
 */
public interface Const {

    /**
     * 日志 trace id
     */
    String TRACE_ID = "traceId";

    String HEADER_NAME_REAL_IP = "x-real-ip";
    /**
     * 服务版本
     */
    String SERVICE_VERSION = "1.0.0";

    /**
     * 成功状态
     */
    String STATUS_SUCCESS = "200";
    /**
     * 失败状态
     */
    String STATUS_FAILED = "500";

    /**
     * 业务处理耗时警告预值
     */
    int MONITOR_TIME = 1000;

    /**
     * 未知的厂商ID
     */
    String DEFAULT_UNKNOWN_DEVICE_TYPE = "UNKNOWN_DEVICE_TYPE";
    /**
     * 未知的设备类型
     */
    String DEFAULT_UNKNOWN_VENDOR = "UNKNOWN_VENDOR";
    /**
     * 未知设备Id
     */
    String DEFAULT_UNKNOWN_DEVICE_ID = "UNKNOWN_DEVICE_ID";
    /**
     * 未知的机主ID
     */
    String DEFAULT_UNKNOWN_MASTER_ID = "UNKNOWN_MASTER_ID";
    /**
     * 未知的用户ID
     */
    String DEFAULT_UNKNOWN_USER_ID = "UNKNOWN_USER_ID";
}
