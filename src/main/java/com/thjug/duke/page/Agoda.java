package com.thjug.duke.page;

import java.util.LinkedList;
import java.util.List;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Agoda extends Page {

	public Agoda(final WebDriverProvider driverProvider) {
		super(driverProvider);
	}
    
    public WebElement findHotelLink(final String hotel) {
        final WebElement element = findElement(By.name("hotelInfoPlaceholder"));
        
        for(final WebElement a : element.findElements(By.cssSelector("a"))) {
            if (hotel.equals(a.getAttribute("title"))) {
                return a;
            }
        }
        
        return null;
    }
    
    public String findHeaderPrice() {
        final WebElement element = findElement(By.className("header_hotel "));
        final WebElement price = element.findElement(By.className("purple"));

        return price.getText();
    }
    
    public List<String> findPriceList() {
        final List<WebElement> element = findElements(By.className("sgrayu"));
        
        final List<String> prices = new LinkedList<>();
        element.stream().forEach((e) -> {
            prices.add(e.findElement(By.className("purple")).getText());
        });

        return prices;
    }
}
