package logic;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import pages.Home;

import java.time.Duration;

public class SetUp {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String URL = "http://localhost:4444/wd/hub/";
    public static final String testURL = "https://www.skillfactory.ru/";
    public  Home homePO;

    public static WebDriver getDriver() {
        return driver;
    }

    public static WebDriverWait getWait() {
        return wait;
    }
    @BeforeTest
    public void startInit() {
        System.setProperty("webdriver.chrome.driver", "D:\\classes\\RUS_QA\\33.3\\SeleniumTest\\selenium\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.get(testURL);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        homePO = new Home();
   }


    @After
    void close(){
        driver.close();
        driver.quit();
    }
}