package com.rokid.skill.kit4j.config;

import com.rokid.skill.kit4j.constants.Const;
import com.rokid.skill.kit4j.constants.ReqAttrName;
import com.rokid.skill.kit4j.exception.SkillKitException;
import com.rokid.skill.kit4j.log.LogRecorder;
import com.rokid.skill.kit4j.util.JacksonUtil;
import com.rokid.skill.kit4j.log.LogUtils;
import com.rokid.skill.protocol.request.ReqRequest;
import com.rokid.skill.protocol.request.context.application.media.ReqApplicationMedia;
import com.rokid.skill.protocol.request.context.application.voice.ReqApplicationVoice;
import com.rokid.skill.protocol.request.context.device.ReqDevice;
import com.rokid.skill.protocol.request.context.device.basic.ReqBasic;
import com.rokid.skill.protocol.request.context.device.location.ReqLocation;
import com.rokid.skill.protocol.request.context.user.ReqUser;
import com.rokid.skill.protocol.request.request.content.enums.RokidActionTypeEnums;
import com.rokid.skill.protocol.request.request.content.extra.media.ReqExtraMedia;
import com.rokid.skill.protocol.request.request.content.extra.voice.ReqExtraVoice;
import com.rokid.skill.protocol.request.request.content.slot.Slot;
import com.rokid.skill.protocol.utils.ReqBasicInfo;
import com.rokid.skill.protocol.utils.RequestUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * 控制拦截器，在config类中注入了LogRecorder，在此处不注入，限制skill使用该拦截器
 *
 * @author Bassam
 */
@Slf4j
class ControllerInterceptor extends HandlerInterceptorAdapter {

    private final LogRecorder logRecorder;

