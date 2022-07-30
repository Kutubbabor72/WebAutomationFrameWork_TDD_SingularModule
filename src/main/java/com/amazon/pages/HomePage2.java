package com.amazon.pages;

import configuration.common.WebTestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static com.amazon.pageElement.HomePageElement.*;
import static configuration.utilities.DataDriven.getItemsListFromDB;
import static configuration.utilities.DataDriven.getItemsListFromExcel;

public class HomePage2 extends WebTestBase {
    // This class is for @Before Method with page factory

    //Action Class for business flow

    //Action Method for search box functionality with valid product name
//    public static void searchValidProductName1() throws InterruptedException {
//        driver.findElement(By.xpath(searchBoxWebElement)).sendKeys("masks");
//        Thread.sleep(2000);
//        driver.findElement(By.xpath(searchButtonWebElement)).click();
//        Thread.sleep(2000);
//    } Don't need this traditional coding

    // Modern Approach : FindBy with how

//    @FindBy()public WebElement variableName;

//    @FindBy(how = How.XPATH,using = searchBoxWebElement)public WebElement searchBox;

    // Modern Approach : FindBy direct approach - it is recommended to use
    @FindBy(xpath = searchBoxWebElement)
    public WebElement searchBox;
    @FindBy(xpath = searchButtonWebElement)
    public WebElement searchButton;

    public void demo() {
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

    public static List<String> getExpectedProducts() {
        List<String> itemsList = new ArrayList<String>();
        itemsList.add("Hand sanitizer");
        itemsList.add("iphone 11 pro max");
        itemsList.add("T-shirt");
        itemsList.add("Mens shirt");
        itemsList.add("Shoes");
        itemsList.add("Camera");
        itemsList.add("Bike");
        itemsList.add("Tv");
        return itemsList;
    }

    public void searchProductUsingValidProductName1() throws InterruptedException {
        for(String st :  getExpectedProducts()){
            searchBox.clear();
            searchBox.sendKeys(st);
            searchButton.click();
            String expectedProductName="\""+ st +"\"";
            String actualProductName=driver.findElement(By.xpath(verifySearchProductWebElement)).getText();
            Assert.assertEquals(actualProductName,expectedProductName,"Product Name does not match");
            waitFor(5);
        }

    }

    // Data driven approach from Excel

    public void searchProductUsingValidProductName2() throws Exception {
        for (int i = 0; i < getItemsListFromExcel().size(); i++) {
            searchBox.clear();
            searchBox.sendKeys(getItemsListFromExcel().get(i+1));
            searchButton.click();
            String expectedProductName = "\"" + getItemsListFromExcel().get(i+1) + "\"";
            String actualProductName = driver.findElement(By.xpath(verifySearchProductWebElement)).getText();
            Assert.assertEquals(actualProductName, expectedProductName, "Product name doest match");
            waitFor(5);
        }

    }

    // Data driven approach from Database
    public void searchProductUsingValidProductName3() throws Exception {
        for (String st : getItemsListFromDB()) {
            searchBox.clear();
            searchBox.sendKeys(st);
            searchButton.click();
            String expectedProductName = "\"" + st + "\"";
            String actualProductName = driver.findElement(By.xpath(verifySearchProductWebElement)).getText();
            Assert.assertEquals(actualProductName, expectedProductName, "Product name doest match");
        }
    }

    // Data driven approach from Data Provider
    public void searchProductUsingValidProductName4() throws Exception {

//        for (int i = 0; i < getRegistrationDataFromExcel().length; i++) {
//            for (int j = i; j < getRegistrationDataFromExcel()[i].length; j++) {
//                System.out.println(getRegistrationDataFromExcel()[i][j]);
//                searchBox.clear();
//                String firstName= (String) getRegistrationDataFromExcel()[i][j];
//                searchBox.sendKeys(firstName);
//                waitFor(5);
//                searchBox.clear();
//                String lastName= (String) getRegistrationDataFromExcel()[i][j];
//                lastName.sendKeys(lastName);

//                searchButton.click();
//                String expectedProductName = "\"" + st + "\"";
//                String actualProductName = driver.findElement(By.xpath(verifySearchedProductWebElement)).getText();
//                Assert.assertEquals(actualProductName, expectedProductName, "Product name doest match");
//
    }



//        for (Object[] stt:  getRegistrationDataFromExcel()) {
//            for (Object st:stt) {
//                searchBox.clear();
//                searchBox.sendKeys((String) st);
//                searchButton.click();
//                String expectedProductName = "\"" + st + "\"";
//                String actualProductName = driver.findElement(By.xpath(verifySearchedProductWebElement)).getText();
//                Assert.assertEquals(actualProductName, expectedProductName, "Product name doest match");
//
//
//            }
//        }


//        for (String st : getItemsListFromDB()) {
//            searchBox.clear();
//            searchBox.sendKeys(st);
//            searchButton.click();
//            String expectedProductName = "\"" + st + "\"";
//            String actualProductName = driver.findElement(By.xpath(verifySearchedProductWebElement)).getText();
//            Assert.assertEquals(actualProductName, expectedProductName, "Product name doest match");
//        }



}
