/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.stax;

import static hieubd.constants.URLConstants.*;
import hieubd.products.Product;
import hieubd.products.Products;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
/**
 *
 * @author Admin
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JAXBException, Exception {
        // TODO code application logic here
        String filepath = "test.html";
        Products products = new Products();
        List<Product> productList = products.getProduct();
        //String url = "http://www.kieutrongkhanh.net/";
        List<String> URLs = initURLs();
        StaxParser parser = new StaxParser();
        
        for (String URL : URLs) {
            Internet.parseHTML(filepath, URL);          
            boolean stillHasException = true;
            while (stillHasException) {            
                stillHasException = parser.parseWellFormed(filepath, stillHasException);
            }
            productList = parser.getElements(filepath, productList);
            //productList.forEach(p -> System.out.println(p));
            
        }
        
        List<String> URLs2nd = initURLs2nd();
        for (String url : URLs2nd) {
            Internet.parseHTML(filepath, url);
            boolean stillHasException = true;
            while (stillHasException) {            
                stillHasException = parser.parseWellFormed(filepath, stillHasException);
            }
            productList = parser.getElements2ndWeb(filepath, productList);
        }
        String XMLFilePath = "src/java/hieubd/xml/product.xml";
        XMLUtils.JAXBMarshalling(products, XMLFilePath);
        XMLUtils.validateXML(XMLFilePath);
        
    }
    
    private static List<String> initURLs(){
        List<String> URLs = new ArrayList<>();
        URLs.add(URL_TENTS + URL_ITEM_PER_PAGE_24);
        URLs.add(URL_STOVE + URL_ITEM_PER_PAGE_24);
        URLs.add(URL_MEDICAL_FIRSTAID + URL_ITEM_PER_PAGE_24);
        URLs.add(URL_FISHING_ROD_REEL + URL_ITEM_PER_PAGE_24);
        URLs.add(URL_FISHING_TACKLE + URL_ITEM_PER_PAGE_24);
        URLs.add(URL_FISHING_TACKLE_BOX + URL_ITEM_PER_PAGE_24);
        return URLs;
    }
    
    private static List<String> initURLs2nd(){
        List<String> URLs = new ArrayList<>();
        URLs.add(TREKKINN_CLIMBING_HARNESS);
        URLs.add(TREKKINN_CLIMBING_QUICKDRAW);
        URLs.add(TREKKINN_ROPE);       
        return URLs;
    }
}
