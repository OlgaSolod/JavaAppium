package lib.ui;

import io.appium.java_client.AppiumDriver;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;


abstract public class ArticlePageObject extends MainPageObject {
    protected static String
            TITLE,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON,
            EXISTING_FOLDER,
            ELEMENT_TYPE_NAVIGATION_BAR,
            CANCEL_SEARCH_BUTTON;


    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Waiting for title element")
    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(
                TITLE,
                "Cannot find article title",
                15
        );
    }

    @Step("Waiting for title element in seconds")
    public WebElement waitForTitleElement(int seconds) {
        return this.waitForElementPresent(
                TITLE,
                "Cannot find article title",
                seconds
        );
    }

    @Step("Waiting for navigation type element")
    public WebElement waitForNavigationTypeElement() {
        return this.waitForElementPresent(
                ELEMENT_TYPE_NAVIGATION_BAR,
                "Cannot find " + ELEMENT_TYPE_NAVIGATION_BAR,
                15
        );
    }

    @Step("Waiting for navigation type element in seconds")
    public WebElement waitForNavigationTypeElement(int seconds) {
        return this.waitForElementPresent(
                ELEMENT_TYPE_NAVIGATION_BAR,
                "Cannot find " + ELEMENT_TYPE_NAVIGATION_BAR,
                seconds
        );
    }

    @Step("Getting article title")
    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        screenshot(this.take_screenshot("article_title"));
        if (Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("text");
        } else if (Platform.getInstance().isIOS()) {
            return title_element.getAttribute("name");
        } else {
            return title_element.getText();
        }
    }

    @Step("Getting element type from navigation bar for iOS")
    public String getElementTypeNavigationBar() {
        WebElement nav_element = waitForNavigationTypeElement();
        return nav_element.getAttribute("name");
    }

    @Step("Swiping to footer")
    public void swipeToFooter() {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpFindToElements(
                    FOOTER_ELEMENT,
                    "Cannot find footer element",
                    40
            );
        } else if (Platform.getInstance().isIOS()) {
            this.swipeUpTillElementAppear(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40
            );
        } else {
            this.scrollWebPageTillElementNotVisible(
                    FOOTER_ELEMENT,
                    "Cannot find the end of the articles",
                    40
            );
        }
    }

    @Step("Adding article to my list")
    public void addArticleToMyList(String name_of_folder) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                15
        );
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find the option to add article to reading list",
                15
        );
        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' tip overlay",
                15
        );
        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input to set name of articles folder",
                15
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text into articles folder input",
                15
        );
        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press OK Button",
                15
        );
    }

    @Step("Adding article to my saved list")
    public void addArticlesToMySaved() {
        if (Platform.getInstance().isMW()) {
            this.removeArticleFromMySavedIfItAdded();
        }
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list", 15);
    }

    @Step("Removing article from my list if it was added")
    public void removeArticleFromMySavedIfItAdded() {
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) {
            this.waitForElementAndClick(
                    OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannot click button to remove from mySaved",
                    5
            );
            this.waitForElementPresent(
                    OPTIONS_ADD_TO_MY_LIST_BUTTON,
                    "Cannot find button to add an article to saved list after removing it from this list before");
        }
    }

    @Step("Adding article to existing list")
    public void addArticleToExistingList() {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                15
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find the option to add article to reading list",
                15
        );

        this.waitForElementAndClick(
                EXISTING_FOLDER,
                "Cannot find 'Lists of lists' element on the page",
                15
        );
    }

    @Step("Closing opening article")
    public void closeArticle() {
        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            this.waitForElementAndClick(
                    CLOSE_ARTICLE_BUTTON,
                    "Cannot close article, cannot find X link",
                    15
            );
        } else {
            System.out.println("Method closeArticle() do nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Tapping cancel button in search line")
    public void tapCancelButtonInSearch() {
        this.waitForElementAndClick(
                CANCEL_SEARCH_BUTTON,
                "Cannot find 'Cancel' search button",
                10);
    }
}
