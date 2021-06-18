package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObject extends MainPageObject {

    private static final String
            STEP_LEARN_MORE_LINK = "id:Learn more about Wikipedia",
            STEP_NEW_WAYS_TO_EXPLORE_TEXT = "id:New ways to explore",
            STEP_ADD_OR_EDIT_PREFERRED_LANGUAGE = "id:Add or edit preferred languages",
            STEP_FOR_LEARN_MORE_ABOUT_DATA_COLLECTED = "id:Learn more about data collected",
            NEXT_LINK = "id:Next",
            GET_STARTED_BUTTON = "id:Get started",
            SKIP = "id:Skip";


    public WelcomePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Waiting for link with text 'Learn more about Wikipedia'")
    public void waitForLearnMoreLink() {
        this.waitForElementPresent(
                STEP_LEARN_MORE_LINK,
                "Cannot find 'Learn more about Wikipedia' link",
                10
        );
    }

    @Step("Clicking 'Next' button")
    public void clickNextButton() {
        this.waitForElementAndClick(
                NEXT_LINK,
                "Cannot find and click 'Next' button",
                10
        );
    }

    @Step("Waiting for appearing of text 'New ways to explore'")
    public void waitForNewWayToExplore() {
        this.waitForElementPresent(
                STEP_NEW_WAYS_TO_EXPLORE_TEXT,
                "Cannot find 'New ways to explore' link",
                10
        );
    }

    @Step("Waiting for text 'Add or edit preferred languages'")
    public void waitForAddOrEditPreferredLanguage() {
        this.waitForElementPresent(
                STEP_ADD_OR_EDIT_PREFERRED_LANGUAGE,
                "Cannot find 'Add or edit preferred languages' link",
                10
        );
    }

    @Step("Waiting for text 'Learn more about data collected'")
    public void waitForLearnMoreAboutDataCollected() {
        this.waitForElementPresent(
                STEP_FOR_LEARN_MORE_ABOUT_DATA_COLLECTED,
                "Cannot find 'Learn more about data collected' link",
                10
        );
    }

    @Step("Clicking on 'Get started' button")
    public void clickGetStarted() {
        this.waitForElementAndClick(
                GET_STARTED_BUTTON,
                "Cannot find 'Get started' button",
                10
        );
    }

    @Step("Clicking 'Skip' button")
    public void clickSkip() {
        this.waitForElementAndClick(SKIP, "Cannot find skip button", 15);
    }
}
