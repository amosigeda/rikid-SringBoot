package com.rokid.skill.kit4j.core;

import com.rokid.skill.kit4j.exception.SkillKitException;
import com.rokid.skill.protocol.request.ReqRequest;
import com.rokid.skill.protocol.request.context.application.media.ReqApplicationMedia;
import com.rokid.skill.protocol.request.context.application.voice.ReqApplicationVoice;
import com.rokid.skill.protocol.request.request.content.extra.media.ReqExtraMedia;
import com.rokid.skill.protocol.request.request.content.extra.voice.ReqExtraVoice;
import com.rokid.skill.protocol.request.request.content.slot.Slot;
import com.rokid.skill.protocol.response.ResResponse;
import com.rokid.skill.protocol.utils.ReqBasicInfo;
import java.lang.reflect.Method;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wuyukai on 2018/5/5.
 */
@Slf4j
@Component("speechletDispatcher")
public class SkillDispatcher implements SpeechletDispatcher {

    private final ActionRegistry actionRegistry;

    private final SkillInterventionChain interventionChain;

    @Autowired
    public SkillDispatcher(ActionRegistry actionRegistry,
        SkillInterventionChain interventionChain) {
        this.actionRegistry = actionRegistry;
        this.interventionChain = interventionChain;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResResponse handle(ReqRequest reqRequest,
        ReqBasicInfo basicInfo, Map<String, Object> attributes, Map<String, Slot> slots,
        ReqApplicationMedia mediaState, ReqApplicationVoice voiceState, ReqExtraMedia mediaExtra,
        ReqExtraVoice voiceExtra) throws Exception {
        interventionChain
            .before(reqRequest, basicInfo, attributes, slots, mediaState, voiceState, mediaExtra,
                voiceExtra);
        Map<String, Handler> router = actionRegistry.getRouter();
        String actionName = basicInfo.getActionName();
        Handler handler = router.get(actionName);
        if (handler == null) {
            throw new SkillKitException(
                "can not find a handler to deal this request, maybe the intent or event does not register");
        }
        Method method = handler.getMethod();
        Object bean = handler.getBean();
        ((BaseProcessor) bean)
            .initArguments(reqRequest, basicInfo, attributes, slots, mediaState,
                voiceState, mediaExtra, voiceExtra);
        ResResponse responseResult = (ResResponse) method.invoke(bean);
        interventionChain.after();
        return responseResult;
    }
}
