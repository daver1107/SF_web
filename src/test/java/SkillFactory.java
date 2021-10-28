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
    void subscriptionPositiveTest() {
        homePO.invalidEmailSubscription();
    }

    @Test
    void checkLinks(){
        LinkChecker.runLinkChecker(homePO.getInnerLinks());
    }

    @AfterClass
    void close() throws InterruptedException {
        getDriver().close();
        getDriver().quit();
    }
}