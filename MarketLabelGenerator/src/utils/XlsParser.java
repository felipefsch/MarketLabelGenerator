package utils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import org.apache.poi.ss.usermodel.*;

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
    private int idIdx = 0;
    private int nameIdx = 0;
    private int priceIdx = 0;
    Workbook productsFile;
    
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
            String cellStr = cell.getStringCellValue().toUpperCase();
            System.out.println(cellStr);
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
        return new Product(
                row.getCell(idIdx).toString(),
                row.getCell(nameIdx).toString(),
                row.getCell(priceIdx).toString()
        );
    }
    
    private List<Product> parseSheet(Sheet sheet) throws Exception {
        List<Product> productsList = new LinkedList<>();       
        
        for (Row row : sheet) {
            if (!hasRowIndexes()) {
                findRowIndexes(row);
            }
            else {
                productsList.add(rowToProduct(row));
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