    ControllerInterceptor(LogRecorder logRecorder) {
        this.logRecorder = logRecorder;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler)
        throws Exception {

        // 设置请求时间
        Long beginTime = System.currentTimeMillis();
        request.setAttribute(ReqAttrName.REQ_BEGIN_TIME, beginTime);

        String serviceName = request.getRequestURI().substring(request.getContextPath().length());
        if (StringUtils.isBlank(serviceName)) {
            log.error("serviceNameError:serviceName is blank");
            throw new ServletException("ServiceNameError");
        }

        // 设置服务名称
        request.setAttribute(ReqAttrName.SERVICE_NAME, serviceName);

        String headers = readRequestHeaders(request).toString();
        request.setAttribute(ReqAttrName.REQ_HEADER, headers);
        String url = getUrl(request);
        log.info("REQUEST BEGIN : Method={}, Url={}, Headers={}", request.getMethod(), url,
            headers);
        String body = getBodyFromRequest(request);
        if (StringUtils.isBlank(body)) {
            log.error("request body is empty");
            throw new SkillKitException("body is empty");
        }
        request.setAttribute(ReqAttrName.REQ_BODY, body);
        // 处理请求数据
        processRokidProtocolData(request, body);
        // 数据处理
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {
        Long beginTime = (Long) request.getAttribute(ReqAttrName.REQ_BEGIN_TIME);
        Long costsTime = System.currentTimeMillis() - beginTime;
        request.setAttribute(ReqAttrName.REQ_COST_TIME, costsTime);
        if (costsTime > Const.MONITOR_TIME) {
            log.warn("TOTAL COSTS = {}ms", costsTime);
        } else {
            log.info("TOTAL COSTS = {}ms", costsTime);
        }
        String sl = LogUtils.buildServiceLog(request, null);
        logRecorder.createServiceLog(sl);
        String mrl = LogUtils.buildSpeechletLog(request, null);
        logRecorder.createMasterRequestLog(mrl);
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex) throws Exception {
        MDC.clear();
        super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 数据预处理
     */
    private void processRokidProtocolData(HttpServletRequest request, String body)
        throws SkillKitException {
        ReqRequest reqRequest;
        // 获取请求体
        reqRequest = JacksonUtil.toEntity(body, ReqRequest.class);
        if (reqRequest == null) {
            log.error("parse reqRequest error");
            throw new SkillKitException("body parse error");
        }

        if (reqRequest.getContext() == null) {
            log.error("reqContext not allowed to be empty");
            throw new SkillKitException("ReqContext error");
        }
        // 基本请求信息
        ReqBasicInfo reqBasicInfo = RequestUtils.getBasicInfo(reqRequest);
        String requestId = reqBasicInfo.getRequestId();
        if (StringUtils.isBlank(requestId)) {
            requestId = UUID.randomUUID().toString().replace("-", "").toUpperCase();
            log.warn("no requestId, replace it by new uuid : {}", requestId);
            reqRequest.getRequest().setReqId(requestId);
            reqBasicInfo.setRequestId(requestId);
        }
        log.info("REQUEST ID : {}", requestId);
        MDC.put(Const.TRACE_ID, requestId);
        log.info("SESSION ID : {}", reqBasicInfo.getSessionId());
        log.info("REQUEST BODY: {}", body);
        request.setAttribute(ReqAttrName.REQUEST_ID, requestId);
        log.info("ACTION TYPE : {}, ACTION NAME : {}", reqBasicInfo.getActionType(),
            reqBasicInfo.getActionName());
        if (StringUtils.isBlank(reqBasicInfo.getActionType())
            || RokidActionTypeEnums.convert(reqBasicInfo.getActionType()) == null || StringUtils
            .isBlank(reqBasicInfo.getActionName())) {
            log.error("ReqBasicInfo error, action type is not allowed or action name is empty");
            throw new SkillKitException(
                "ReqBasicInfo error, action type is not allowed or action name is empty");
        }
        if (reqRequest.getRequest() == null) {
            log.error("ReqRequestValue error, request value not allowed to be empty");
            throw new SkillKitException(
                "ReqRequestValue error, request value not allowed to be empty");
        }

        if (reqRequest.getContext() != null) {
            // 进行一次数据矫正功能，保证服务不会出现空指针异常
            if (reqRequest.getContext().getUser() == null) {
                ReqUser reqUser = new ReqUser();
                reqRequest.getContext().setUser(reqUser);
                log.warn("NO USER NODE");
            }
            if (reqRequest.getContext().getDevice() == null) {
                ReqDevice reqDevice = new ReqDevice();
                reqRequest.getContext().setDevice(reqDevice);
                log.warn("NO DEVICE NODE");
            }
            if (reqRequest.getContext().getDevice().getBasic() == null) {
                ReqBasic reqBasic = new ReqBasic();
                reqRequest.getContext().getDevice().setBasic(reqBasic);
                log.warn("NO DEVICE BASIC NODE");
            }
            String vendor = reqRequest.getContext().getDevice().getBasic().getVendor();
            String deviceType = reqRequest.getContext().getDevice().getBasic().getDeviceType();
            String deviceId = reqRequest.getContext().getDevice().getBasic().getDeviceId();
            String masterId = reqRequest.getContext().getDevice().getBasic().getMasterId();
            String userId = reqRequest.getContext().getUser().getUserId();
            if (StringUtils.isBlank(vendor)) {
                vendor = Const.DEFAULT_UNKNOWN_VENDOR;
                log.warn("no vendor put it like: {}", vendor);
            }
            if (StringUtils.isBlank(deviceType)) {
                deviceType = Const.DEFAULT_UNKNOWN_DEVICE_TYPE;
                log.warn("no deviceType put it like: {}", deviceType);
            }
            if (StringUtils.isBlank(deviceId)) {
                deviceId = Const.DEFAULT_UNKNOWN_DEVICE_ID;
                log.warn("no deviceId put it like: {}", deviceId);
            }
            if (StringUtils.isBlank(masterId)) {
                masterId = Const.DEFAULT_UNKNOWN_MASTER_ID;
                log.warn("no masterId put it like: {}", masterId);
            }
            if (StringUtils.isBlank(userId)) {
                userId = Const.DEFAULT_UNKNOWN_USER_ID;
                log.warn("no userId put it like:{}", userId);
            }
        }

        // 获取Slots信息
        Map<String, Slot> slots = RequestUtils.getSlots(reqRequest);
        // 获取拓展信息
        Map<String, Object> attributes = RequestUtils.getAttributes(reqRequest);
        // Media状态
        ReqApplicationMedia mediaState = RequestUtils.getMediaState(reqRequest);
        // Voice状态
        ReqApplicationVoice voiceState = RequestUtils.getVoiceState(reqRequest);
        // 获取地理位置
        ReqLocation reqLocation = RequestUtils.getLocation(reqRequest);
        // 获取媒体状态信息
        ReqExtraMedia mediaExtra = RequestUtils.getMediaExtra(reqRequest);
        // 获取TTS状态信息
        ReqExtraVoice voiceExtra = RequestUtils.getVoiceExtra(reqRequest);

        // 放置请求内容
        request.setAttribute(ReqAttrName.REQ_REQUEST, reqRequest);
        request.setAttribute(ReqAttrName.REQ_ATTRIBUTES, attributes);
        request.setAttribute(ReqAttrName.REQ_BASIC_INFO, reqBasicInfo);
        request.setAttribute(ReqAttrName.REQ_MEDIA, mediaState);
        request.setAttribute(ReqAttrName.REQ_VOICE, voiceState);
        request.setAttribute(ReqAttrName.REQ_LOCATION, reqLocation);
        request.setAttribute(ReqAttrName.REQ_MEDIA_EXTRA, mediaExtra);
        request.setAttribute(ReqAttrName.REQ_VOICE_EXTRA, voiceExtra);
        request.setAttribute(ReqAttrName.REQ_SLOTS, slots);
    }

    private String getBodyFromRequest(HttpServletRequest request) throws IOException {
        try (BufferedReader reader = request.getReader()) {
            return reader.lines().map(String::trim).filter(s -> !s.isEmpty())
                .collect(Collectors.joining());

        }
    }

    private Map<String, String> readRequestHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>(8);
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                headers.put(headerName, headerValue);
            }
        }
        return headers;
    }

    private String getUrl(HttpServletRequest request) {
        return request.getScheme() + "://" +
            request.getServerName() +
            ("http".equals(request.getScheme()) && request.getServerPort() == 80
                || "https".equals(request.getScheme()) && request.getServerPort() == 443 ? ""
                : ":" + request.getServerPort()) +
            request.getRequestURI() +
            (request.getQueryString() != null ? "?" + request.getQueryString() : "");
    }
}
