package com.amazon.testPages.HomePageTest;

import com.amazon.pages.HomePage;
import com.amazon.pages.HomePage2;
import configuration.common.WebTestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static com.amazon.pageElement.HomePageElement.verifySearchProductWebElement;

public class HomePageTest2 extends WebTestBase {
    static HomePage2 homePage2;
    @BeforeMethod
    public void getInit(){
          homePage2= PageFactory.initElements(driver,HomePage2.class);
    }


    @Test
    public static void verifySearchProductUsingValidProductName() throws InterruptedException {
       //Action Method
        //HomePage homePage = new HomePage(driver);
        homePage2.searchValidProductName();

        //verification method
        String expectedProductName="\"masks\"";
        String actualProductName=driver.findElement(By.xpath(verifySearchProductWebElement)).getText();
        Assert.assertEquals(actualProductName,expectedProductName,"Product name doesn't match ");
    }

    @Test
    public static void verifySearchProductUsingValidProductName1() throws InterruptedException {
        //Action Method
        //HomePage homePage = new HomePage(driver);
        homePage2.searchValidProductName();

        //verification method
        String expectedProductName="\"masks1\"";
        String actualProductName=driver.findElement(By.xpath(verifySearchProductWebElement)).getText();
        Assert.assertEquals(actualProductName,expectedProductName,"Product name doesn't match ");
    }

    @Test @Ignore
    public void verifySearchProductUsingValidProductNameFromDirectArray() throws InterruptedException {
        homePage2.searchProductUsingValidProductName1();
    }

    @Test @Ignore
    public void verifySearchProductUsingValidProductNameFromExcelFile() throws Exception {
        homePage2.searchProductUsingValidProductName2();
    }
}
