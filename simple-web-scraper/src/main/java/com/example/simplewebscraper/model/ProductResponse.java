package com.example.simplewebscraper.model;

import com.example.simplewebscraper.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private String title;
    private Currency currency;
    private double price;
    private String url;
}
