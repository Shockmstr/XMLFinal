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
    public static final String URL_TENTS = "https://www.theoutdoorworld.com/camping-hiking-backpacking-tents";
    public static final String URL_STOVE = "https://www.theoutdoorworld.com/camping-hiking-backpacking-stoves";
    public static final String URL_MEDICAL_FIRSTAID = "https://www.theoutdoorworld.com/camping-hiking-backpacking-medical-first-aid";
    public static final String URL_FISHING_ROD_REEL = "https://www.theoutdoorworld.com/fishing-rod-reel-combos";
    public static final String URL_FISHING_TACKLE = "https://www.theoutdoorworld.com/fishing-tackle";
    public static final String URL_FISHING_TACKLE_BOX = "https://www.theoutdoorworld.com/fishing-tackle-storage-boxes";
    public static final String ITEM_PER_PAGE = "/browse/perpage";
    public static final String NEXT_PAGE = "/browse/page";
    public static final String URL_ITEM_PER_PAGE_24 = ITEM_PER_PAGE + "/24";
    public static final String URL_ITEM_PER_PAGE_48 = ITEM_PER_PAGE + "/48";
    public static final String URL_ITEM_PER_PAGE_72 = ITEM_PER_PAGE + "/72";
    public static final String URL_ITEM_PER_PAGE_96 = ITEM_PER_PAGE + "/96";
    
    public static final String TREKKINN_CLIMBING_HARNESS = "https://www.trekkinn.com/outdoor-mountain/climbing-equipment-harnesses/14513/s";
    public static final String TREKKINN_CLIMBING_QUICKDRAW = "https://www.trekkinn.com/outdoor-mountain/climbing-equipment-quickdraws/14517/s";
    public static final String TREKKINN_ROPE = "https://www.trekkinn.com/outdoor-mountain/climbing-equipment-ropes---webbing/14514/s";
    
    public static String urlOnPage(String url, int pageNum){
        return url + NEXT_PAGE + "/" + String.valueOf(pageNum);
    }
}