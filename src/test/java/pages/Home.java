package pages;

import logic.SetUp;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Home extends SetUp {
    private static By dropDownTopMenu = By.xpath("//div[@class = 't978__menu-link-wrapper']");

    public void setDropDownTopMenuClickable() {
        List <WebElement> dropDownElements = getDriver().findElements(dropDownTopMenu);
        for (WebElement element: dropDownElements) {
            element.click();
        }
    }
}
