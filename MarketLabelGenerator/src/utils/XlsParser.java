package utils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * This class parses the XLS table containing the list of products and their
 * prices.
 * 
 * @author felipe-schmidt
 */
public class XlsParser {

    private String id;
    private String name;
    private String price;
    private int idIdx;
    private int nameIdx;
    private int priceIdx;
    File productsFile;    
    
    /**
     * Initialize parser configuring it to correctly select the necessary
     * data in each row of a product entry
     * 
     * @param columnProductId name of the product ID header
     * @param columnProductName name of product name header
     * @param columnProductPrice name of product price header
     * @param xlsFilePath path of xls file containing the product entries
     */
    public XlsParser(
            String columnProductId,
            String columnProductName,
            String columnProductPrice,
            String xlsFilePath)
    {
        this.id    = columnProductId;
        this.name  = columnProductName;
        this.price = columnProductPrice;
        
        productsFile = new File(xlsFilePath);
        if (!productsFile.exists()) {
            System.err.println(
                    "Input xls file does not exists!"
                    + productsFile.getAbsolutePath()
            );
        }
    }
    
    public List<Product> parse() {
        List<Product> productsList = new LinkedList<>();
        
        return productsList;
    }
}
