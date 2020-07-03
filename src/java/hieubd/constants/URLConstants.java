/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.constants;

/**
 *
 * @author Admin
 */
public class URLConstants {
    public static final String URL = "https://www.theoutdoorworld.com/camping-hiking-backpacking-sleeping-bags";
    public static final String ITEM_PER_PAGE = "/browse/perpage";
    public static final String NEXT_PAGE = "/browse/page";
    public static final String URL_ITEM_PER_PAGE_24 = URL + ITEM_PER_PAGE + "/24";
    public static final String URL_ITEM_PER_PAGE_48 = URL + ITEM_PER_PAGE + "/48";
    public static final String URL_ITEM_PER_PAGE_72 = URL + ITEM_PER_PAGE + "/72";
    public static final String URL_ITEM_PER_PAGE_96 = URL + ITEM_PER_PAGE + "/96";
    
    public static String urlOnPage(int pageNum){
        return URL + NEXT_PAGE + "/" + String.valueOf(pageNum);
    }
}