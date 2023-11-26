import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class TestFilter {

    public static WebDriver driver;

    public void setFilter(String filterName, List<String> values) throws InterruptedException {
        Thread.sleep(5000);
        WebElement filterAccordion = driver.findElement(By.xpath("//span[contains(@class,'mat-content')]/following::legend[contains(text(),'"+filterName+"')]"));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(filterAccordion));
        filterAccordion.click();

        for (String value: values) {
            if(value.equalsIgnoreCase("All")){
                List<WebElement> filterCheckboxes = driver.findElements(By.xpath("//input[@name='"+filterName+"']/following::span[@class='filter-display-name']"));
                for(int i =0 ;i<filterCheckboxes.size();i++){
                    filterCheckboxes.get(i).click();
                }
                break;
            }else {
                WebElement element = driver.findElement(By.xpath("//input[@name='"+filterName+"']/following::span[@class='filter-display-name' and contains(text(),'" +value+ "')]"));
                element.click();
            }
        }

    }

    @BeforeMethod
    public void setUp(){
        driver = new ChromeDriver();
        driver.get("https://www.t-mobile.com/tablets");
        driver.manage().window().maximize();
    }

    @Test(description = "Dummy test case")
    public void testFilter() throws InterruptedException {
        List<String> OSValues = List.of("All");
        List<String> BrandValues = List.of("Apple");
        setFilter("Operating System", OSValues);
        setFilter("Brands", BrandValues);
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}

