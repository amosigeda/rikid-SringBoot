package com.rokid.skill.kit4j.core;


import java.lang.reflect.Method;

/**
 * @author wuyukai on 2018/5/6.
 */
public class Handler {

	private Method method;
	private Object bean;

	public Handler(Method method, Object bean) {
		this.method = method;
		this.bean = bean;
	}

	public Method getMethod() {
		return method;
	}

	public Object getBean() {
		return bean;
	}

}
