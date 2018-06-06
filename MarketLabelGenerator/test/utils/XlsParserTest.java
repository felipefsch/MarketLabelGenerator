package utils;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author felipe-schmidt
 */
public class XlsParserTest {
    
    public XlsParserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    private List<Product> getExpectedResultDefault() {
        List<Product> expResult = new LinkedList<>();
        expResult.add(new Product("001", "Product 1", BigDecimal.valueOf(1.15)));
        expResult.add(new Product("002", "Product 2", BigDecimal.valueOf(2.30)));
        expResult.add(new Product("003", "Product 3", BigDecimal.valueOf(15.20)));        
        return expResult;
    }
    
    @Test
    public void testParseDefult() {
        System.out.println("parse");
        String testFilePath = "test\\docs\\testDefault.xls";
        XlsParser instance = new XlsParser("ID", "NAME", "PRICE", testFilePath);
        List<Product> expResult = getExpectedResultDefault();
        List<Product> result = instance.parse();
        assertEquals(expResult, result);

        //fail("The test case is a prototype.");
    }
    
}
