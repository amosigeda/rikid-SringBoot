package com.rokid.skill.kit4j.core;

import com.rokid.skill.protocol.request.ReqRequest;
import com.rokid.skill.protocol.request.context.application.media.ReqApplicationMedia;
import com.rokid.skill.protocol.request.context.application.voice.ReqApplicationVoice;
import com.rokid.skill.protocol.request.request.content.extra.media.ReqExtraMedia;
import com.rokid.skill.protocol.request.request.content.extra.voice.ReqExtraVoice;
import com.rokid.skill.protocol.request.request.content.slot.Slot;
import com.rokid.skill.protocol.utils.ReqBasicInfo;
import java.util.Map;

/**
 * @author wuyukai on 2018/7/6.
 */
public interface SkillIntervention {

    /**
     * 是否支持干预
     *
     * @return 预，
     */
    boolean support();

    /**
     * 请求处理前进行干预，可干预请求中的所有参数
     *
     * @param reqRequest 请求
     * @param basicInfo 详情信息
     * @param attributes 属性
     * @param slots nlp输出的slot
     * @param mediaState 媒体状态
     * @param voiceState 音频状态
     * @param mediaExtra 媒体扩展字段
     * @param voiceExtra 音频扩展字段
     */
    void before(ReqRequest reqRequest,
        ReqBasicInfo basicInfo, Map<String, Object> attributes, Map<String, Slot> slots,
        ReqApplicationMedia mediaState, ReqApplicationVoice voiceState, ReqExtraMedia mediaExtra,
        ReqExtraVoice voiceExtra);

    /**
     * 请求处理后进行干预
     */
    void after();

}
