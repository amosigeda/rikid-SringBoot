package com.rokid.skill.kit4j.service.impl;

import com.rokid.skill.kit4j.exception.SkillKitException;
import com.rokid.skill.kit4j.service.SkillSecurityService;
import com.rokid.skill.protocol.utils.ReqBasicInfo;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("skillSecurityService")
public class SkillSecurityServiceImpl implements SkillSecurityService {

    @Override
    public boolean security(HttpServletRequest request, ReqBasicInfo reqBasicInfo)
        throws SkillKitException {
        log.debug("security check");
//		TODO 因目前工程中未使用到安全检查，暂时直接使用true返回。
        return true;
    }
}
