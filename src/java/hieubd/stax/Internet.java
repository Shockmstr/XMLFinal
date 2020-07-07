/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.stax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Internet {
    public static void parseHTML(String filePath, String uri){
        Writer writer = null;
        try {
            boolean isBody = false;
            boolean isJavascript = true;
            URL url = new URL(uri);
            URLConnection yc = url.openConnection();
            yc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MTSE 6.0; Windows NT 5.0)");
            InputStream is = yc.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String inputLine;
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));
            inputLine = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
            writer.write(inputLine);
            while ((inputLine = in.readLine()) != null) {
                //inputLine = inputLine.replace("<!doctype html>", "");
                if (inputLine.contains("<body")){
                    inputLine = inputLine.substring(inputLine.indexOf("<body"));
                    isBody = true;                    
                }
                if (inputLine.contains("<script")){
                    isJavascript = true;
                }
                if (inputLine.contains("</script")){
                    inputLine = "";
                    isJavascript = false;
                }
                if (inputLine.contains("<input")){
                    inputLine = "";
                }
                if (isBody && !isJavascript){
                    inputLine = inputLine.replace("&", "&#38;")
                            .replace("<strong>Categories</strong></strong>", "<strong>Categories</strong>")
                            .replace("</select></label></div>", "</select></div>")
                            .replace("<!-- fin Menu Mobile-->", "<div>")
                            .replace("<div class=\"seo_titulo_pie\"> </div>", "<div class=\"seo_titulo_pie\">");/*.replace("async defer", "").replace("</body>", "</div></section></body>")*/
                            /*.replace("class=\"btn btn-primary\"", "")
                            .replace("xlink:", "")
                            .replace("height=16px", "height='16px'")
                            .replace("\"}", "\"");*/
                   // .replace("&#38;nbsp;", "")                     
                    //.replace("\t", "").replace("<br>", "").replace("</br>", "");
                    if (inputLine.contains("View More</a>")){
                        inputLine = inputLine + "</div>";
                    }
                    if (inputLine.contains("<div style=\"clear:both\"></div></div>")){
                        inputLine = inputLine.replace("<div style=\"clear:both\"></div></div>", "");
                    }
                    
                    writer.write(inputLine.trim() + "\n");
                    if (inputLine.contains("</body")){
                        break;
                    }
                }              
            }
            isBody = false;
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
