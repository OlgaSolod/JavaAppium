package lib.Tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
    private static final String name_of_folder = "Learning programming";

    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Java (programming language)");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String article_title = articlePageObject.getArticleTitle();
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(name_of_folder);
        } else {
            articlePageObject.addArticlesToMySaved();
        }
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.closeArticle();
        } else {
            articlePageObject.closeArticle();
            articlePageObject.tapCancelButtonInSearch();
        }

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();
        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {

            myListsPageObject.openFolderByName(name_of_folder);
        } else {
            myListsPageObject.tapCloseButtonInOverlayScreenInSaved();
        }
        myListsPageObject.swipeArticleToDelete(article_title);
    }

    @Test
    public void testSavedTwoArticlesToMyListsAndDeleteOne() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Java (programming language)");
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        String article_title_first;
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.waitForTitleElement();
            article_title_first = articlePageObject.getArticleTitle();
            articlePageObject.addArticleToMyList(name_of_folder);
            articlePageObject.closeArticle();
        } else {
            articlePageObject.waitForNavigationTypeElement();
            article_title_first = articlePageObject.getElementTypeNavigationBar();
            articlePageObject.addArticlesToMySaved();
            articlePageObject.closeArticle();
            articlePageObject.tapCancelButtonInSearch();
        }
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("JavaScript");
        String article_title_second;
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.waitForTitleElement();
            article_title_second = articlePageObject.getArticleTitle();
            articlePageObject.addArticleToExistingList();
            articlePageObject.closeArticle();
        } else {
            articlePageObject.waitForNavigationTypeElement();
            article_title_second = articlePageObject.getElementTypeNavigationBar();
            articlePageObject.addArticlesToMySaved();
            articlePageObject.closeArticle();
            articlePageObject.tapCancelButtonInSearch();
        }
        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();
        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(name_of_folder);
        } else {
            myListsPageObject.tapCloseButtonInOverlayScreenInSaved();
        }
        myListsPageObject.swipeArticleToDelete(article_title_first);
        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.waitForArticleToAppearByTitle(article_title_second);
        }
        if (Platform.getInstance().isIOS()) {
            myListsPageObject.waitForElementAndClick(
                    article_title_second,
                    "Cannot find the article " + article_title_second,
                    15
            );
            String element_type_navigation_bar = articlePageObject.getElementTypeNavigationBar();
            Assert.assertEquals("There is no expected article " + article_title_second,
                    "JavaScript",
                    element_type_navigation_bar);
        } else {
            Assert.assertEquals("There is no expected article " + article_title_second,
                    "JavaScript",
                    article_title_second);
        }
    }
}
