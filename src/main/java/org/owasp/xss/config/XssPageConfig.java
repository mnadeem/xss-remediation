package org.owasp.xss.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="xss-page-config")
public class XssPageConfig {

	private String page;
	private List<XssParamConfig> paramConfigs;
	
	public XssPageConfig() {
		this.paramConfigs = new ArrayList<>();
	}

	public String getPage() {
		return page;
	}
	
	public void setPage(String page) {
		this.page = page;
	}
	
	public List<XssParamConfig> getParamConfigs() {
		return paramConfigs;
	}
	
	@XmlElement(name = "xss-param-config")
	public void setParamConfigs(List<XssParamConfig> paramConfigs) {
		this.paramConfigs = paramConfigs;
	}
	
	public void addParamConfig(XssParamConfig config) {
		this.paramConfigs.add(config);
	}

	public boolean forPage(String another) {
		return page.equalsIgnoreCase(another);
	}
	

	public XssParamContext getParamContext(String parameter) {
		for (XssParamConfig xssParamConfig : paramConfigs) {
			if (xssParamConfig.getParam().equalsIgnoreCase(parameter)) {
				return xssParamConfig.getContext();
			}
		}
		return null;
	}
}
