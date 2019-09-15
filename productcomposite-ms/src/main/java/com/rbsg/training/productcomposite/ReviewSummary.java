package com.rbsg.training.productcomposite;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ReviewSummary {
    private int reviewId;
    private String author;
    private String subject;
}
