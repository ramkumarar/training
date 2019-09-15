package com.rbsg.training.productcomposite;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.rbsg.training.productcomposite.model.Product;
import com.rbsg.training.productcomposite.model.Recommendation;
import com.rbsg.training.productcomposite.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Component
public class ProductCompositeDelegate {
    private final RestTemplate restTemplate;
    private EurekaClient eurekaClient;
    private LoadBalancerClient loadBalancer;

    @Autowired
    public ProductCompositeDelegate(RestTemplate restTemplate,EurekaClient eurekaClient, LoadBalancerClient loadBalancer) {
        this.restTemplate = restTemplate;
        this.eurekaClient = eurekaClient;
        this.loadBalancer =loadBalancer;
    }

    @HystrixCommand(fallbackMethod = "defaultProduct")
    public Product getProduct(int productId) {
        //ServiceInstance serviceInstance=loadBalancer.choose("product-ms");
        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("product-ms", false);
        String baseUrl=instanceInfo.getHomePageUrl() + "/product/" + productId;

        ResponseEntity<Product> result = restTemplate.getForEntity(baseUrl, Product.class);
        return result.getBody();
    }

    @HystrixCommand(fallbackMethod = "defaultRecommendations",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value="5"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value="10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="50")
            }
    )
    public List<Recommendation>  getRecommendations(int productId) {
        ServiceInstance serviceInstance=loadBalancer.choose("recommendation-ms");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(serviceInstance.getUri().toString() + "/recommendation").queryParam("productId",productId);
        String baseUrl=builder.toUriString();
        ResponseEntity<List<Recommendation>> result = restTemplate.exchange(builder.toUriString(),HttpMethod.GET,null, new ParameterizedTypeReference<List<Recommendation>>(){});
        return result.getBody();
    }

    @HystrixCommand(fallbackMethod = "defaultReviews")
    public List<Review> getReviews(int productId) {
        ServiceInstance serviceInstance=loadBalancer.choose("review-ms");
        String baseUrl=serviceInstance.getUri().toString() + "/review";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl).queryParam("productId",productId);
        ResponseEntity<List<Review>> result = restTemplate.exchange(builder.toUriString(),HttpMethod.GET,null, new ParameterizedTypeReference<List<Review>>(){});
        return result.getBody();
    }

    /**
     * Fallback method for getRecommendations()
     *
     * @param productId
     * @return
     */
    public List<Recommendation> defaultRecommendations(int productId) {
            return Arrays.asList(new Recommendation(productId, 1, "Fallback Author 1", 1, "Fallback Content 1", ""));
    }

    /**
     * Fallback method for getReviews()
     *
     * @param productId
     * @return
     */
    public List<Review> defaultReviews(int productId) {
        return Arrays.asList(new Review(productId, 1, "Fallback Author 1", "Fallback Subject 1", "Fallback Content 1", ""));
    }

    /**
     * Fallback method for getProduct()
     *
     * @param productId
     * @return
     */
    public Product defaultProduct(int productId) {
        return new Product(productId, "Fallback Name", -1, "");
    }

    public <T> ResponseEntity<T> createOkResponse(T body) {
        return createResponse(body, HttpStatus.OK);
    }


    public <T> ResponseEntity<T> createResponse(T body, HttpStatus httpStatus) {
        return new ResponseEntity<>(body, httpStatus);
    }
}
