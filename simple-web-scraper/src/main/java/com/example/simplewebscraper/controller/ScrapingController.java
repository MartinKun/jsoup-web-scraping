package com.example.simplewebscraper.controller;

import com.example.simplewebscraper.model.ProductResponse;
import com.example.simplewebscraper.model.enums.Currency;
import com.example.simplewebscraper.utils.WebScraper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ScrapingController {

    private final String URL = "https://listado.mercadolibre.com.ar/guitarra-con-flores#D[A:guitarra%20con%20flores]";

    @GetMapping("/products")
    public List<ProductResponse> scrapeProducts() {

        ScrapeProductsSettings scrapeProductSettings = ScrapeProductsSettings.builder()
                .url(URL)
                .currency(Currency.ARG)
                .productsTagAttribute("li.ui-search-layout__item")
                .titleProductTagAttribute("h2")
                .priceFractionTagAttribute("span.price-tag-fraction")
                .priceCentsTagAttribute("span.price-tag-cent")
                .urlProductTagAttribute("a.ui-search-item__group__element.shops__items-group-details.ui-search-link")
                .build();

        return WebScraper.scrapeProductsFromWebsite(scrapeProductSettings);
    }
}
