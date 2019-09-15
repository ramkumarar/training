package com.rbsg.training.productcomposite;

import com.rbsg.training.productcomposite.model.Product;
import com.rbsg.training.productcomposite.model.Recommendation;
import com.rbsg.training.productcomposite.model.Review;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductCompositeService {

    private final ProductCompositeDelegate productCompositeDelegate;

    public ProductCompositeService(ProductCompositeDelegate productCompositeDelegate) {
        this.productCompositeDelegate = productCompositeDelegate;
    }

    @RequestMapping("/{productId}")
    public ResponseEntity<ProductAggregated> getProduct(@PathVariable int productId) {
        Product product = productCompositeDelegate.getProduct(productId);
        List<Recommendation> recommendations = productCompositeDelegate.getRecommendations(productId);
        List<Review> reviews = productCompositeDelegate.getReviews(productId);
        ProductAggregated productAggregated = new ProductAggregated(product, recommendations,reviews,null);

        return new ResponseEntity<>(productAggregated, HttpStatus.OK);
    }
}
