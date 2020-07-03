/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.stax;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.api.ErrorListener;
import com.sun.tools.xjc.api.S2JJAXBModel;
import com.sun.tools.xjc.api.SchemaCompiler;
import com.sun.tools.xjc.api.XJC;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Admin
 */
public class XJCGenerateJavaObj {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            // TODO code application logic here
            String output = "src/java";
            SchemaCompiler sc = XJC.createSchemaCompiler();
            sc.setErrorListener(new ErrorListener() {
                @Override
                public void error(SAXParseException saxpe) {
                    System.out.println("error" + saxpe.getMessage());
                }
                
                @Override
                public void fatalError(SAXParseException saxpe) {
                    System.out.println("error" + saxpe.getMessage());
                }
                
                @Override
                public void warning(SAXParseException saxpe) {
                    System.out.println("error" + saxpe.getMessage());
                }
                
                @Override
                public void info(SAXParseException saxpe) {
                    System.out.println("error" + saxpe.getMessage());
                }
            });
            sc.forcePackageName("hieubd.products");
            File schema = new File("src/java/hieubd/xml/product.xsd");
            InputSource is = new InputSource(schema.toURI().toString());
            sc.parseSchema(is);
            S2JJAXBModel model = sc.bind();
            JCodeModel code = model.generateCode(null, null);
            code.build(new File(output));
            System.out.println("Finished");
        } catch (IOException ex) {
            Logger.getLogger(XJCGenerateJavaObj.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
