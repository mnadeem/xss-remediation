package org.owasp.xss.config;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class XssParamConfig {
	
	private String param;
	private XssParamContext context;

	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public XssParamContext getContext() {
		return context;
	}
	public void setContext(XssParamContext context) {
		this.context = context;
	}
}
