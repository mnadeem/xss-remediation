package org.owasp.xss;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.owasp.xss.config.XssPageConfigs;
import org.owasp.xss.config.XssParamContext;

public class XSSRequestWrapper extends HttpServletRequestWrapper {

	private XssPageConfigs configs;

    public XSSRequestWrapper(XssPageConfigs configs, HttpServletRequest servletRequest) {
        super(servletRequest);
        this.configs = configs;
    }

    @Override
    public String getParameter(String parameter) {
    	XssParamContext xssContext = getXssParamContext(parameter);
    	SafeDataBuilder builder = SafeDataBuilder.requestParam((HttpServletRequest) getRequest(), cleanParameter(parameter, xssContext));
        if (xssContext != null) {
        	stripXSS(builder, xssContext);
		}
        return builder.get();
    }

	private String cleanParameter(String parameter, XssParamContext xssContext) {
		if (parameter != null && xssContext !=null && parameter.endsWith(xssContext.name())) {
			return parameter.substring(0, parameter.lastIndexOf("_" + xssContext.name()));
		}
		return parameter;
	}

	private XssParamContext getXssParamContext(String parameter) {
		XssParamContext xssContextFromConfig =  getXssContextFromPageConfig(parameter);
		if (xssContextFromConfig != null) {
			return xssContextFromConfig;
		}
    	
		return getXssContextFromParam(parameter);
	}

	private XssParamContext getXssContextFromPageConfig(String parameter) {
		String page = super.getRequestURI();
		return configs.getParamContext(page, parameter);
	}

	private XssParamContext getXssContextFromParam(String parameter) {
		try {
			return XssParamContext.valueOf(parameter.substring(parameter.indexOf("_") + 1));
		} catch (IllegalArgumentException e) {
			
		}
		return null;
	}

	private void stripXSS(SafeDataBuilder builder, XssParamContext xssContext) {
		if (XssParamContext.BASIC_HTML.equals(xssContext)) {
			builder.forHtml();
		} else if (XssParamContext.HTML_CONTENT.equals(xssContext)) {
			builder.forHtmlContent();
		} else if (XssParamContext.HTML_ATTRIBUTE.equals(xssContext)) {
			 builder.forHtmlAttribute();
		} else if (XssParamContext.JS_BLOCK.equals(xssContext)) {
			 builder.forJavaScriptBlock();
		} else if (XssParamContext.JS_ATTRIBUTE.equals(xssContext)) {
			builder.forJavaScriptAttribute();
		} else if (XssParamContext.URI.equals(xssContext)) {
			builder.forUriComponent();
		} else if (XssParamContext.HTML_CONTENT.equals(xssContext)) {
			builder.forHtmlContent();
		}
	}
}