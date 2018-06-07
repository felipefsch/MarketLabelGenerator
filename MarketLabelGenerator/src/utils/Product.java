package utils;

import java.math.BigDecimal;

/**
 *
 * @author felipe-schmidt
 */
public class Product {
   
    private String id;
    private String name;
    private BigDecimal price;
   
    public Product() {}
    
    public Product(String id, String name, BigDecimal price) {
        this.id    = id;
        this.name  = name;
        this.price = price;
    }
    
    public Product(String id, String name, String price) {
        this.id    = id;
        this.name  = name;
        BigDecimal tmpPrice = new BigDecimal(price);
        tmpPrice.setScale(2);
        this.price = tmpPrice;
    }    

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
    
    public boolean equals(Product p) {
        if (p.getId().equals(this.id)
            && p.getName().equals(this.name)
            && p.getPrice().equals(this.price))
        {
            return true;
        }
        
        return false;
    }
}
