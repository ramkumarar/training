package com.rbsg.training.productcomposite;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RecommendationSummary {
    private int recommendationId;
    private String author;
    private int rate;
}
