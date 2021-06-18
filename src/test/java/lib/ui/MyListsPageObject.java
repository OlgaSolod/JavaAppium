package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;


abstract public class MyListsPageObject extends MainPageObject {
    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            CLOSE_OVERLAY_SYNC_YOUR_SAVED_ARTICLES,
            REMOVE_FROM_SAVED_BUTTON;


    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    /*TEMPLATES*/
    private static String getFolderXPathByName(String name_of_folder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getArticleByTitleTpl(String article_title) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    private static String getSavedArticleXPathByTitle(String article_title) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    private static String getRemoveButtonByTitle(String article_title) {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", article_title);
    }
    /*TEMPLATES*/

    @Step("Opening folder by name '{name_of_folder}'")
    public void openFolderByName(String name_of_folder) {
        String folder_name_xpath = getFolderXPathByName(name_of_folder);
        this.waitForElementAndClick(
                folder_name_xpath,
                "Cannot find folder by name " + name_of_folder,
                15
        );
    }

    @Step("Waiting for article to appear by title '{article_title}'")
    public void waitForArticleToAppearByTitle(String article_title) {
        String article_xpath = getSavedArticleXPathByTitle(article_title);
        this.waitForElementPresent(
                article_xpath,
                "Cannot find article by title " + article_title,
                15
        );
    }

    @Step("Waiting for article to disappear by title '{article_title}'")
    public void waitForArticleToDisappearByTitle(String article_title) {
        String article_xpath = getSavedArticleXPathByTitle(article_title);
        this.waitForElementNotPresent(
                article_xpath,
                "Saved article still present with title " + article_title,
                15
        );
    }

    @Step("Swiping article to delete with title '{article_title}'")
    public void swipeArticleToDelete(String article_title) {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXPathByTitle(article_title);
        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            this.swipeElementToLeft(
                    article_xpath,
                    "Cannot find saved article"
            );
        } else {
            String remove_locator = getRemoveButtonByTitle(article_title);
            this.waitForElementAndClick(
                    remove_locator,
                    "Cannot click button to remove article from saved",
                    10);
        }

        if (Platform.getInstance().isIOS()) {
            this.clickElementToTheRightUpperCorner(article_xpath, "Cannot find saved article");
        }

        if (Platform.getInstance().isMW()) {
            driver.navigate().refresh();
        }
        this.waitForArticleToDisappearByTitle(article_title);
    }

    @Step("Tapping to close button of overlay screen in saved in iOS")
    public void tapCloseButtonInOverlayScreenInSaved() {
        this.waitForElementAndClick(CLOSE_OVERLAY_SYNC_YOUR_SAVED_ARTICLES, "Cannot find 'Close' button on overlay screen 'sync your saved articles'", 10);
    }

    @Step("Clicking on header of article '{article_title}'")
    public void clickOnHeaderOfArticle(String article_title) {
        String header = getArticleByTitleTpl(article_title);
        this.waitForElementAndClick(
                header,
                "Cannot find article with title " + header,
                15
        );
    }
}
