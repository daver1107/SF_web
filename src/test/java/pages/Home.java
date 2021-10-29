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

    public boolean subscribeWithEmail(String emailValue) {
        if (emailSubscription(emailValue).getText().contains(PreSetData.invalidEmailMessageRU) ||
                emailSubscription(emailValue).getText().contains(PreSetData.invalidEmailMessageRU) ||
                emailSubscription(emailValue).getText().contains(PreSetData.requiredFieldsEmptyRU) ||
                emailSubscription(emailValue).getText().contains(PreSetData.requiredFieldsEmptyEN)) {
            System.out.println(emailSubscription(emailValue).getText());
            return true;
        }
        if (emailSubscription(emailValue).getText().contains(PreSetData.subscriptionSuccessRU) ||
                emailSubscription(emailValue).getText().contains(PreSetData.subscriptionSuccessEN)) {
            System.out.println(emailSubscription(emailValue).getText());
            return true;
        } else return false;
    }

    private WebElement emailSubscription(String providedEmail) {
        WebElement emailField = getWait().until(ExpectedConditions.elementToBeClickable(emailSubscriptionFieldxPath));
        emailField.clear();
        emailField.sendKeys(providedEmail);
        emailField.submit();
        WebElement result;
        try {
            result = getDriver().findElement(invalidEmailErrorxPath);
        }
        catch (Exception e)
        {
            result = getDriver().findElement(successEmailSubmitxPath);
        }
        return result;

    }
    public void test(String emailValue){
        emailSubscription(emailValue);
        WebElement error = getDriver().findElement(invalidEmailErrorxPath);
        WebElement success = getDriver().findElement(successEmailSubmitxPath);
//        System.out.println(error.getText());
        System.out.println(success.getText());

    }
}
