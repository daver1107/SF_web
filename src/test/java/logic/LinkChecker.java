package logic;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class LinkChecker extends SetUp {
    private static By innerLinksxPath = By.xpath("//a[contains(@href,'skillfactory.ru/')]");
    private static WebDriver linkCheckerdr;


    public static void runLinkChecker() {
        System.setProperty("webdriver.chrome.driver", "D:\\classes\\RUS_QA\\33.3\\SeleniumTest\\selenium\\chromedriver.exe");
        linkCheckerdr = new ChromeDriver();
        linkCheckerdr.manage().window().maximize();
        linkCheckerdr.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        linkCheckerdr.get(testURL);
        List<WebElement> links = linkCheckerdr.findElements(innerLinksxPath);

        String pageURL = "";
        HttpURLConnection huc;
        int respCode = 200;

        Iterator<WebElement> it = links.iterator();

        while (it.hasNext()) {

            pageURL = it.next().getAttribute("href");

            try {
                huc = (HttpURLConnection) (new URL(pageURL).openConnection());

                huc.setRequestMethod("HEAD");

                huc.connect();

                respCode = huc.getResponseCode();
                int numberOfBrokenLinks = 0;
                if (respCode >= 400) {
                    System.out.println(pageURL + " is a broken link");
                    numberOfBrokenLinks++;
                } else {
                    System.out.println(pageURL + " is a valid link");
                }
                Assert.assertEquals(numberOfBrokenLinks, 0);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
