package com.example.simplewebscraper.controller;

import com.example.simplewebscraper.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScrapeProductsSettings{
    private String url;
    private Currency currency;
    private String productsTagAttribute;
    private String titleProductTagAttribute;
    private String priceFractionTagAttribute;
    private String priceCentsTagAttribute;
    private String urlProductTagAttribute;

}