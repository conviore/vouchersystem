package com.tiger.utilities;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;



public class XmlUtil {
	public static final String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

	/**
	 * 拿到一个document对象
	 * 
	 * @param xmlStr
	 *            : <req>...</req>
	 * @param codeen
	 *            UTF-8
	 * @return
	 * @throws DocumentException
	 */
	public static Document getDocument(String xmlStr, String codeen)
			throws DocumentException {
		if (null == xmlStr || "".equals(xmlStr))
			return null;
		ByteArrayInputStream contents;
		try {
			contents = new ByteArrayInputStream(xmlStr.getBytes(codeen));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		SAXReader reader = new SAXReader();
		reader.setEntityResolver(new EntityResolver() {
			String emptyDtd = "";
			ByteArrayInputStream bytels = new ByteArrayInputStream(emptyDtd.getBytes());

			public InputSource resolveEntity(String publicId, String systemId)
					throws SAXException, IOException {
				return new InputSource(bytels);
			}
		});

		return reader.read(contents);
	}

	/**
	 * 重载获取document方法
	 * 
	 * @param xmlStr
	 * @return
	 * @throws DocumentException
	 */
	public static Document getDocument(String xmlStr) throws DocumentException {
		return getDocument(xmlStr, "UTF-8");

	}

	/**
	 * 拿到一个document对象
	 * 
	 * @param contents
	 *            fileIO
	 * @return
	 * @throws DocumentException
	 */
	public static Document getDocument(InputStream contents)
			throws DocumentException {
		if (contents == null)
			return null;
		SAXReader reader = new SAXReader();
		reader.setEntityResolver(new EntityResolver() {
			String emptyDtd = "";
			ByteArrayInputStream bytels = new ByteArrayInputStream(emptyDtd
					.getBytes());

			public InputSource resolveEntity(String publicId, String systemId)
					throws SAXException, IOException {
				return new InputSource(bytels);
			}
		});

		return reader.read(contents);
	}

	/**
	 * Map headParameter=XmlUtils.getParameter(document, "/req/reqHead/*");
	 * @param document
	 * @param nodeName
	 * @return
	 */
	public static Map getParameter(Document document, String nodeName) {
		if (document == null)
			return null;
		Map map = new HashMap();
		
		List<Element> elements = document.selectNodes(nodeName);
		if (elements.size() <= 0)
			return null;

		for (int i = 0; i < elements.size(); i++) {
			Element element = elements.get(i);
			map.put(element.getName(), element.getText().trim());
		}
		return map;
	}
	
	/**
	 * 获得对应循环节点的所有值
	 * @param document
	 * @param nodeName
	 * @return
	 */
	public static String[] getLoopNodeParameter(Document document, String nodeName) {
		if (document == null)
			return null;
		List<Element> elements = document.selectNodes(nodeName);
		if (elements.size() <= 0)
			return null;
        String[] values = new String[elements.size()];
		for (int i = 0; i < elements.size(); i++) {
			Element element = elements.get(i);
			if(element.getText()!=null && !element.getText().trim().equals(""))
			     values[i]= element.getText().trim();
		}
		return values;
	}
	
	/**
	 * 获得根节点下的所有节点
	 * */
	public static Map getParameter(Document document) {
		if (document == null)
			return null;
		Map map = new HashMap();
		Element root = document.getRootElement();
		for(Iterator i = root.elementIterator();i.hasNext();){
			Element element = (Element)i.next();
			map.put(element.getName(), element.getText().trim());
		}
		
		return map;
	}
	
	public static String buildTag(String tag, String value){
		return "<" + tag + ">" + (value==null?"":value) + "</" + tag + ">";
	}
	
	public static String mapToXml(Map<String,String> map) {
		 StringBuffer rtnXml = new StringBuffer();
		 rtnXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		 Iterator<String> iter = map.keySet().iterator();
		 while(iter.hasNext()){
	        	String obj = iter.next();
	        	rtnXml.append(buildTag(obj,map.get(obj)));
	        }
		 return rtnXml.toString();
	 }
	
	/**
	 * 创建xml
	 * @param rootNode
	 * @return
	 */
	public static String createXML(Node rootNode){
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(rootNode.getName());
		addElement(rootNode,root);
		return document.asXML();
	}
	
	private static void addElement(Node node,Element element){
		if(element !=null && node!=null){
			List<Node> childNodes = node.getChildNodes();
			List<Node> attributeNodes = node.getNodeAttribute();
			if(childNodes!=null && childNodes.size()>0){
				for(Node childnode :childNodes){
					Element childElement = element.addElement(childnode.getName());
				    if(!StringUtil.isEmpty(childnode.getValue())){
				    	childElement.addText(childnode.getValue());	    	
				    }
				    if(childnode.getChildNodes()!=null &&childnode.getChildNodes().size()>0){
				    	addElement(childnode,childElement);
				    }
				}
			}
			if(attributeNodes!=null && attributeNodes.size()>0){
				for(Node attriNode :attributeNodes){
					element.addAttribute(attriNode.getName(), attriNode.getValue());
				}
			}
		}
	}
	
	
	
	public static void main(String[] a) throws DocumentException{
//		System.out.println(XmlUtil.buildTag("name", "value"));
		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><tranData><orderInfo><merchantNo>888888888</merchantNo><orderNo>1340681999668</orderNo><orderAmount>14900</orderAmount><orderTime>20120626113959</orderTime><orderNote></orderNote><orderUrl>http://172.16.1.135:8080/shop/recvPayResult_payAction</orderUrl></orderInfo></tranData>";
		Document doc = XmlUtil.getDocument(xmlStr);
		Element orderInfo = doc.getRootElement().element("orderInfo");
		System.out.println(orderInfo.elementText("orderNote"));
		System.out.println(orderInfo.elementText("orderNot"));
	}
}
