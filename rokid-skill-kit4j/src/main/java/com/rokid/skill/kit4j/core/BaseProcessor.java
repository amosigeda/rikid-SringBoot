package com.rokid.skill.kit4j.core;

import com.rokid.skill.protocol.request.ReqRequest;
import com.rokid.skill.protocol.request.context.application.media.ReqApplicationMedia;
import com.rokid.skill.protocol.request.context.application.voice.ReqApplicationVoice;
import com.rokid.skill.protocol.request.request.content.extra.media.ReqExtraMedia;
import com.rokid.skill.protocol.request.request.content.extra.voice.ReqExtraVoice;
import com.rokid.skill.protocol.request.request.content.slot.Slot;
import com.rokid.skill.protocol.utils.ReqBasicInfo;
import lombok.Getter;

import java.util.Map;

/**
 * @author wuyukai on 2018/5/7.
 */
@Getter
public class BaseProcessor {

    private ReqRequest request;
    private ReqBasicInfo basicInfo;
    private Map<String, Object> attributes;
    private Map<String, Slot> slotMap;
    private ReqApplicationMedia applicationMedia;
    private ReqApplicationVoice applicationVoice;
    private ReqExtraMedia extraMedia;
    private ReqExtraVoice extraVoice;

    void initArguments(ReqRequest request, ReqBasicInfo basicInfo,
        Map<String, Object> attributes, Map<String, Slot> slotMap,
        ReqApplicationMedia applicationMedia, ReqApplicationVoice applicationVoice, ReqExtraMedia extraMedia,
        ReqExtraVoice extraVoice) {
        this.request = request;
        this.basicInfo = basicInfo;
        this.attributes = attributes;
        this.slotMap = slotMap;
        this.applicationMedia = applicationMedia;
        this.applicationVoice = applicationVoice;
        this.extraMedia = extraMedia;
        this.extraVoice = extraVoice;
    }
}
