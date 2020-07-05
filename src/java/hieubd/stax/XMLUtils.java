/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.stax;

import hieubd.products.Products;
import java.io.File;
import java.io.Serializable;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

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
                        curEvent.nextEvent();
                        String result = curEvent.getElementText();
                        curEvent.nextTag();
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
    
    public static String getTextContent(XMLEventReader curEvent, String tagName, int tagPosition, String tagParent, String attrParent, String attrParentVal) throws Exception{
        if (curEvent != null){
            int curPos = 0;
            boolean insideParentTag = false;
            while (curEvent.hasNext()) {                
                XMLEvent event = curEvent.nextEvent();
                if (event.isStartElement()){
                    StartElement ele = (StartElement) event;
                    String name = ele.getName().toString();
                    if (name.equals(tagParent)){
                       // System.out.println("Tagpar");
                        Attribute parentAttr = ele.getAttributeByName(new QName(attrParent));
                        System.out.println(parentAttr + " " + parentAttr.getValue());
                        if (parentAttr.getValue().equals(attrParentVal)){
                           
                            insideParentTag = true;
                             System.out.println(insideParentTag);
                        }
                    }
                    
                    if (name.equals(tagName) && insideParentTag){
                        System.out.println("curpos=" + curPos);
                        //if (curPos == tagPosition){
                            // curEvent.nextEvent();
                            String result = curEvent.getElementText();
                            System.out.println(result);
                            //curEvent.nextTag();
                            return result;
                        //}                 
                        //curPos++;   
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
}
