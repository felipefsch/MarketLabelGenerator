package utils;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author felipe-schmidt
 */
public class XlsParserTest {
    
    private static List<Product> expResult;
    XlsParser defaultParser;
    
    public XlsParserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        expResult = getExpectedResultDefault();
        String testFilePath = "test\\docs\\testDefault.xls";
        defaultParser = new XlsParser("ID", "NAME", "PRICE", testFilePath);
    }
    
    @After
    public void tearDown() {
    }
    
    private static List<Product> getExpectedResultDefault() {
        List<Product> productList = new LinkedList<>();
        productList.add(new Product("001", "Product 1", BigDecimal.valueOf(1.15)));
        productList.add(new Product("002", "Product 2", BigDecimal.valueOf(2.30)));
        productList.add(new Product("003", "Product 3", BigDecimal.valueOf(15.20)));        
        return productList;
    }
    
    @Test
    public void testParseDefault() throws Exception {
        String testFilePath = "test\\docs\\testDefault.xls";
        XlsParser instance = new XlsParser("ID", "NAME", "PRICE", testFilePath);
        List<Product> result = instance.parse();
        assertArrayEquals(expResult.toArray(), result.toArray());
    }
    
    @Test
    public void testParseComplex() throws Exception {
        String testFilePath = "test\\docs\\testComplex.xls";
        XlsParser instance = new XlsParser("ID", "NAME", "PRICE", testFilePath);
        List<Product> result = instance.parse();
        assertArrayEquals(expResult.toArray(), result.toArray());
    }    
    
    @Test
    public void testParseNonExistingFile() {
        String nonExistingFilePath = "test\\docs\\nonExistingFile.xls";
        try {
            XlsParser instance = new XlsParser("ID", "NAME", "PRICE", nonExistingFilePath);
            fail("Should throw Exception");
        } catch (Exception e) {}
    } 
    
    private void setParserRowIndexes(
            int idIdx,
            int nameIdx,
            int priceIdx)
    throws Exception {
        Field fieldIdIdx    = XlsParser.class.getDeclaredField("idIdx");
        Field fieldNameIdx  = XlsParser.class.getDeclaredField("nameIdx");
        Field fieldPriceIdx = XlsParser.class.getDeclaredField("priceIdx");
        
        fieldIdIdx.setAccessible(true);
        fieldNameIdx.setAccessible(true);
        fieldPriceIdx.setAccessible(true);
        
        fieldIdIdx.set(defaultParser, idIdx);
        fieldNameIdx.set(defaultParser, nameIdx);
        fieldPriceIdx.set(defaultParser, priceIdx);        
    }
    
    @Test
    public void testHasRowIndexesTrue() throws Exception {
        setParserRowIndexes(0, 1, 2);
        Method method = XlsParser.class.getDeclaredMethod("hasRowIndexes", null);
        method.setAccessible(true);
        assertEquals(method.invoke(defaultParser, null), true);
    }
    
    @Test
    public void testHasRowIndexesFalse() throws Exception {
        setParserRowIndexes(0, 0, 0);
        Method method = XlsParser.class.getDeclaredMethod("hasRowIndexes", null);
        method.setAccessible(true);
        assertEquals(method.invoke(defaultParser, null), false);       
    }

    private void testFindRowIndexesSameRow(
            int idIdx,
            int nameIdx,
            int priceIdx)
    throws Exception {
        // Mock input file
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("sheet1");
        Row row = sheet.createRow(0);
        row.createCell(idIdx).setCellValue("ID");
        row.createCell(nameIdx).setCellValue("NAME");
        row.createCell(priceIdx).setCellValue("PRICE"); 
        
        Field productFile = XlsParser.class.getDeclaredField("productsFile");
        productFile.setAccessible(true);
        productFile.set(defaultParser, workbook);
       
        Field fieldIdIdx    = XlsParser.class.getDeclaredField("idIdx");
        Field fieldNameIdx  = XlsParser.class.getDeclaredField("nameIdx");
        Field fieldPriceIdx = XlsParser.class.getDeclaredField("priceIdx");        
        fieldIdIdx.setAccessible(true);
        fieldNameIdx.setAccessible(true);
        fieldPriceIdx.setAccessible(true);
        
        defaultParser.parse();
        
        assertEquals(idIdx, fieldIdIdx.get(defaultParser));
        assertEquals(nameIdx, fieldNameIdx.get(defaultParser));
        assertEquals(priceIdx, fieldPriceIdx.get(defaultParser)); 
    }
    
    @Test
    public void testFindRowIndexesDense() throws Exception {
        testFindRowIndexesSameRow(0,1,2);
    }
    
    @Test
    public void testFindRowIndexesSparseSameRow() throws Exception {
        testFindRowIndexesSameRow(4,6,10);
    }    
    
    @Test
    public void testFindRowIndexesSparseDifferentRows() throws Exception {
        // Mock input file
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("sheet1");
        Row row1 = sheet.createRow(0);
        Row row2 = sheet.createRow(4);
        Row row3 = sheet.createRow(9);
        row1.createCell(0).setCellValue("ID");
        row2.createCell(5).setCellValue("NAME");
        row3.createCell(7).setCellValue("PRICE"); 
        
        Field productFile = XlsParser.class.getDeclaredField("productsFile");
        productFile.setAccessible(true);
        productFile.set(defaultParser, workbook);
       
        Field fieldIdIdx    = XlsParser.class.getDeclaredField("idIdx");
        Field fieldNameIdx  = XlsParser.class.getDeclaredField("nameIdx");
        Field fieldPriceIdx = XlsParser.class.getDeclaredField("priceIdx");        
        fieldIdIdx.setAccessible(true);
        fieldNameIdx.setAccessible(true);
        fieldPriceIdx.setAccessible(true);
        
        defaultParser.parse();
        
        assertEquals(0, fieldIdIdx.get(defaultParser));
        assertEquals(5, fieldNameIdx.get(defaultParser));
        assertEquals(7,fieldPriceIdx.get(defaultParser));
    }    

}