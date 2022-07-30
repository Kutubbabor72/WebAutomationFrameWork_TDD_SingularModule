package com.amazon.testPages.HomePageTest;

import com.amazon.pages.HomePage;
import configuration.common.WebTestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.amazon.pageElement.HomePageElement.verifySearchProductWebElement;
import static com.amazon.pages.HomePage.*;

public class HomePageTest extends WebTestBase {


    @Test (enabled = false)
    public static void verifySearchProductUsingValidProductName() throws InterruptedException {
       //Action Method
        HomePage homePage = new HomePage(driver);
        homePage.searchValidProductName();

        //verification method
        String expectedProductName="\"masks\"";
        String actualProductName=driver.findElement(By.xpath(verifySearchProductWebElement)).getText();
        Assert.assertEquals(actualProductName,expectedProductName,"Product name doesn't match ");
    }
}
