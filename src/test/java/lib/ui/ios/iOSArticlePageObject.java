package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSArticlePageObject extends ArticlePageObject {
    static {
        TITLE = "id:Java (programming language)";
        FOOTER_ELEMENT = "id:View article in browser";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:Save for later";
        CLOSE_ARTICLE_BUTTON = "id:Back";
        CANCEL_SEARCH_BUTTON="id:Cancel";
        ELEMENT_TYPE_NAVIGATION_BAR="id:JavaScript";
    }
    public iOSArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

}
