package utils;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 *
 * @author felipe-schmidt
 */
public class Product {
   
    private String id;
    private String name;
    private String price;
   
    public Product() {}
    
    public Product(String id, String name, String price) {
        setId(id);
        setName(name);
        setPrice(price);
    }    

    public final void setId(String id) {
        this.id = id;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final void setPrice(String price) {
        // Change comma to dots to support any currency format
        price = price.replace(',', '.');
        BigDecimal tmpPrice = new BigDecimal(price);        
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        this.price = nf.format(tmpPrice);
    }
    
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
    
    @Override
    public boolean equals(Object o) {
        Product p = (Product) o;
        if (p.getId().equals(this.id)
            && p.getName().equals(this.name)
            && p.getPrice().equals(this.price))
        {
            return true;
        }        
        return false;
    }
    
    public String toString() {
        return "ID: \"" + id + "\" NAME: \"" + name + "\" PRICE: \"" + price + "\"";
    }
}
