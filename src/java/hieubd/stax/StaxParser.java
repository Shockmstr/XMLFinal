/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.stax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Admin
 */
public class StaxParser {
    public boolean parseWellFormed(String filePath, boolean stillHasException){
        XMLInputFactory fact = XMLInputFactory.newInstance();
        fact.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
        fact.setProperty(XMLInputFactory.IS_VALIDATING, false);
        
        XMLEventReader reader = null;
        XMLEvent eve = null;
        try {
            reader = fact.createXMLEventReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            //boolean inProductTag = false;
            while (reader.hasNext()) {                
                eve = reader.nextEvent();
                //System.out.println(eve.toString());
                //if (eve.isStartElement()){
                   // StartElement ele = (StartElement) eve;
                    
                    //System.out.println(ele.getName());
                    /*if (ele.getName().toString().equals("div")){
                        Attribute attr = ele.getAttributeByName(new QName("class"));
                        if (attr != null){
                            if (attr.getValue().equals("date-outer")){
                                inProductTag = true;
                            }
                        }
                    }
                    if (ele.getName().toString().equals("img") && inProductTag){
                        Attribute sourceAtt = ele.getAttributeByName(new QName("src"));
                        detail = sourceAtt.getValue();
                    }
                    if (ele.getName().toString().equals("a") && inProductTag){
                        Attribute attr = ele.getAttributeByName(new QName("class"));
                        if (attr != null){
                            System.out.println(attr.getValue());
                            inProductTag = false;
                            Attribute titleAtt = ele.getAttributeByName(new QName("title"));
                            title = titleAtt.getValue();
                            MedicineItem med = new MedicineItem();
                            med.setId(new BigInteger((idGen++) + ""));
                            med.setCategory("currentCate");
                            med.setDetail(detail);
                            med.setName(title);
                            med.setProvider("HCM");
                            list.getMedicine().add(med);
                        }
                    }*/
                //}
            }
            stillHasException = false;
        } catch (XMLStreamException e) {
            stillHasException = true;
            FileReader fr = null;
            try {
                //e.printStackTrace();
                String msg = e.getMessage();
                if (msg.contains("terminated by the matching end-tag")){
                    String endTag = msg.substring(msg.lastIndexOf("<"), msg.length() - 2);
                    System.out.println(endTag);
                    //System.out.println(msg.substring(msg.lastIndexOf("<"), msg.length() - 2));
                    //ystem.out.println(eve.getLocation().getColumnNumber());

                    int row = eve.getLocation().getLineNumber();
                    int col = e.getLocation().getColumnNumber();

                    fr = new FileReader(filePath);
                    BufferedReader br = new BufferedReader(fr);                   
                    List<String> lines = br.lines().skip(row - 1).limit(1).collect(Collectors.toList());

                    StringBuilder fixedLine = new StringBuilder();
                    for (String line : lines) {                   
                        fixedLine.append(line);
                        fixedLine.insert(col - 3, endTag);
                        break;
                    }
                    br.close();
                    fr.close();

                    String ss;
                    int lineNum = 1;
                    StringBuilder streamLines = new StringBuilder();
                    fr = new FileReader(new File(filePath));
                    br = new BufferedReader(fr);
                    while ((ss = br.readLine()) != null){
                      //  if (!ss.isEmpty()){
                            if (lineNum == row){
                                streamLines.append(fixedLine.toString() + "\n");
                            }else{
                                streamLines.append(ss + "\n");
                            }
                            lineNum++;
                      //  }                      
                    }
                    //System.out.println(streamLines.toString());
                    FileWriter fw = new FileWriter(new File(filePath));
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(streamLines.toString());
                    bw.flush();
                    bw.close();
                    fw.close();
                }else if (msg.contains("must be followed by")){
                   // System.out.println("att-att");
                    int firstOccur = msg.indexOf("\"");
                    String error = msg.substring(firstOccur + 1, msg.indexOf("\"", firstOccur + 1));
                    //System.out.println(error);
                    int row = eve.getLocation().getLineNumber();
                    int col = e.getLocation().getColumnNumber();
                    //System.out.println(e.getLocation());
                    System.out.println(row + " " + col);
                    fr = new FileReader(filePath);
                    BufferedReader br = new BufferedReader(fr);                   
                    List<String> lines = br.lines().skip(row - 1).limit(1).collect(Collectors.toList());

                    StringBuilder fixedLine = new StringBuilder();
                    for (String line : lines) {                   
                        fixedLine.append(line);
                        fixedLine.insert(col - 1, "=\"\" ");
                        break;
                    }
                    br.close();
                    fr.close();

                    String ss;
                    int lineNum = 1;
                    StringBuilder streamLines = new StringBuilder();
                    fr = new FileReader(new File(filePath));
                    br = new BufferedReader(fr);
                    while ((ss = br.readLine()) != null){
                      //  if (!ss.isEmpty()){
                            if (lineNum == row){
                                streamLines.append(fixedLine.toString() + "\n");
                            }else{
                                streamLines.append(ss + "\n");
                            }
                            lineNum++;
                      //  }                      
                    }
                    //System.out.println(streamLines.toString());
                    FileWriter fw = new FileWriter(new File(filePath));
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(streamLines.toString());
                    bw.flush();
                    bw.close();
                    fw.close();
                }              
            } catch (FileNotFoundException ex) {
                Logger.getLogger(StaxParser.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(StaxParser.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fr.close();
                } catch (IOException ex) {
                    Logger.getLogger(StaxParser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }catch(Exception e){
            stillHasException = true;
            e.printStackTrace();
        }
        return stillHasException;
    }
    
    public void getElements(String filePath){
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = null;
        XMLEvent event = null;
        try {
            eventReader = factory.createXMLEventReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            while (eventReader.hasNext()){
                event = eventReader.nextEvent();
                if (event.isStartElement()){
                    StartElement ele = (StartElement) event;
                     //System.out.println(ele.getName().toString());
                    if (ele.getName().toString().equals("h1") && ele.getAttributeByName(new QName("class")).getValue().equals("breadCrumbs")){
                        boolean insideParentTag = true;
                        //String home = XMLUtils.getTextContent(eventReader, "a", insideParentTag);
                        eventReader.nextEvent();
                        String category = XMLUtils.getTextContent(eventReader, "a", insideParentTag);
                        String productType = XMLUtils.getTextContent(eventReader, "a", insideParentTag);
                        //System.out.println(home + "=" + category + "=" + productType);
                        System.out.println(category + "=" + productType);
                    }
                    
                    if (ele.getName().toString().equals("article") && ele.getAttributeByName(new QName("class")).getValue().equals("productlisting")){
                        String brand = XMLUtils.getTextContent(eventReader, "a", "class", "brand");
                        String name = XMLUtils.getTextContent(eventReader, "a", "class", "name");
                        String price = XMLUtils.getTextContent(eventReader, "strong", "class", "itemPrice");
                        System.out.println(brand + "-" + name +"-" + price);
                    }
                   
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*public void saveToXML(String xmlDataFilePath){
        try {
            JAXBContext ct = JAXBContext.newInstance(list.getClass());
            Marshaller ms = ct.createMarshaller();
            ms.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            ms.marshal(list, new File(xmlDataFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
