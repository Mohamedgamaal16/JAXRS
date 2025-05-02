package org.example.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService {


    private static Map<Integer, Product> productMap = new HashMap<>();
    private static int idCounter = 1;

    public List<Product> getAllProducts() {
        return new ArrayList<>(productMap.values());
    }

    public Product getProduct(int id) {
        return productMap.get(id);
    }

    public Product addProduct(Product product) {
        product.setId(idCounter++);
        productMap.put(product.getId(), product);
        return product;
    }

    public Product updateProduct(int id, Product product) {
        if (!productMap.containsKey(id)) return null;
        product.setId(id);
        productMap.put(id, product);
        return product;
    }

    public Product deleteProduct(int id) {
        return productMap.remove(id);
    }


}
