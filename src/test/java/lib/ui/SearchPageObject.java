package lib.ui;

import io.qameta.allure.Step;
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
            SEARCH_RESULT_BY_NAME_AND_DESCRIPTION_TPL_ANDROID,
            SEARCH_RESULT_BY_NAME_AND_DESCRIPTION_TPL_MW;


    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }


    /*TEMPLATES METHODS*/
    @Step("Getting result '{substring}' element")
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL
                .replace("{SUBSTRING}", substring);
    }

    @Step("Getting result search '{substring_title}' and '{substring_description}' of element")
    private static String getResultSearchElementByTwoSubstrings(String substring_title, String substring_description) {

        return SEARCH_RESULT_BY_NAME_AND_DESCRIPTION_TPL_ANDROID
                .replace("{DESCRIPTION}", substring_description)
                .replace("{TITLE}", substring_title);
    }

    @Step("Getting result '{substring_title}' and '{substring_description}'of element for iOS")
    private static String getResultSearchElementByTwoSubstringsIOS(String substring_title, String substring_description) {
        return SEARCH_RESULT_BY_NAME_AND_DESCRIPTION_TPL_IOS
                .replace("{DESCRIPTION}", substring_description)
                .replace("{TITLE}", substring_title);
    }
    @Step("Getting result '{substring_title}' and '{substring_description}'of element for MW")
    private static String getResultSearchElementByTwoSubstringsMW(String substring_title, String substring_description) {
        return SEARCH_RESULT_BY_NAME_AND_DESCRIPTION_TPL_MW
                .replace("{DESCRIPTION}", substring_description)
                .replace("{TITLE}", substring_title);
    }

    /*TEMPLATES METHODS*/
    @Step("Initializing search field")
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

    @Step("Waiting for appearing of cancel button")
    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(
                SEARCH_CANCEL_BUTTON,
                "Cannot find cancel button",
                15
        );
    }

    @Step("Waiting for disappearing of cancel button")
    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(
                SEARCH_CANCEL_BUTTON,
                "Search cancel button is still present",
                15
        );
    }

    @Step("Try to click to cancel search")
    public void clickCancelSearch() {
        this.waitForElementAndClick(
                SEARCH_CANCEL_BUTTON,
                "Cannot find and click search cancel button",
                10
        );
    }

    @Step("Input '{search_line}' in search line")
    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(
                SEARCH_INPUT,
                search_line,
                "Cannot find any type into search input",
                15

        );
    }

    @Step("Waiting for search result of element with '{substring}'")
    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(
                search_result_xpath,
                "Cannot find search result with substring " + substring);
    }

    @Step("Waiting for search results and select an article by '{substring}' in article title")
    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(
                search_result_xpath,
                "Cannot find and click search result with substring " + substring,
                10);
    }

    @Step("Getting an amount of found articles")
    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15
        );
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    @Step("Waiting for empty results label")
    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty result element",
                15
        );
    }

    @Step("Making sure there are no results for the search")
    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(
                SEARCH_RESULT_ELEMENT,
                "We supposed not to find any results"
        );
    }

    @Step("Initiating search field line")
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

    @Step("Making sure element by '{title}' and '{description}' is on the page (for Android)")
    public void waitForElementByTitleAndDescription(String title, String description) {
        this.waitForElementPresent(
                getResultSearchElementByTwoSubstrings(title, description),
                "Cannot find elements with title '" + title + "' and description '" + description + "'",
                30
        );
    }

    @Step("Making sure element by '{title}' and '{description}' is on the page (for iOS)")
    public void waitForElementByTitleAndDescriptionIOS(String title, String description) {
        this.waitForElementPresent(
                getResultSearchElementByTwoSubstringsIOS(title, description),
                "Cannot find elements with title '" + title + "' and description '" + description + "'",
                30
        );
    }

    @Step("Making sure element by '{title}' and '{description}' is on the page (for MW)")
    public void waitForElementByTitleAndDescriptionMW(String title, String description) {
        this.waitForElementPresent(
                getResultSearchElementByTwoSubstringsMW(title, description),
                "Cannot find elements with title '" + title + "' and description '" + description + "'",
                30
        );
    }

}

