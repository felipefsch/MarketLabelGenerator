package utils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import org.apache.poi.ss.usermodel.*;

/**
 * This class parses the XLS table containing the list of products and their
 * prices, generating a valid list of products.
 * 
 * @author felipe-schmidt
 */
public class XlsParser {

    private final String id;
    private final String name;
    private final String price;
    
    private int idIdx = 0;
    private int nameIdx = 0;
    private int priceIdx = 0;
    
    Workbook productsFile;
    
    // Default 6 digits product ID
    private String validRowProductIdRegex = "^[0-9]{1,6}$";
    
    /**
     * Initialize parser configuring it to correctly select the necessary
     * data in each row of a product entry
     * 
     * @param columnProductId name of the product ID header
     * @param columnProductName name of product name header
     * @param columnProductPrice name of product price header
     * @param xlsFilePath path of xls file containing the product entries
     * @throws java.io.FileNotFoundException
     */
    public XlsParser(
            String columnProductId,
            String columnProductName,
            String columnProductPrice,
            String xlsFilePath)
    throws Exception
    {
        this.id    = columnProductId.toUpperCase();
        this.name  = columnProductName.toUpperCase();
        this.price = columnProductPrice.toUpperCase();
        
        File f = new File(xlsFilePath);
        productsFile = WorkbookFactory.create(f);
    }    
    
    public void setValidRowProductIdRegex(String regex) {
        this.validRowProductIdRegex = regex;
    }
    
    private boolean hasRowIndexes() {
        return (idIdx != nameIdx)
                && (idIdx != priceIdx)
                && (nameIdx != priceIdx);
    }
    
    /**
     * Check if row contains headers and updates the cell indexes for each
     * required information.
     * 
     * @param row
     * @return
     */
    private void findRowIndexes(Row row) {        
        for(Cell cell : row) {
            DataFormatter formatter = new DataFormatter();
            String cellStr = formatter.formatCellValue(cell).toUpperCase();
            if (cellStr.equals(this.id)) {
                idIdx = cell.getColumnIndex();
            }
            else if (cellStr.equals(this.name)) {
                nameIdx = cell.getColumnIndex();
            }
            else if (cellStr.equals(this.price)) {
                priceIdx = cell.getColumnIndex();
            }
        }
    }
    
    private Product rowToProduct(Row row) {
        try {
            DataFormatter formatter = new DataFormatter();
            
            String productId    = formatter.formatCellValue(row.getCell(idIdx));
            String productName  = formatter.formatCellValue(row.getCell(nameIdx));
            String productPrice = formatter.formatCellValue(row.getCell(priceIdx));

            if (productId.matches(validRowProductIdRegex)
                && !productId.isEmpty()
                && !productName.isEmpty()
                && !productPrice.isEmpty())
            {
                return new Product(productId, productName, productPrice);
            }
            return null;
        } catch (Exception e) {        
            System.err.println("Error parsing row: " + row);
            return null;
        }
    }
    
    private List<Product> parseSheet(Sheet sheet) throws Exception {
        List<Product> productsList = new LinkedList<>();       
        
        for (Row row : sheet) {
            if (!hasRowIndexes()) {
                findRowIndexes(row);
            }
            else {
                Product p = rowToProduct(row);
                if (p != null) {
                    productsList.add(p);
                }
            }
        }
        
        return productsList;
    }
    
    public List<Product> parse() throws Exception {
        List<Product> productsList = new LinkedList<>();
        
        // Include products from all sheets
        for (Sheet sheet : productsFile) {
            productsList.addAll(parseSheet(sheet));
        }
        
        return productsList;
    }
}
