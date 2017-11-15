package baseFramework.xml;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.Map;

/**
 * @Description XML解析器, 注意:一个解析器只能绑定一个XML文件.
 * @Usage <pre>
 * // 获取解析器
 * XMLParser xmlParser = new XMLParser(xmlPath);
 * <p>
 * // 解析XML文件
 * // 解析完成后将通过 {@link XMLObject} 节点对象获取属性和标签体
 * XMLObject root = xmlParser.parse();
 * </pre>
 * @see XMLObject
 */
public class XMLParser {
	private static Logger logger = LogManager.getLogger(XMLParser.class);

	/**
	 * @param tagName 标签名
	 * @param content 标签体
	 * @param attrs   属性列表
	 * @return XMLObject 新节点对象
	 * @Title: createNode
	 * @Description: 创建新标签
	 */
	public static XMLObject createNode(String tagName, String content, Map<String, String> attrs) {
		XMLObject newNode = new XMLObject(tagName, content, attrs);
		newNode.setParent(null);
		return newNode;
	}

	public static XMLObject parse(String content) throws DocumentException {
		SAXReader saxReader = new SAXReader();
		saxReader.setEncoding("UTF-8");
		InputSource source = new InputSource(new StringReader(content));
		Document document = saxReader.read(source);
		return parse(document);
	}

	public static XMLObject parse(File file) throws DocumentException, DocumentException {
		SAXReader saxReader = new SAXReader();
		saxReader.setEncoding("UTF-8");
		Document document = saxReader.read(file);
		return parse(document);
	}

	public static XMLObject parse(URL url) throws DocumentException {
		SAXReader saxReader = new SAXReader();
		saxReader.setEncoding("UTF-8");
		Document document = saxReader.read(url);
		return parse(document);
	}

	/**
	 * @return XMLObject XML对象
	 * @Title: parse
	 * @Description: 解析XML文件
	 * <p>
	 * <ul>
	 * <li>如果当前标签含有子标签时, 将不会获取当前标签的<i>存文本内容</i>, <span
	 * style="color:#F00">红色内容将会丢失</span></li>
	 * <li>当前解析器不解析注释内容</li>
	 * </ul>
	 * <p>
	 * <pre>
	 * &lt;root&gt;
	 *   tag content
	 *   &lt;tag&gt;
	 *     child tag content
	 *     &lt;sub-tag id="subTag"&gt;sub tag content&lt;/sub-tag&gt;
	 *   &lt;/tag&gt;
	 *   <span style="color:#F00">&lt;!-- &lt;desc&gt; 这个内容将不会解析 &lt;/desc&gt; --&gt;</span>
	 *   <span style="color:#F00">&lt;!-- 这个内容将也不会解析 --&gt;</span>
	 * &lt;/root&gt;
	 * </pre>
	 * <p>
	 * </P>
	 */
	private static XMLObject parse(Document document) {

		// 获取根节点名称
		Element rootElement = document.getRootElement();
		String tagName = rootElement.getName();

		// 构建 XMLObject 对象
		XMLObject xmlObject = new XMLObject(tagName);
		xmlObject.setParent(null);
		xmlObject.setRoot(Boolean.TRUE);

		// 解析XML
		parseNode(xmlObject, rootElement);
		return xmlObject;
	}

	/**
	 * @param xmlObject 上个节点对象
	 * @param node      节点元素
	 * @Title: parseNode
	 * @Description: 解析XML
	 */
	private static void parseNode(XMLObject xmlObject, Element node) {
		for (int i = 0, size = node.nodeCount(); i < size; i++) {
			Node subNode = node.node(i);
			XMLObject subXmlObject = null;
			if (subNode instanceof Element) {
				subXmlObject = appendSubTag(xmlObject, subNode);
				parseNode(subXmlObject, (Element) subNode);
				subXmlObject.setParent(xmlObject);
			}

			if (subNode instanceof Comment) {
				continue;
			}
		}

		setAttrsAndContent(xmlObject, node);

		// 根据策略去除重复标签
		// if (null == allowDuplicateSubTag || !allowDuplicateSubTag) {
		// filterDuplicateSubTags(xmlObject);
		// }
	}

	/**
	 * @param xmlObject XML节点映射对象
	 * @param node      XML节点
	 * @Title: setAttrsAndContent
	 * @Description: 设置属性和内容
	 */
	private static void setAttrsAndContent(XMLObject xmlObject, Element node) {
		if (null == xmlObject || null == node) {
			return;
		}

		if (xmlObject.getAttrs().isEmpty()) {
			setAttributes(xmlObject, node);
			setContent(xmlObject, node);
		}
	}

	/**
	 * @param xmlObject 当前节点对象
	 * @param subNode   子节点
	 * @return XMLObject 子节点映射对象
	 * @Title: appendSubTag
	 * @Description: 追加子标签
	 */
	private static XMLObject appendSubTag(XMLObject xmlObject, Node subNode) {
		// 获取子标签
		String subTagName = subNode.getName();
		XMLObject subXmlObject = new XMLObject(subTagName);

		// 添加子标签
		xmlObject.addChildTag(subXmlObject);
		return subXmlObject;
	}

	/**
	 * @param xmlObject XMLObject对象
	 * @param node      与XMLObject对象关联的节点
	 * @Title: setContent
	 * @Description: 设置标签值
	 */
	private static void setContent(XMLObject xmlObject, Element node) {
		String content = node.getTextTrim();
		xmlObject.setContent(content);
	}

	/**
	 * @param xmlObject XMLObject对象
	 * @param node      与XMLObject对象关联的节点
	 * @Title: setAttributes
	 * @Description: 设置属性
	 */
	private static void setAttributes(XMLObject xmlObject, Element node) {
		for (int i = 0, size = node.attributeCount(); i < size; i++) {
			// 获取属性
			Attribute attr = node.attribute(i);
			// 获取属性名
			String attrName = attr.getName();

			// // 验证是否重名属性
			// if (xmlObject.hasAttr(attrName) && isAttributeStrictest) {
			// throw new RuntimeException("Duplicate attribute[" + attrName +
			// "]");
			// }

			// 保存属性
			xmlObject.addAttr(attrName, attr.getValue());
		}
	}


	/**
	 * @param root       根元素
	 * @param outputFile 输出文件
	 * @param compact    true-紧凑排版, false-缩进排版
	 * @return boolean true-转换成功, false-转换失败
	 * @throws IOException
	 * @Title: transferRoot
	 * @Description: 转换为文件中, 必须从根节点开始
	 */
	public static boolean transferRoot(XMLObject root, File outputFile, boolean compact) throws IOException {
		// root校验
		if (null == root || !root.isRoot()) {
			logger.debug("The target node is invald root element");
			return false;
		}

		// 输出文件校验
		if (null == outputFile) {
			logger.debug("The target output file is invalid");
			return false;
		} else if (!outputFile.exists()) {
			logger.debug("The target output file is not exist, will create it");
			File parent = outputFile.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
			// outputFile.createNewFile();
		} else {
			logger.warn("The target output file is not empty, will clean it");
		}

		// 格式化输出
		// 创建格式化输出工具
		XMLObjectFormatter formatter = XMLObjectFormatterFactory.createFormatter(compact);

		// 执行格式化
		StringBuilder content = formatter.format(root);

		// 将格式化内容写入文件
		FileUtils.write(outputFile, content, false);

		return true;
	}

}
