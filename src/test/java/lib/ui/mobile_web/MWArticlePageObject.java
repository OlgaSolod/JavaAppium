package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {
    static {
        TITLE = "css:#content h1";
        FOOTER_ELEMENT = "css:minerva-footer";
        OPTIONS_BUTTON = "css:#page-actions li#ca-watch";
    }

    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
