package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {
    static {
        TITLE = "css:#content h1";
        FOOTER_ELEMENT = "css:minerva-footer";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://body/div//a[text()='Watch']";
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "xpath://body/div//a[text()='Unwatch']";
    }

    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
