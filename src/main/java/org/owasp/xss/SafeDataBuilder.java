package org.owasp.xss;

import javax.servlet.http.HttpServletRequest;

import org.owasp.encoder.Encode;

public class SafeDataBuilder {

	private String paramVal = null;

	private SafeDataBuilder(HttpServletRequest request, String paramName) {
		String val = getValue(request, paramName);
		this.paramVal = cleanValue(val);
	}

	private String cleanValue(String val) {
		String result = val; 
		if (val != null && val.length() > 16000) {
			result = val.substring(0, 16000);
		}
		return result;
	}

	private String getValue(HttpServletRequest request, String paramName) {
		String val = request.getParameter(paramName);

		if (val == null) {
			Object attr = request.getAttribute(paramName);
			if (attr instanceof String) {
				val = (String) attr;
			}
			if (attr instanceof Boolean) {
				val = attr.toString();
			}
		}
		return val;
	}

	public static SafeDataBuilder requestParam(HttpServletRequest request, String paramName) {
		return new SafeDataBuilder(request, paramName);
	}

	public SafeDataBuilder defaultValue(String val) {
		if (this.paramVal == null) {
			this.paramVal = val;
		}
		return this;
	}
	
	public SafeDataBuilder trim() {
		if (this.paramVal != null) {
			this.paramVal = paramVal.trim();
		}
		return this;
	}

	public SafeDataBuilder forHtml() {
		if (this.paramVal != null) {
			this.paramVal = Encode.forHtml(this.paramVal);
		}
		return this;
	}

	public SafeDataBuilder forHtmlContent() {
		if (this.paramVal != null) {
			this.paramVal = Encode.forHtml(this.paramVal);
		}
		return this;
	}

	public SafeDataBuilder forHtmlAttribute() {
		if (this.paramVal != null) {
			this.paramVal = Encode.forHtmlAttribute(this.paramVal);
		}
		return this;
	}

	public SafeDataBuilder forJavaScript() {
		if (this.paramVal != null) {
			this.paramVal = Encode.forJavaScript(this.paramVal);
		}
		return this;
	}

	public SafeDataBuilder forJavaScriptAttribute() {
		if (this.paramVal != null) {
			this.paramVal = Encode.forJavaScriptAttribute(this.paramVal);
		}
		return this;
	}

	public SafeDataBuilder forFormActionAttribute() {
		if (this.paramVal != null) {			
			this.paramVal = Encode.forHtmlAttribute(cleanJsAttribute(this.paramVal));
		}
		return this;
	}

	private String cleanJsAttribute(String action) {
		String result = action;
		String cleansed = action.trim().toLowerCase();
		if (cleansed.startsWith("javascript")) {
			result = cleansed.substring(10);
		}
		return result;
	}

	public SafeDataBuilder forJavaScriptBlock() {
		if (this.paramVal != null) {
			this.paramVal = Encode.forJavaScriptBlock(this.paramVal);
		}
		return this;
	}

	public SafeDataBuilder forJavaScriptSource() {
		if (this.paramVal != null) {
			this.paramVal = Encode.forJavaScriptSource(this.paramVal);
		}
		return this;
	}

	public SafeDataBuilder forCssString() {
		if (this.paramVal != null) {
			this.paramVal = Encode.forCssString(this.paramVal);
		}
		return this;
	}

	public SafeDataBuilder forCssUrl() {
		if (this.paramVal != null) {
			this.paramVal = Encode.forCssUrl(this.paramVal);
		}
		return this;
	}

	public SafeDataBuilder forUriComponent() {
		if (this.paramVal != null) {
			this.paramVal = Encode.forUriComponent(this.paramVal);
		}
		return this;
	}

	public String get() {
		return this.paramVal;
	}

	public String getQuoted() {
		return "'" + this.paramVal + "'";
	}
}