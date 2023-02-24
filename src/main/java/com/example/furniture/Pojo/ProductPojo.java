package com.example.furniture.Pojo;


import com.example.furniture.Entity.Product;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPojo {
    private Integer id;
    private  String price;
    private  String productname;
    private String description;
     private String quantity;
     private String brand;
     private String category;
     private String color;
     private MultipartFile image;
     public ProductPojo(Product product){
       this.id=product.getId();
       this.price=product.getPrice();
       this.description=product.getDescription();
       this.productname=product.getProductname();
       this.quantity=product.getQuantity();
       this.brand=product.getBrand();
       this.category=product.getCatrgory();
       this.color=product.getColor();
     }
}
