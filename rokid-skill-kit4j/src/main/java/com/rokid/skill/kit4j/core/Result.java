package com.rokid.skill.kit4j.core;

import com.rokid.skill.protocol.response.ResResponse;
import lombok.Builder;
import lombok.Data;

/**
 * 统一API响应结果封装
 *
 * @author wuyukai
 * @date 2018/7/22
 */
@Data
@Builder
public class Result {

    private int code;
    private String message;
    private ResResponse data;
}

