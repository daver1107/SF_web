package pages;

import logic.PreSetData;
import logic.SetUp;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

public class Home extends SetUp {
    private static By dropDownTopMenuxPath = By.xpath("//a[@class = 't978__menu-link t-name t-name_xs t-menu__link-item']");
    private static By buttomMenuxPath = By.xpath("//a[@class = 'tn-atom']");
    private static By emailSubscriptionFieldxPath = By.xpath("//input[@class = 't-input js-tilda-rule ']");
    private static By invalidEmailErrorCss = By.id("tilda-popup-for-error");
    private static By successEmailSubmitxPath = By.xpath("//div[@class = 't-form-success-popup__wrapper']");
    //    private static By successPopUpClosexPath = By.className("t-form-success-popup__close-icon");
    private static By innerLinksxPath = By.xpath("//a[contains(@href,'skillfactory.ru/')]");
    private static By giftButtonPopUpxPath = By.xpath("//div[@class = 'cq-popup__body wrapper wrapper-col']");
    private static By giftModalxPath = By.xpath("//div[@id= 'carrotquest-messenger-initialmsg']");
    private static By chatButtonCss = By.id("carrotquest-messenger-collapsed-container");
    private static By chatWindowCss = By.className("carrot-frame-body");
    private static By chatTextFieldxPath = By.xpath("//textarea[@id = 'carrotquest-messenger-reply-textarea']");
    private static By closeChatxPath = By.className("carrot-messenger-closed");


    private static List<WebElement> dropDownElements = getDriver().findElements(dropDownTopMenuxPath);
    private static List<WebElement> buttomMenu = getDriver().findElements(buttomMenuxPath);

    private static final int numberOfDropDownElements = 13;

    public void dropDownNumberOfSections() {
        Assert.assertEquals(dropDownElements.size(), numberOfDropDownElements);
    }

    public List<WebElement> innerLinks() {
        List<WebElement> innerLinks = getDriver().findElements(innerLinksxPath);
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
            result = getWait().until(ExpectedConditions.presenceOfElementLocated(invalidEmailErrorCss));
        } catch (Exception e) {
            result = getWait().until(ExpectedConditions.presenceOfElementLocated(successEmailSubmitxPath));
        }
        return result.getText();

    }

    public boolean giftPopUp() {
        getWait().until(ExpectedConditions.elementToBeClickable(giftButtonPopUpxPath)).click();
        if (getWait().until(ExpectedConditions.presenceOfElementLocated(giftModalxPath)).
                isDisplayed()) {
            getDriver().navigate().refresh();
            return true;
        } else return false;
    }

    public boolean onlineChat() {
        getWait().until(ExpectedConditions.elementToBeClickable(chatButtonCss)).click();
        getDriver().switchTo().frame("carrot-messenger-frame");
        if (getDriver().findElement(chatWindowCss).isDisplayed()) {
            getWait().until(ExpectedConditions.elementToBeClickable(chatTextFieldxPath)).
                    sendKeys("test qa message");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getDriver().findElement(chatWindowCss).click();
            return true;
        } else return false;
    }
}
