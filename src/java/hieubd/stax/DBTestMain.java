/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.stax;

import hieubd.dao.ProductTest;

/**
 *
 * @author Admin
 */
public class DBTestMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            String filePath = "src/java/hieubd/xml/product.xml";
            ProductTest test = new ProductTest();
            test.insertAll(filePath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        /*Product product = new Product();
        product.setBrand("aa");
        product.setName("A");
        product.setCategory("bb");
        product.setPrice("$1");
        product.setType("type");
        product.setImgSource("img");
        System.out.println(product.toString());
        ProductClient client = new ProductClient();
        client.create_XML(product);*/
    }  
}
