package lib.ui;

import io.appium.java_client.AppiumDriver;

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
            CANCEL_SEARCH_BUTTON,
            HIDE_NOTICE_BUTTON;


    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(
                TITLE,
                "Cannot find article title",
                15
        );
    }

    public WebElement waitForTitleElement(int seconds) {
        return this.waitForElementPresent(
                TITLE,
                "Cannot find article title",
                seconds
        );
    }

    public WebElement waitForNavigationTypeElement() {
        return this.waitForElementPresent(
                ELEMENT_TYPE_NAVIGATION_BAR,
                "Cannot find " + ELEMENT_TYPE_NAVIGATION_BAR,
                15
        );
    }

    public WebElement waitForNavigationTypeElement(int seconds) {
        return this.waitForElementPresent(
                ELEMENT_TYPE_NAVIGATION_BAR,
                "Cannot find " + ELEMENT_TYPE_NAVIGATION_BAR,
                seconds
        );
    }

    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        if (Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("text");
        } else if (Platform.getInstance().isIOS()) {
            return title_element.getAttribute("name");
        } else {
            return title_element.getText();
        }
    }

    public String getElementTypeNavigationBar() {
        WebElement nav_element = waitForNavigationTypeElement();
        return nav_element.getAttribute("name");
    }

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

    public void addArticlesToMySaved() {
        if (Platform.getInstance().isMW()) {
            this.removeArticleFromMySavedIfItAdded();
        }
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list", 15);
    }

    public void hideNoticeWindow() {
        try {
            this.waitForElementAndClick(HIDE_NOTICE_BUTTON, "Cannot find notice window", 5);
        } catch (Exception e) {
            return;
        }
    }

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

    public void tapCancelButtonInSearch() {
        this.waitForElementAndClick(
                CANCEL_SEARCH_BUTTON,
                "Cannot find 'Cancel' search button",
                10);
    }
}
