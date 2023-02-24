package com.example.furniture.Services.Impl;

import com.example.furniture.Entity.Product;
import com.example.furniture.Pojo.ProductPojo;
import com.example.furniture.Pojo.UserPojo;
import com.example.furniture.Repo.ProductRepo;
import com.example.furniture.Services.ProductServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductServices {
    private  final ProductRepo productRepo;
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/productimage";

    @Override
    public ProductPojo save(ProductPojo productPojo) throws IOException {
        Product product;
        if (productPojo.getId() != null) {
            product = productRepo.findById(productPojo.getId()).orElseThrow(() -> new RuntimeException("Not Found"));
        } else {
            product = new Product();
        }

        if(productPojo.getId()!=null){
            product.setId(product.getId());
        }
        product.setDescription(productPojo.getDescription());
        product.setPrice(productPojo.getPrice());
        product.setProductname(productPojo.getProductname());
        product.setQuantity(productPojo.getQuantity());
        product.setBrand(productPojo.getBrand());
        product.setColor(productPojo.getColor());
        product.setCatrgory(productPojo.getCategory());

        if(productPojo.getImage()!=null){
            StringBuilder fileNames = new StringBuilder();
            System.out.println(UPLOAD_DIRECTORY);
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, productPojo.getImage().getOriginalFilename());
            fileNames.append(productPojo.getImage().getOriginalFilename());
            Files.write(fileNameAndPath, productPojo.getImage().getBytes());

            product.setImage(productPojo.getImage().getOriginalFilename());
        }

        productRepo.save(product);
        return new ProductPojo(product);
    }

    @Override
    public List<Product> findAll() {
        return findAllinList(productRepo.findAll());
    }

    @Override
    public List<Product> getThreeRandomData() {
        return findAllinList(productRepo.getThreeRandomData());
    }


    @Override
    public Product findById(Integer id) {
        Product product=productRepo.findById(id).orElseThrow(()-> new RuntimeException("not found"));
        product=Product.builder()
                .id(product.getId())
                .productname(product.getProductname())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .brand(product.getBrand())
                .catrgory(product.getCatrgory())
                .color(product.getColor())
                .description(product.getDescription())
                .imageBase64(getImageBase64(product.getImage()))
                .build();
        return product;
    }

    @Override
    public void deleteById(Integer id) {
        productRepo.deleteById(id);
    }


    public List<Product> findAllinList(List<Product> list){

        Stream<Product> allJobsWithImage = list.stream().map(product ->
                Product.builder()
                        .id(product.getId())
                        .productname(product.getProductname())
                        .quantity(product.getQuantity())
                        .price(product.getPrice())
                        .description(product.getDescription())
                        .imageBase64(getImageBase64(product.getImage()))
                        .build()
        );
        list = allJobsWithImage.toList();
        return list;
    }


    public String getImageBase64(String fileName) {
        if (fileName!=null) {
            String filePath = System.getProperty("user.dir")+"/productimage/";
            File file = new File(filePath + fileName);
            byte[] bytes;
            try {
                bytes = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return Base64.getEncoder().encodeToString(bytes);
        }
        return null;
    }
}
