package lib.Tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase {
    @Test
    public void testChangingScreenOrientationAndRevert() {
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
    public void testReturningAppFromBackground() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Java (programming language)");
        this.backgroundApp(2);
        searchPageObject.waitForSearchResult("Java (programming language)");
    }
}
