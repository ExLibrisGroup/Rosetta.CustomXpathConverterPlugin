package com.exlibris.dps.repository.plugin.registry;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.exlibris.core.infra.common.exceptions.logging.ExLogger;
import com.exlibris.dps.sdk.registry.ConverterRegistryPlugin;

/**
 * @author Motip
 *
 */

public class XpathConverterPlugin implements ConverterRegistryPlugin {
	
	private static ExLogger log = ExLogger.getExLogger(XpathConverterPlugin.class);
	private static final String XPATH = "Xpath";
	private String xpathStr;
	@Override
	public String convert(String ieXml) {
	
		try {
			return xpathConverter(ieXml);
		} catch (XPathExpressionException | ParserConfigurationException
				| SAXException | IOException e) {
			log.error("convert of IE using Xpath failed", e);
		}
		return null;
	}

	@Override
	public void initParam(Map<String, String> params) {
		this.xpathStr = params.get(XPATH);
		
	}

	@Override
	public String unPublish(String ieXml) {
		try {
			return xpathConverter(ieXml);
		} catch (XPathExpressionException | ParserConfigurationException
				| SAXException | IOException e) {
			log.error("convert of IE using Xpath failed", e);
		}
		return null;
	}
	
	private String xpathConverter(String ieXml) throws ParserConfigurationException, XPathExpressionException, SAXException, IOException{
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false);
		DocumentBuilder builder = factory.newDocumentBuilder();
		StringReader reader = new StringReader(ieXml);
		InputSource source = new InputSource(reader);
		org.w3c.dom.Document doc = builder.parse(source);
		XPathFactory xPathfactory = XPathFactory.newInstance(); 
		javax.xml.xpath.XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile(xpathStr);
		return expr.evaluate(doc);	
	}

}

