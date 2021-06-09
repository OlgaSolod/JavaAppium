package lib.ui.mobile_web;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWNavigationUI extends NavigationUI {
    static {
        MY_LISTS_LINK = "css:a[data-event-name='menu.unStar']";
        OPEN_NAVIGATION = "css:#mw-mf-main-menu-button";
        RETURN_TO_MAIN_PAGE = "css:.branding-box";
    }

    public MWNavigationUI(RemoteWebDriver driver) {
        super(driver);
    }
}
