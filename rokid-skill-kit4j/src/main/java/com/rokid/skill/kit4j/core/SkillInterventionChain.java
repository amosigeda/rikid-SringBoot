package com.rokid.skill.kit4j.core;

import com.rokid.skill.protocol.request.ReqRequest;
import com.rokid.skill.protocol.request.context.application.media.ReqApplicationMedia;
import com.rokid.skill.protocol.request.context.application.voice.ReqApplicationVoice;
import com.rokid.skill.protocol.request.request.content.extra.media.ReqExtraMedia;
import com.rokid.skill.protocol.request.request.content.extra.voice.ReqExtraVoice;
import com.rokid.skill.protocol.request.request.content.slot.Slot;
import com.rokid.skill.protocol.utils.ReqBasicInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wuyukai on 2018/7/6.
 */
@Component
public class SkillInterventionChain {

    private final List<SkillIntervention> interventions;

    @Autowired
    public SkillInterventionChain(Optional<List<SkillIntervention>> optionalSkillInterventions) {
        this.interventions = optionalSkillInterventions.orElseGet(ArrayList::new);
    }

    void before(ReqRequest reqRequest,
        ReqBasicInfo basicInfo, Map<String, Object> attributes, Map<String, Slot> slots,
        ReqApplicationMedia mediaState, ReqApplicationVoice voiceState, ReqExtraMedia mediaExtra,
        ReqExtraVoice voiceExtra) {

        for (SkillIntervention intervention : interventions) {
            if (intervention.support()) {
                intervention
                    .before(reqRequest, basicInfo, attributes, slots, mediaState, voiceState,
                        mediaExtra, voiceExtra);
            }
        }
    }

    void after() {
        for (int i = interventions.size() - 1; i >= 0; i--) {
            interventions.get(i).after();
        }
    }
}
