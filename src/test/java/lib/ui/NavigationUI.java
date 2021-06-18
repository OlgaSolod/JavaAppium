package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject {
    protected static String
            MY_LISTS_LINK,
            OPEN_NAVIGATION,
            RETURN_TO_MAIN_PAGE;

    public NavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Open navigation menu")
    public void openNavigation() {
        if (Platform.getInstance().isMW()) {
            this.waitForElementAndClick(OPEN_NAVIGATION,
                    "Cannot find and click open navigation button",
                    5);
        } else {
            System.out.println("Method openNavigation() do nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Clicking on my lists menu")
    public void clickMyLists() {
        if (Platform.getInstance().isMW()) {
            this.tryClickElementWithFewAttempts(
                    MY_LISTS_LINK,
                    "Cannot find navigation button to My list",
                    5
            );
        } else {
            this.waitForElementAndClick(
                    MY_LISTS_LINK,
                    "Cannot find navigation button to My list",
                    5
            );
        }
    }

    @Step("Clicking on return button to return to main page")
    public void clickToReturnToMainPage() {
        waitForElementAndClick(RETURN_TO_MAIN_PAGE, "Cannot click to Wikipedia-main page button", 15);
    }
}
