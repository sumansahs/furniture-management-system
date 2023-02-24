package com.example.furniture.Services;

import com.example.furniture.Entity.Product;
import com.example.furniture.Pojo.ProductPojo;

import java.util.*;
import java.io.IOException;

public interface ProductServices {
    ProductPojo save(ProductPojo productPojo) throws IOException;
    List<Product> findAll();
    List<Product> getThreeRandomData();

    Product findById(Integer id);
    void deleteById(Integer id);
}

