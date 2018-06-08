package utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

/**
 * This generates a doc with labels containing product name, price and its
 * ID based on a given model.
 * 
 * @author felipe-schmidt
 */
public class LabelsGenerator {
    
    private String outputFilePath;
    private List<Product> productList;    
    private WordprocessingMLPackage modelFile;
    
    public LabelsGenerator(
            String modelFilePath,
            String outputFilePath,
            List<Product> productList)
    {
        try {
            File f = new File(modelFilePath);
            FileInputStream inputStream = new FileInputStream(f);
            modelFile = WordprocessingMLPackage.load(inputStream);            
        } catch (Exception e) {
            System.err.println("Error while reading input model file: " + e);
        }
        this.outputFilePath = outputFilePath;
        this.productList = productList;
    }
    
    public void generate() {
        
        System.out.println("TRY PRINTING!!!");
        for (Product p : productList) {
            System.out.println(p);
        }
    }
}
