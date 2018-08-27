package com.rokid.skill.kit4j.core;

import com.rokid.skill.kit4j.constants.ReqAttrName;
import com.rokid.skill.kit4j.exception.DefaultExceptionHandler;
import com.rokid.skill.kit4j.util.JacksonUtil;
import com.rokid.skill.protocol.request.ReqRequest;
import com.rokid.skill.protocol.request.context.application.media.ReqApplicationMedia;
import com.rokid.skill.protocol.request.context.application.voice.ReqApplicationVoice;
import com.rokid.skill.protocol.request.request.content.extra.media.ReqExtraMedia;
import com.rokid.skill.protocol.request.request.content.extra.voice.ReqExtraVoice;
import com.rokid.skill.protocol.request.request.content.slot.Slot;
import com.rokid.skill.protocol.response.ResResponse;
import com.rokid.skill.protocol.utils.ReqBasicInfo;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 语音业务请求入口
 *
 * @author wuyukai
 */
@Slf4j
@CrossOrigin
@RestController
public class SkillController {

    /**
     * 业务分发器
     */
    private final SpeechletDispatcher speechletDispatcher;

    private final DefaultExceptionHandler defaultExceptionHandler;

    @Autowired
    public SkillController(SpeechletDispatcher speechletDispatcher,
        DefaultExceptionHandler defaultExceptionHandler) {
        this.speechletDispatcher = speechletDispatcher;
        this.defaultExceptionHandler = defaultExceptionHandler;
    }

    /**
     * 设备调用统一入口
     *
     * @param request 请求
     * @return 响应json字符串
     * @throws Exception 异常
     */
    @RequestMapping(path = "/speechlet", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String handleRequest(HttpServletRequest request) throws Exception {
        // 原始的请求数据对象
        ReqRequest reqRequest = (ReqRequest) request.getAttribute(ReqAttrName.REQ_REQUEST);
        // 二次处理后的请求数据
        ReqBasicInfo basicInfo = (ReqBasicInfo) request.getAttribute(ReqAttrName.REQ_BASIC_INFO);
        // 属性信息
        @SuppressWarnings("unchecked")
        Map<String, Object> attributes = (Map<String, Object>) request
            .getAttribute(ReqAttrName.REQ_ATTRIBUTES);
        //当前Skill的媒体状态信息
        ReqApplicationMedia mediaState = (ReqApplicationMedia) request
            .getAttribute(ReqAttrName.REQ_MEDIA);
        //当前语音的状态信息
        ReqApplicationVoice voiceState = (ReqApplicationVoice) request
            .getAttribute(ReqAttrName.REQ_VOICE);
        @SuppressWarnings("unchecked")
        // Intent请求的Slots信息
            Map<String, Slot> slots = (Map<String, Slot>) request
            .getAttribute(ReqAttrName.REQ_SLOTS);
        //当前媒体事件上报的Media拓展信息
        ReqExtraMedia mediaExtra = (ReqExtraMedia) request
            .getAttribute(ReqAttrName.REQ_MEDIA_EXTRA);
        //当前Voice事件上报的Voice拓展信息
        ReqExtraVoice voiceExtra = (ReqExtraVoice) request
            .getAttribute(ReqAttrName.REQ_VOICE_EXTRA);
        // 进行业务分发
        ResResponse resResponse = speechletDispatcher
            .handle(reqRequest, basicInfo, attributes, slots, mediaState, voiceState, mediaExtra,
                voiceExtra);
        String result = JacksonUtil.toJson(resResponse);
        //log.info("RESPONSE : {}", result);
        request.setAttribute("Result", result);
        return result;
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResResponse defaultErrorHandler(
        HttpServletRequest request, HttpServletResponse response, Exception ex) {
        return defaultExceptionHandler.errorHandler(request, response, ex);
    }

}
