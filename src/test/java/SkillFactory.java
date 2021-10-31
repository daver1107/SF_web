import logic.LinkChecker;
import logic.PreSetData;
import logic.SetUp;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class SkillFactory extends SetUp {

    @Test
    void menuElements() {
        homePO.dropDownNumberOfSections();
    }

    @Test
    void subscriptionNegativeTest() {
        homePO.subscribeWithEmail(PreSetData.invalidEmail);
    }
    @Test
    void supscriptionPositiveTest() {
        homePO.subscribeWithEmail(PreSetData.validEmail);
    }

    @Test
    void subscriptionEmptyEmail() {
        homePO.subscribeWithEmail(PreSetData.emptyField);
    }

    @Test
    void checkLinks(){
        LinkChecker.runLinkChecker(homePO.innerLinks());
    }

    @Test
    void isGiftPresent() {
        homePO.giftPopUp();
    }

    @Test
    void onLineChatPresence() {
        homePO.onlineChat();
    }

    @AfterClass
    void close(){
        getDriver().close();
        getDriver().quit();
    }
}