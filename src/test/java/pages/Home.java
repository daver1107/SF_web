package pages;

import logic.PreSetData;
import logic.SetUp;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Home extends SetUp {
    private static By dropDownTopMenuxPath = By.xpath("//a[@class = 't978__menu-link t-name t-name_xs t-menu__link-item']");
    private static By buttomMenuxPath = By.xpath("//a[@class = 'tn-atom']");
    private static By innerLinksxPath = By.xpath("//a[contains(@href,'skillfactory.ru/')]");
    private static By emailSubscriptionFieldxPath = By.xpath("//input[@class = 't-input js-tilda-rule ']");
    private static By invalidEmailErrorxPath = By.id("tilda-popup-for-error");
    private static By successEmailSubmitxPath = By.xpath("//div[@class = 't-form-success-popup__wrapper']");
    private static By successPopUpClosexPath = By.className("t-form-success-popup__close-icon");

    private static List<WebElement> dropDownElements = getDriver().findElements(dropDownTopMenuxPath);
    private static List<WebElement> buttomMenu = getDriver().findElements(buttomMenuxPath);

    public static List<WebElement> getInnerLinks() {
        return innerLinks;
    }

    private static List<WebElement> innerLinks = getDriver().findElements(innerLinksxPath);


    private static final int numberOfDropDownElements = 13;

    public void dropDownNumberOfSections() {
        Assert.assertEquals(dropDownElements.size(), numberOfDropDownElements);
    }

    public List<WebElement> allMenuLinksCollection() {
        List<WebElement> innerLinks = new ArrayList<>();
        Stream.of(dropDownElements, buttomMenu).forEach(innerLinks::addAll);
        return innerLinks;
    }

    public boolean subscribeWithEmail(String providedEmail) {
        String actualResult = emailSubscriptionResult(providedEmail);
        getDriver().navigate().refresh();
        if (actualResult.contains(PreSetData.invalidEmailMessageRU) ||
                actualResult.contains(PreSetData.invalidEmailMessageEN) ||
                actualResult.contains(PreSetData.requiredFieldsEmptyRU) ||
                actualResult.contains(PreSetData.requiredFieldsEmptyEN)) {
            System.out.println(actualResult);
            return true;
        }
        if (actualResult.contains(PreSetData.subscriptionSuccessRU) ||
                actualResult.contains(PreSetData.subscriptionSuccessEN)) {
            System.out.println(actualResult);
            return true;
        } else return false;
    }

    private String emailSubscriptionResult(String providedEmail) {
        WebElement emailField = getWait().until(ExpectedConditions.elementToBeClickable(emailSubscriptionFieldxPath));
        emailField.clear();
        emailField.sendKeys(providedEmail);
        emailField.submit();
        WebElement result;
        try {
            result = getWait().until(ExpectedConditions.presenceOfElementLocated(invalidEmailErrorxPath));
        } catch (Exception e) {
            result = getWait().until(ExpectedConditions.presenceOfElementLocated(successEmailSubmitxPath));
        }
        return result.getText();

    }
}
