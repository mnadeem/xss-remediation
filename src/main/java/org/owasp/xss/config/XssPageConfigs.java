package org.owasp.xss.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="xss-page-configs")
public class XssPageConfigs {

	private List<XssPageConfig> configs;

	public XssPageConfigs() {
		this.configs = new ArrayList<>();
	}

	public List<XssPageConfig> getConfigs() {
		return configs;
	}
	
	@XmlElement(name = "xss-page-config")
	public void setConfigs(List<XssPageConfig> configs) {
		this.configs = configs;
	}
	
	public void addConfig(XssPageConfig config) {
		this.configs.add(config);
	}

	public XssParamContext getParamContext(String page, String parameter) {
		XssPageConfig pageConfig = getPageConfig(page);
		return pageConfig != null ? pageConfig.getParamContext(parameter) : null;
	}

	private XssPageConfig getPageConfig(String page) {
		for (XssPageConfig xssPageConfig : configs) {
			if (xssPageConfig.forPage(page)) {
				return xssPageConfig;
			}
		}
		return null;
	}

}
