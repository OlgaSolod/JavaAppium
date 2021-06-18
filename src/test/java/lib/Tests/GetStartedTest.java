package lib.Tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;
@Epic("Tests of welcome page")
public class GetStartedTest extends CoreTestCase {
    @Test
    @Features(value = {@Feature(value = "Welcome screen")})
    @DisplayName("Skipping welcome screen on iOS")
    @Description("We pass through welcome screen in app Wiki in iOS")
    @Severity(value = SeverityLevel.NORMAL)
    public void testPassThroughWelcome(){
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isMW()){
            return;
        }
        WelcomePageObject welcomePageObject = new WelcomePageObject(driver);
        welcomePageObject.waitForLearnMoreLink();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForNewWayToExplore();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForAddOrEditPreferredLanguage();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForLearnMoreAboutDataCollected();
        welcomePageObject.clickGetStarted();
    }
}
