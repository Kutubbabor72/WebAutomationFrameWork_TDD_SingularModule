package com.amazon.testPages.HomePageTest;

import com.amazon.pages.HomePage;
import com.amazon.pages.HomePage1;
import configuration.common.WebTestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.amazon.pageElement.HomePageElement.verifySearchProductWebElement;

public class HomePageTest1 extends WebTestBase {
    // Another approach of constructor without parameter
    HomePage1 homePage1;


    @Test(enabled = false)
    public static void verifySearchProductUsingValidProductName() throws InterruptedException {
       //Action Method
        HomePage1 homePage1 = new HomePage1();
        homePage1.searchValidProductName();

        //verification method
        String expectedProductName="\"masks\"";
        String actualProductName=driver.findElement(By.xpath(verifySearchProductWebElement)).getText();
        Assert.assertEquals(actualProductName,expectedProductName,"Product name doesn't match ");
    }
}
