package com.rokid.skill.kit4j.log;

import com.rokid.skill.kit4j.constants.Const;
import com.rokid.skill.kit4j.constants.ReqAttrName;
import com.rokid.skill.kit4j.util.IpHolder;
import com.rokid.skill.kit4j.util.JacksonUtil;
import com.rokid.skill.protocol.request.ReqRequest;
import com.rokid.skill.protocol.request.request.content.ReqRequestContent;
import com.rokid.skill.protocol.utils.ReqBasicInfo;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author wuyukai on 2018/4/4.
 */
@Slf4j
public class LogUtils {

    @SuppressWarnings("unchecked")
    public static String buildServiceLog(HttpServletRequest request, String exception) {
        ServiceLog sl = new ServiceLog();
        String id = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        sl.setId(id);
        sl.setServerIp(IpHolder.getIp());
        String speechletId = Optional.ofNullable(request.getAttribute(ReqAttrName.REQUEST_ID))
            .map(Object::toString).orElse("");
        sl.setSpeechletId(speechletId);
        sl.setRequestId(speechletId);
        // 获取请求IP
        String requestIp = Optional
            .ofNullable(request.getHeader(Const.HEADER_NAME_REAL_IP))
            .map(Object::toString).orElse("");
        sl.setRequestIp(requestIp);
        String serviceName = Optional.ofNullable(request
            .getAttribute(ReqAttrName.SERVICE_NAME)).map(Object::toString).orElse("");
        sl.setServiceName(serviceName);
        // 服务版本，正在处理日志的时候最好重载掉
        sl.setServiceVersion(Const.SERVICE_VERSION);
        sl.setMethodName(request.getMethod());
        String requestHeader = Optional.ofNullable(request
            .getAttribute(ReqAttrName.REQ_HEADER)).map(Object::toString).orElse("");
        sl.setRequestHeader(requestHeader);
        String requestBody = Optional.ofNullable(request
            .getAttribute(ReqAttrName.REQ_BODY)).map(Object::toString).orElse("");
        sl.setRequestBody(requestBody);
        sl.setStatus(Const.STATUS_SUCCESS);
        String result = Optional.ofNullable(request.getAttribute(ReqAttrName.RESULT))
            .map(Object::toString).orElse("");
        sl.setResult(result);
        Long beginTime = Optional.ofNullable(request
            .getAttribute(ReqAttrName.REQ_BEGIN_TIME)).map(l -> (Long) l).orElse(0L);
        sl.setGmtCreated(beginTime);
        Long costTime = Optional.ofNullable(request.getAttribute(ReqAttrName.REQ_COST_TIME))
            .map(l -> (Long) l).orElse(0L);
        sl.setCostsTime(costTime);
        Optional<ReqRequest> reqRequest = Optional.ofNullable(request
            .getAttribute(ReqAttrName.REQ_REQUEST)).map(r -> (ReqRequest) r);
        reqRequest.ifPresent(req -> {
            sl.setMasterId(req.getContext().getDevice().getBasic().getMasterId());
            sl.setDeviceId(req.getContext().getDevice().getBasic().getDeviceId());
            sl.setDeviceType(req.getContext().getDevice().getBasic().getDeviceType());
            ReqRequestContent content = req.getRequest().getContent();
            String actionName = content.getIntent();
            if (StringUtils.isBlank(actionName)) {
                actionName = content.getEvent();
            }
            sl.setActionName(actionName);
            sl.setSessionId(req.getSession().getSessionId());
            sl.setAppCode(req.getContext().getApplication().getApplicationId());
            sl.setAppVersion(req.getVersion());
        });
        sl.setException(StringUtils.defaultString(exception));

        return JacksonUtil.toJson(sl);
    }

    public static String buildSpeechletLog(HttpServletRequest request, String exception) {

        MasterRequestLog mrl = new MasterRequestLog();
        String id = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        mrl.setId(id);
        String requestId = Optional.ofNullable(request.getAttribute(ReqAttrName.REQUEST_ID))
            .map(Object::toString).orElse("");
        mrl.setRequestId(requestId);
        Optional<ReqBasicInfo> basicInfo = Optional
            .ofNullable(request.getAttribute(ReqAttrName.REQ_BASIC_INFO))
            .map(b -> (ReqBasicInfo) b);
        basicInfo.ifPresent(reqBasicInfo -> {
            mrl.setRokidid(reqBasicInfo.getDeviceId());
            mrl.setDomain(reqBasicInfo.getAppId());
            mrl.setIntent(reqBasicInfo.getActionName());
            mrl.setRequestId(reqBasicInfo.getRequestId());
            mrl.setSessionId(reqBasicInfo.getSessionId());
            mrl.setMasterId(reqBasicInfo.getMasterId());
        });
        mrl.setStatus(Const.STATUS_SUCCESS);
        if (StringUtils.isNotBlank(exception)) {
            mrl.setStatus(Const.STATUS_FAILED);
        }
        mrl.setDisabled(0);
        mrl.setDeleted(0);
        mrl.setGmtCreated(System.currentTimeMillis());
        mrl.setGmtModified(mrl.getGmtCreated());
        return JacksonUtil.toJson(mrl);
    }
}
