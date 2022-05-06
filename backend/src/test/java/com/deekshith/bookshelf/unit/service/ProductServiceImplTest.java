package com.deekshith.bookshelf.unit.service;

import com.deekshith.bookshelf.model.Product;
import com.deekshith.bookshelf.repository.ProductRepository;
import com.deekshith.bookshelf.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductServiceImplTest implements ProductServiceTest{
    @Mock
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl productService;

    Product product = new Product("Introduction to Algorithms", "https://d1b14unh5d6w7g.cloudfront.net/0262032937.01.S001.LXXXXXXX.jpg?Expires=1649693396&Signature=cAHAJWlxMc2zz65Z7BogPJHdNFqWfpfpAa1N71iEltfLuBlsZ-1nphp6Der75x9cs7TU0TxQjVNdSex1CuBhgcAyMDEC0zHTJjKVMILMxNMhP03wqBlecFVfHUb91VsngKXLgPwPY8LWsJs36dQnDoViDUE2mH2e94H6f22b5Y8_&Key-Pair-Id=APKAIUO27P366FGALUMQ", "The MIT Press", "Computer Science", "Introduction to Algorithms is a book on computer programming by Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, and Clifford Stein.", 4.5, 12, 20.89, 10);

    @Test
    public void whenGetProduct_shouldReturnProduct() {
        Mockito.when(productRepository.findById("62603785716cf23e69afb81f")).thenReturn(Optional.ofNullable(product));
        System.out.println(product);
        assertEquals("62603785716cf23e69afb81f", productService.getProduct("62603785716cf23e69afb81f").getId());
    }

    @Test
    public void whenGetProducts_shouldReturnAllProducts() {
        List<Product> retrievedProducts = new ArrayList<>(List.of(product));
        Mockito.when(productRepository.findAll()).thenReturn(retrievedProducts);
        assertEquals(retrievedProducts.get(0).getName(), productService.getProducts().get(0).getName());

    }
}
