package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class SearchPageObject extends MainPageObject {
    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_RESULT_BY_NAME_AND_DESCRIPTION_TPL_IOS,
            SEARCH_RESULT_BY_NAME_AND_DESCRIPTION_TPL;


    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }


    /*TEMPLATES METHODS*/
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL
                .replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementByTwoSubstrings(String substring_title, String substring_description) {

        return SEARCH_RESULT_BY_NAME_AND_DESCRIPTION_TPL
                .replace("{DESCRIPTION}", substring_description)
                .replace("{TITLE}", substring_title);
    }

    private static String getResultSearchElementByTwoSubstringsIOS(String substring_title, String substring_description) {
        return SEARCH_RESULT_BY_NAME_AND_DESCRIPTION_TPL_IOS
                .replace("{DESCRIPTION}", substring_description)
                .replace("{TITLE}", substring_title);
    }

    /*TEMPLATES METHODS*/
    public void initSearchInput() {
        this.waitForElementAndClick(
                SEARCH_INIT_ELEMENT,
                "Cannot find search init element",
                15
        );
        this.waitForElementPresent(
                SEARCH_INPUT,
                "Cannot find search input after clicking search init element"
        );
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(
                SEARCH_CANCEL_BUTTON,
                "Cannot find cancel button",
                15
        );
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(
                SEARCH_CANCEL_BUTTON,
                "Search cancel button is still present",
                15
        );
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(
                SEARCH_CANCEL_BUTTON,
                "Cannot find and click search cancel button",
                10
        );
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(
                SEARCH_INPUT,
                search_line,
                "Cannot find any type into search input",
                15

        );
    }

    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(
                search_result_xpath,
                "Cannot find search result with substring " + substring);
    }

    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(
                search_result_xpath,
                "Cannot find and click search result with substring " + substring,
                10);
    }

    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15
        );
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty result element",
                15
        );
    }

    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(
                SEARCH_RESULT_ELEMENT,
                "We supposed not to find any results"
        );
    }

    public void initSearchField() {
        String text_search_field = waitForElementAndGetAttribute(
                SEARCH_INPUT,
                "text",
                "Cannot find 'Search...' in search field",
                15
        );
        this.assertElementHasText(
                SEARCH_INPUT,
                text_search_field,
                "This text hasn't found in this field!"
        );

        this.waitForElementAndClick(
                SEARCH_INPUT,
                "Cannot find search init element",
                15
        );
        this.waitForElementPresent(
                SEARCH_INPUT,
                "Cannot find search input after clicking search init element"
        );
    }

    public void waitForElementByTitleAndDescription(String title, String description) {
        this.waitForElementPresent(
                getResultSearchElementByTwoSubstrings(title, description),
                "Cannot find elements with title '" + title + "' and description '" + description + "'",
                30
        );
    }

    public void waitForElementByTitleAndDescriptionIOS(String title, String description) {
        this.waitForElementPresent(
                getResultSearchElementByTwoSubstringsIOS(title, description),
                "Cannot find elements with title '" + title + "' and description '" + description + "'",
                30
        );
    }

}

