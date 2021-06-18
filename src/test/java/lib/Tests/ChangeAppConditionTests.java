package lib.Tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Epic("Tests of changing app conditions")
public class ChangeAppConditionTests extends CoreTestCase {
    @Test
    @Features(value = {@Feature(value = "Screen orientation")})
    @DisplayName("Change orientation screen and revert")
    @Description("We change orientation screen two times")
    @Severity(value = SeverityLevel.NORMAL)
    public void testChangingScreenOrientationAndRevert() {
        if (Platform.getInstance().isMW()) {
            return;
        }
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Java (programming language)");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        String article_title_before_rotation = articlePageObject.getArticleTitle();
        this.rotateScreenToLandscape();
        String article_title_after_rotation = articlePageObject.getArticleTitle();
        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                article_title_before_rotation,
                article_title_after_rotation
        );
        this.rotateScreenToPortrait();
        String article_title_after_second_rotation = articlePageObject.getArticleTitle();
        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                article_title_before_rotation,
                article_title_after_second_rotation
        );
    }

    @Test
    @Features(value = {@Feature(value = "Background mode")})
    @DisplayName("Return app from background")
    @Description("We put app in background and return from it")
    @Severity(value = SeverityLevel.NORMAL)
    public void testReturningAppFromBackground() {
        if (Platform.getInstance().isMW()) {
            return;
        }
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Java (programming language)");
        this.backgroundApp(2);
        searchPageObject.waitForSearchResult("Java (programming language)");
    }
}
