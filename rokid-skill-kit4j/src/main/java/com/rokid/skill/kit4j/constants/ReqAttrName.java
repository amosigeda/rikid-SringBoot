package com.rokid.skill.kit4j.constants;

/**
 * 请求参数存储key
 *
 * @author wuyukai
 */
public interface ReqAttrName {

    /**
     * 请求进入时间
     */
    String REQ_BEGIN_TIME = "ReqBeginTime";

    /**
     * 请求耗时
     */
    String REQ_COST_TIME = "costTime";
    /**
     * 请求服务名称
     */
    String SERVICE_NAME = "ServiceName";
    /**
     * 请求ID
     */
    String REQUEST_ID = "requestId";
    /**
     * 请求头
     */
    String REQ_HEADER = "HEADER";
    /**
     * 请求体
     */
    String REQ_BODY = "BODY";
    /**
     * 请求内容
     */
    String REQ_REQUEST = "ReqRequest";
    /**
     * IntentRequest中的拓展信息
     */
    String REQ_ATTRIBUTES = "ReqAttributes";
    /**
     * 基本请求信息
     */
    String REQ_BASIC_INFO = "ReqBasicInfo";
    /**
     * 当前Skill的Media和Voice状态信息
     */
    String REQ_MEDIA = "ReqMedia";
    /**
     * 当前Voice状态信息
     */
    String REQ_VOICE = "ReqVoice";
    /**
     * 当前地址位置
     */
    String REQ_LOCATION = "ReqLocation";
    /**
     * Media事件中的Media拓展信息
     */
    String REQ_MEDIA_EXTRA = "ReqMediaExtra";
    /**
     * Voice事件中的Voice拓展信息
     */
    String REQ_VOICE_EXTRA = "ReqVoiceExtra";
    /**
     * IntentRequest中的Slots信息
     */
    String REQ_SLOTS = "ReqSlots";
    /**
     * Controller或者Resolver中处理
     */
    String RESULT = "Result";
}
