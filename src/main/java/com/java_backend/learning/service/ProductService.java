package com.java_backend.learning.service;

import com.java_backend.learning.entity.Product;
import com.java_backend.learning.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ✅ Create / Save Product
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // ✅ Get All Products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // ✅ Get Product By ID
    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // ✅ Delete Product
    public void deleteProduct(Integer id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    // ✅ Update Product
    public Product updateProduct(Integer id, Product updatedProduct) {
        Product product = getProductById(id);

        product.setCode(updatedProduct.getCode());
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setCategory(updatedProduct.getCategory());

        if (updatedProduct.getImage() != null) {
            product.setImage(updatedProduct.getImage());
        }

        return productRepository.save(product);
    }

    // ✅ Patch (Partial Update)
    public Product patchProduct(Integer id, Product partialProduct) {
        Product product = getProductById(id);

        if (partialProduct.getCode() != null)
            product.setCode(partialProduct.getCode());

        if (partialProduct.getName() != null)
            product.setName(partialProduct.getName());

        if (partialProduct.getPrice() != null)
            product.setPrice(partialProduct.getPrice());

        if (partialProduct.getCategory() != null)
            product.setCategory(partialProduct.getCategory());

        if (partialProduct.getImage() != null)
            product.setImage(partialProduct.getImage());

        return productRepository.save(product);
    }
}