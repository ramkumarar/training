package com.rbsg.training.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Product {
    private int productId;
    private String name;
    private int weight;
    private String serviceAddress;
}
