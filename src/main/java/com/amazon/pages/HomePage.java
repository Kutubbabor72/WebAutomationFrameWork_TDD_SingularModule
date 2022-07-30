package com.amazon.pages;

import configuration.common.WebTestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Optional;

import static com.amazon.pageElement.HomePageElement.*;

public class HomePage extends WebTestBase {
    public HomePage(WebDriver driver){
       // HomePage homePage = PageFactory.initElements(driver,HomePage.class);
        WebTestBase.driver=driver;
        PageFactory.initElements(WebTestBase.driver,this);
    }

    //Action Class for business flow

    //Action Method for search box functionality with valid product name
    public static void searchValidProductName1() throws InterruptedException {
        driver.findElement(By.xpath(searchBoxWebElement)).sendKeys("masks");
        Thread.sleep(2000);
        driver.findElement(By.xpath(searchButtonWebElement)).click();
        Thread.sleep(2000);
    }

    // Modern Approach : FindBy with how

    @FindBy()public WebElement variableName;

//    @FindBy(how = How.XPATH,using = searchBoxWebElement)public WebElement searchBox;

    // Modern Approach : FindBy direct approach - it is recommended to use
    @FindBy(xpath = searchBoxWebElement) public WebElement searchBox;
    @FindBy(xpath = searchButtonWebElement) public WebElement searchButton;

    public void demo(){
        searchBox.sendKeys("masks");
        searchBox.click();
        searchBox.getText();
    }

    public void searchValidProductName() throws InterruptedException {
        searchBox.sendKeys("masks");
        Thread.sleep(2000);
        searchButton.click();
        Thread.sleep(2000);
    }

}
