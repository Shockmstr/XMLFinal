/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.stax;

import hieubd.products.Products;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.util.StringTokenizer;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.InputSource;

/**
 *
 * @author Admin
 */
public class XMLUtils implements Serializable{
    public static String getTextContent(XMLEventReader curEvent, String tagName) throws Exception{
        if (curEvent != null){
            while (curEvent.hasNext()) {                
                XMLEvent event = curEvent.nextEvent();
                if (event.isStartElement()){
                    StartElement ele = (StartElement) event;
                    String name = ele.getName().toString();
                    if (name.equals(tagName)){
                        //curEvent.nextEvent();
                        String result = curEvent.getElementText();
                        //curEvent.nextTag();
                        return result;
                    }
                }
            }
        }
        return null;
    }
    
    public static String getTextContent(XMLEventReader curEvent, String tagName, String attributeName, String attributeValue) throws Exception{
        if (curEvent != null){
            while (curEvent.hasNext()) {                
                XMLEvent event = curEvent.nextEvent();
                if (event.isStartElement()){
                    StartElement ele = (StartElement) event;
                    String name = ele.getName().toString();
                    Attribute attr = ele.getAttributeByName(new QName(attributeName));
                    if (attr != null){
                        if (name.equals(tagName) && attr.getValue().equals(attributeValue)){
                       // curEvent.nextEvent();
                        String result = curEvent.getElementText();
                        //curEvent.nextTag();
                        return result;
                        }
                    }             
                }
            }
        }
        return null;
    }
    
    public static String getTextContentContains(XMLEventReader curEvent, String tagName, String attributeName, String attributeValue) throws Exception{
        if (curEvent != null){
            while (curEvent.hasNext()) {                
                XMLEvent event = curEvent.nextEvent();
                if (event.isStartElement()){
                    StartElement ele = (StartElement) event;
                    String name = ele.getName().toString();
                    Attribute attr = ele.getAttributeByName(new QName(attributeName));
                    if (attr != null){
                        if (name.equals(tagName) && attr.getValue().contains(attributeValue)){
                       // curEvent.nextEvent();
                        String result = curEvent.getElementText();
                        //curEvent.nextTag();
                        return result;
                        }
                    }             
                }
            }
        }
        return null;
    }
    
    public static String getTextContentNextTag(XMLEventReader curEvent, String tagName, String attributeName, String attributeValue) throws Exception{
        if (curEvent != null){
            while (curEvent.hasNext()) {                
                XMLEvent event = curEvent.nextEvent();
                if (event.isStartElement()){
                    StartElement ele = (StartElement) event;
                    String name = ele.getName().toString();
                    Attribute attr = ele.getAttributeByName(new QName(attributeName));
                    if (attr != null){
                        if (name.equals(tagName) && attr.getValue().equals(attributeValue)){
                        curEvent.nextEvent();
                        String result = curEvent.getElementText();
                        //curEvent.nextTag();
                        return result;
                        }
                    }             
                }
            }
        }
        return null;
    }
    
    public static String getTextContent(XMLEventReader curEvent, String tagName, boolean insideParentTag) throws Exception{
        if (curEvent != null){
            int curPos = 0;
            while (curEvent.hasNext()) {                
                XMLEvent event = curEvent.nextEvent();
                if (event.isStartElement()){
                    StartElement ele = (StartElement) event;
                    String name = ele.getName().toString();
                   
                    
                    if (name.equals(tagName) && insideParentTag){
                            // curEvent.nextEvent();
                            String result = curEvent.getElementText();
                            //System.out.println(result);
                            //curEvent.nextTag();
                            return result; 
                    }                             
                }
            }
        }
        return null;
    }

    public static String getAttributeValue(XMLEventReader curEvent, String tagName, String attributeName) throws Exception{
        if (curEvent != null){
            while (curEvent.hasNext()) {                
                XMLEvent event = curEvent.nextEvent();
                if (event.isStartElement()){
                    StartElement ele = (StartElement) event;
                    String name = ele.getName().toString();
                    Attribute attr = ele.getAttributeByName(new QName(attributeName));
                    if (attr != null){
                        if (name.equals(tagName)){
                       // curEvent.nextEvent();
                        String result = attr.getValue();
                        //curEvent.nextTag();
                        return result;
                        }
                    }             
                }
            }
        }
        return null;
    }
    
    public static void JAXBMarshalling(Products productList, String XmlFilePath) throws JAXBException{
        JAXBContext ctx = JAXBContext.newInstance(productList.getClass());
        Marshaller mar = ctx.createMarshaller();
        mar.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        mar.marshal(productList, new File(XmlFilePath));
    }
    
    public static void validateXML(String xmlFilePath) throws Exception{
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File("src/java/hieubd/xml/product.xsd"));
        Validator validator = schema.newValidator();
        InputSource inputFile = new InputSource(new BufferedReader(new FileReader(xmlFilePath)));
        validator.validate(new SAXSource(inputFile));
    }
    
    public static Double getThePrice(String price) throws Exception{
        StringTokenizer stk = new StringTokenizer(price, "$");
        while (stk.hasMoreTokens()) {            
            String newPrice = stk.nextToken();
            //System.out.println(newPrice);
            return Double.parseDouble(newPrice);
        }
        return 0.0;
    }
}
