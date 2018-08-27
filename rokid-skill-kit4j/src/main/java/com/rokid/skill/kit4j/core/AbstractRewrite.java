package com.rokid.skill.kit4j.core;

import com.rokid.skill.protocol.request.ReqRequest;
import com.rokid.skill.protocol.request.context.application.media.ReqApplicationMedia;
import com.rokid.skill.protocol.request.context.application.voice.ReqApplicationVoice;
import com.rokid.skill.protocol.request.request.content.extra.media.ReqExtraMedia;
import com.rokid.skill.protocol.request.request.content.extra.voice.ReqExtraVoice;
import com.rokid.skill.protocol.request.request.content.slot.Slot;
import com.rokid.skill.protocol.utils.ReqBasicInfo;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author wuyukai on 2018/7/8.
 */
@Slf4j
public abstract class AbstractRewrite implements SkillIntervention {

    @Override
    public final void before(ReqRequest reqRequest,
        ReqBasicInfo basicInfo, Map<String, Object> attributes, Map<String, Slot> slots,
        ReqApplicationMedia mediaState, ReqApplicationVoice voiceState, ReqExtraMedia mediaExtra,
        ReqExtraVoice voiceExtra) {
        //action 改写
        String currentActionName = rewriteAction(basicInfo.getActionName());
        log.info("before rewrite action name : {}, after rewrite action name : {}",
            basicInfo.getActionName(), currentActionName);
        if (StringUtils.isNotBlank(currentActionName)) {
            basicInfo.setActionName(currentActionName);
        }
        log.info("before rewrite slots : originalSlots = {}", slots);
        Map<String, Slot> currentSlots = rewriteSlots(slots);
        log.info("after rewrite slots : currentSlots = {}", currentSlots);
        if (currentSlots != null) {
            slots = currentSlots;
        }
    }

    @Override
    public final void after() {

    }

    /**
     * intent或event改写
     *
     * @param originalActionName 意图/事件名称，原始名称
     * @return 改写后名称
     */
    public abstract String rewriteAction(String originalActionName);

    /**
     * 申请需改写的slot key值，通过key值下发slot
     *
     * @return key值的数据
     */
    public String[] applyRewriteSlots() {
        return null;
    }

    /**
     * 对slot进行改写，仅允许对申请改写的slots进行改写， 并且使用校验，不支持新增或删减或将原slot赋值为空的操作
     *
     * @param originalSlots 改写前的slots
     * @return 改写后的slots
     */
    public abstract Map<String, Slot> rewriteSlots(Map<String, Slot> originalSlots);
}
