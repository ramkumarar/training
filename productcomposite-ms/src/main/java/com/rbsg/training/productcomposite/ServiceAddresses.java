package com.rbsg.training.productcomposite;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ServiceAddresses {
    private String compositeAddress;
    private String productAddress;
    private String reviewAddress;
    private String recommendationAddress;

}
