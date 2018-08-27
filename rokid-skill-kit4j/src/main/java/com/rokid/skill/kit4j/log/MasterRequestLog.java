package com.rokid.skill.kit4j.log;

import lombok.Data;

/**
 * 兼容老服务日志，不关注日志打印服务可去除此类
 *
 * @author wuyukai
 */
@Data
public class MasterRequestLog {

    private String id;
    private String rokidid;
    private String domain;
    private String intent;
    private String slots;
    private String requestId;
    private String sessionId;
    private String masterId;
    private String status;
    private Integer disabled;
    private Integer deleted;
    private Long gmtCreated;
    private Long gmtModified;
    private String createdBy;
    private String modifiedBy;
}