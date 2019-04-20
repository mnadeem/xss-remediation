package org.owasp.xss;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.owasp.xss.config.XssPageConfigs;

@WebFilter(filterName = "xssFilter", initParams = {
		@WebInitParam(name = "configFile", value = "/WEB-INF/xss-page-configs.xml") })
public class XssFilter implements Filter {
	
	private XssPageConfigs configs;

	private static final String CONFIG_PARAMETER_NAME = "configFile";

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Wrap the filter with the new filter.
		// Any requests to the HttpRequest or HttpResponse will go through the
		// wrapper.
		chain.doFilter(new XSSRequestWrapper(configs, (HttpServletRequest) request), response);

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String fileName = config.getInitParameter(CONFIG_PARAMETER_NAME);

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(XssPageConfigs.class);
			final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			InputStream is = config.getServletContext().getResourceAsStream(fileName);
			configs = (XssPageConfigs) jaxbUnmarshaller.unmarshal(is);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
