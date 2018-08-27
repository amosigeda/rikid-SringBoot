package com.rokid.skill.kit4j.service;

import com.rokid.skill.kit4j.exception.SkillKitException;
import com.rokid.skill.protocol.utils.ReqBasicInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * 安全检查服务
 * 
 * @author Bassam
 *
 */
public interface SkillSecurityService {
	boolean security(HttpServletRequest request, ReqBasicInfo reqBasicInfo) throws SkillKitException;
}
