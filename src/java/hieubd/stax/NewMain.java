/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.stax;

/**
 *
 * @author Admin
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String filepath = "test.html";
        //String url = "http://www.kieutrongkhanh.net/";
        //Internet.parseHTML(filepath, URLConstants.URL_ITEM_PER_PAGE_72);
        StaxParser parser = new StaxParser();
        boolean stillHasException = true;
        while (stillHasException) {            
            stillHasException = parser.parseWellFormed(filepath, stillHasException);
        }
        parser.getElements(filepath);
        
        //parser.saveToXML("test.xml");
    }
    
}
