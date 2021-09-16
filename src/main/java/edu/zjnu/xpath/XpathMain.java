package edu.zjnu.xpath;

import org.apache.ibatis.io.Resources;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: XpathMain
 * @author: 杨海波
 * @date: 2021-09-16
 **/
public class XpathMain {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, InstantiationException, IllegalAccessException {
        // 创建DocumentBuilderFactory对象
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        // 创建DocumentBuilder实例
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        InputStream inputStream = Resources.getResourceAsStream("xpath-user.xml");
        Document document = builder.parse(inputStream);
        // 获取xPath实例
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodeList = (NodeList) xPath.evaluate("/users/*", document, XPathConstants.NODESET);
        List<XpathUser> userList = new ArrayList<XpathUser>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            String path = "/users/user";
            String name = (String) xPath.evaluate(path + "/name", document, XPathConstants.STRING);
            Integer age = ((Double) xPath.evaluate(path + "/age", document, XPathConstants.NUMBER)).intValue();

            XpathUser user = builderEntity(XpathUser.class, name, age);

            userList.add(user);
        }

        for (XpathUser user : userList) {
            System.out.println(user.getName() + ":" + user.getAge());
        }
    }

    /**
     * 构建对象
     * @param xpathUserClass
     * @param name
     * @param age
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static XpathUser builderEntity(Class<XpathUser> xpathUserClass, String name, Integer age) throws IllegalAccessException, InstantiationException {
        XpathUser user = xpathUserClass.newInstance();
        user.setAge(age);
        user.setName(name);

        return user;
    }
}
