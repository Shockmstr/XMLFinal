/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.dao;

import hieubd.entity.Product;
import hieubd.stax.XMLUtils;
import hieubd.ws.client.ProductClient;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.modelmapper.ModelMapper;

/**
 *
 * @author Admin
 */
public class ProductTest implements Serializable{

    ModelMapper modelMapper = new ModelMapper();
    
    public void insertAll(String filePath) throws Exception{
        
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.FALSE);
        XMLEventReader eventReader = factory.createXMLEventReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
        XMLEvent event = null;
        List<Product> proList = new ArrayList<>();
        int count = 0;
        while (eventReader.hasNext()) {            
            event = eventReader.nextEvent();
            if (event.isStartElement()){
                StartElement ele = (StartElement) event;
                //System.out.println("st= " + ele.getName().toString());
                boolean hasProduct = false;
                Product product = new Product();
                /*switch(ele.getName().toString()){
                    case "brand":
                        product.setBrand(XMLUtils.getTextContent(eventReader, "brand"));
                        break;
                    case "name":
                        product.setName(XMLUtils.getTextContent(eventReader, "name"));
                        break;
                    case "price":
                        product.setPrice(XMLUtils.getTextContent(eventReader, "price"));
                        break;
                    case "category":
                        product.setCategory(XMLUtils.getTextContent(eventReader, "category"));
                        break;
                    case "type":
                        product.setType(XMLUtils.getTextContent(eventReader, "type"));
                        break;
                    case "imgSource":
                        product.setImgSource(XMLUtils.getTextContent(eventReader, "imgSource"));
                        break;
                    case "status":
                        product.setStatus(Integer.parseInt(XMLUtils.getTextContent(eventReader, "status")));
                        break;
                }*/
                if (ele.getName().toString().equals("product")){
                    product.setBrand(XMLUtils.getTextContent(eventReader, "brand"));
                    product.setName(XMLUtils.getTextContent(eventReader, "name"));
                    product.setPrice(XMLUtils.getTextContent(eventReader, "price"));
                    product.setCategory(XMLUtils.getTextContent(eventReader, "category"));
                    product.setType(XMLUtils.getTextContent(eventReader, "type"));
                    product.setImg(XMLUtils.getTextContent(eventReader, "img"));
                    product.setStatus(Integer.parseInt(XMLUtils.getTextContent(eventReader, "status")));                   
                    product.setId(1);
                    hasProduct = true;
                }
                
                if (hasProduct){
                    //proList.add(product);
                    //hieubd.entity.Product entity = modelMapper.map(product, hieubd.entity.Product.class);
                    //System.out.println(entity.toString());
                    ProductClient client = new ProductClient();
                    client.create_XML(product);
                    //System.out.println(client.countREST());
                   // count++;
                    //System.out.println(count);
                }
            }
        }
           
    }
}
