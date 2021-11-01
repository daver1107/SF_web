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
    private static WebDriver linkCheckerdr;
    private static By innerLinksxPath = By.xpath("//a[contains(@href,'skillfactory.ru/')]");


    public static void runLinkChecker(String URL) {
                System.setProperty("webdriver.chrome.driver", "D:\\classes\\RUS_QA\\33.3\\SeleniumTest\\selenium\\chromedriver.exe");
                linkCheckerdr = new ChromeDriver();
                linkCheckerdr.manage().window().maximize();
                linkCheckerdr.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
                linkCheckerdr.get(URL);
                List<WebElement> innerLinks = linkCheckerdr.findElements(innerLinksxPath);
                linkChecker(innerLinks);

    }

    private static void linkChecker(List<WebElement> links) {


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
                    System.out.println(pageURL + " !BROKEN LINK!");
                    numberOfBrokenLinks++;
                } else {
                    System.out.println(pageURL + " ok");
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
