package com.example.simplewebscraper.utils;

import com.example.simplewebscraper.controller.ScrapeProductsSettings;
import com.example.simplewebscraper.exception.ScrapeException;
import com.example.simplewebscraper.model.ProductResponse;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WebScraper {

    /**
     * Method that retrieves the HTML code from the web page.
     *
     * @param url of the website from which the HTML code will be extracted.
     * @return html code as a Document object.
     * @throws IOException if there is an error connecting to the website or retrieving the HTML.
     */
    public static Document getHTML(String url) throws IOException {
        Connection connection = Jsoup.connect(url).timeout(10000);
        // Set appropriate headers to simulate a browser request
        connection.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");
        connection.header("Accept-Language", "en-US,en;q=0.9");
        connection.header("Referer", "https://www.google.com/");
        Connection.Response response = connection.execute();
        return response.parse();
    }


    /**
     * Scrapes products from a website based on the provided settings.
     *
     * @param scrapeProductsSettings the settings for the web scraping process.
     * @return a list of ProductResponse objects representing the scraped products.
     */
    public static List<ProductResponse> scrapeProductsFromWebsite(ScrapeProductsSettings scrapeProductsSettings) {
        List<ProductResponse> response = new ArrayList<>();
        try {
            Document document = getHTML(scrapeProductsSettings.getUrl());
            Elements products = document.select(scrapeProductsSettings.getProductsTagAttribute());
            for (Element product : products) {
                try {
                    ProductResponse productResponse = extractProductData(product, scrapeProductsSettings);
                    response.add(productResponse);
                } catch (Exception e) {
                    // Log or handle the exception accordingly
                    System.err.println("Error while accessing the element: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new ScrapeException("Error retrieving the HTML code");
        }
        return response;
    }

    /**
     * Extracts product data from a given product element using the provided settings.
     *
     * @param productElement the HTML element representing a product.
     * @param scrapeProductsSettings the settings for scraping the product data.
     * @return a ProductResponse object containing the extracted product data.
     */
    public static ProductResponse extractProductData(
            Element productElement,
            ScrapeProductsSettings scrapeProductsSettings
    ) {
        String title = productElement.select(scrapeProductsSettings.getTitleProductTagAttribute()).text();
        String urlProduct = productElement
                .select(scrapeProductsSettings.getUrlProductTagAttribute())
                .attr("href");
        String priceFraction = productElement
                .select(scrapeProductsSettings.getPriceFractionTagAttribute())
                .text();

        if (priceFraction.contains(" "))
            priceFraction = priceFraction.split(" ")[1];

        if (priceFraction.contains(".")) {
            priceFraction = Arrays.stream(priceFraction.split("\\."))
                    .reduce(String::concat).orElse(priceFraction);
        }

        String priceCent = productElement.select(scrapeProductsSettings.getPriceCentsTagAttribute()).text();

        double price = 0;
        if (!priceCent.equals("")) {
            price = Double.parseDouble(
                    String.format(
                            "%d.%d",
                            Integer.parseInt(priceFraction),
                            Integer.parseInt(priceCent))
            );

        } else {
            price = Double.parseDouble(
                    Integer.parseInt(priceFraction) + ""
            );
        }

        return ProductResponse.builder()
                .title(title)
                .url(urlProduct)
                .price(price)
                .currency(scrapeProductsSettings.getCurrency())
                .build();
    }
}
