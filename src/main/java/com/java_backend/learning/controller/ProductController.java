package com.java_backend.learning.controller;

import com.java_backend.learning.entity.Product;
import com.java_backend.learning.service.ProductService;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ✅ Upload Product with Image (Compressed → MEDIUMBLOB)
    @PostMapping("/upload")
    public Product uploadProduct(
            @RequestParam("file") MultipartFile file,
            @RequestParam String code,
            @RequestParam String name,
            @RequestParam Double price,
            @RequestParam String category) throws Exception {

        // ✅ Resize & Compress Image
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Thumbnails.of(file.getInputStream())
                .size(800, 800)        // Resize
                .outputQuality(0.6)    // Compression (0–1)
                .toOutputStream(outputStream);

        byte[] compressedImage = outputStream.toByteArray();

        Product product = new Product();
        product.setCode(code);
        product.setName(name);
        product.setPrice(price);
        product.setCategory(category);
        product.setImage(compressedImage);

        return productService.saveProduct(product);
    }

    // ✅ Normal JSON Product Creation (Without Image)
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    // ✅ Get All Products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // ✅ Get Product By ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    // ✅ Get Product Image
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Integer id) {

        Product product = productService.getProductById(id);

        return ResponseEntity.ok()
                .header("Content-Type", "image/png") // adjust if jpeg
                .body(product.getImage());
    }

    // ✅ Full Update (PUT)
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Integer id,
                                 @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    // ✅ Partial Update (PATCH)
    @PatchMapping("/{id}")
    public Product patchProduct(@PathVariable Integer id,
                                @RequestBody Product product) {
        return productService.patchProduct(id, product);
    }

    // ✅ Delete Product
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return "✅ Product deleted successfully";
    }
}