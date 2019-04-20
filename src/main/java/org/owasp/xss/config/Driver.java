package org.owasp.xss.config;

import org.owasp.encoder.Encode;

public class Driver {

	public static void main(String[] args) throws Exception {
		/*JAXBContext jaxbContext = JAXBContext.newInstance(XssPageConfigs.class);

		Marshaller jaxbmarshaller = jaxbContext.createMarshaller();
		
		StringWriter writer = new StringWriter();
		XssPageConfigs configs = buildConfigs();
		jaxbmarshaller.marshal(configs, writer);
		
		System.out.println(writer.toString());*/
		
		System.out.println( Encode.forHtmlAttribute("Nadeem's"));
	}

	private static XssPageConfigs buildConfigs() {
		XssPageConfigs configs = new XssPageConfigs();
		configs.addConfig(newPageConfig("Page1"));
		configs.addConfig(newPageConfig("Page2"));
		return configs;
	}

	private static XssPageConfig newPageConfig(String page) {
		XssPageConfig config = new XssPageConfig();
		config.setPage(page);
		config.addParamConfig(newParamConfig());
		return config;
	}

	private static XssParamConfig newParamConfig() {
		XssParamConfig config = new XssParamConfig();
		config.setContext(XssParamContext.BASIC_HTML);
		config.setParam("xyz");
		return config;
	}

}
