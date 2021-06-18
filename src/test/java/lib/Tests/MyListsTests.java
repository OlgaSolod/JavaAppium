package lib.Tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;
@Epic("Tests of saved articles")
public class MyListsTests extends CoreTestCase {
    private static final String name_of_folder = "Learning programming";
    private static final String login = "MyOwnTestAccount";
    private static final String password = "-mk6$x-R8z&K$SH";


    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article"), @Feature(value = "MyLists")})
    @DisplayName("Save first article to my list and delete it then")
    @Description("We find and open an article, add it to my_list, check if it really added, and delete it then, and check, that article is not in our list")
    @Severity(value = SeverityLevel.NORMAL)
    public void testSaveFirstArticleToMyList() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String article_title = articlePageObject.getArticleTitle();
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(name_of_folder);
        } else {
            articlePageObject.addArticlesToMySaved();
        }
        if (Platform.getInstance().isMW()) {
            AuthorizationPageObject auth = new AuthorizationPageObject(driver);
            auth.clickAuthButton();
            auth.enterLoginData(login, password);
            auth.submitForm();

            articlePageObject.waitForTitleElement();

            Assert.assertEquals("We are not on the same page after login",
                    article_title,
                    articlePageObject.getArticleTitle());

            articlePageObject.addArticlesToMySaved();
        }

        articlePageObject.closeArticle();
        if (Platform.getInstance().isIOS()) {
            articlePageObject.tapCancelButtonInSearch();
        }

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();
        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {

            myListsPageObject.openFolderByName(name_of_folder);
        } else if (Platform.getInstance().isIOS()) {
            myListsPageObject.tapCloseButtonInOverlayScreenInSaved();
        }
        myListsPageObject.swipeArticleToDelete(article_title);
    }

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article"), @Feature(value = "MyLists")})
    @DisplayName("Save two articles to my list and delete one of them")
    @Description("We find and open an article, add it to my_list, then add next article, check if both of them are really added, and delete one of them, and check, that one article was deleted and another article was left in our list")
    @Severity(value = SeverityLevel.NORMAL)
    public void testSavedTwoArticlesToMyListsAndDeleteOne() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        String article_title_first;
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.waitForTitleElement();
            article_title_first = articlePageObject.getArticleTitle();
            articlePageObject.addArticleToMyList(name_of_folder);
            articlePageObject.closeArticle();
        } else if (Platform.getInstance().isIOS()) {
            article_title_first = articlePageObject.getArticleTitle();
//            articlePageObject.waitForNavigationTypeElement();
//            article_title_first = articlePageObject.getElementTypeNavigationBar();
            articlePageObject.addArticlesToMySaved();
            articlePageObject.closeArticle();
            articlePageObject.tapCancelButtonInSearch();
        } else {
            article_title_first = articlePageObject.getArticleTitle();
            articlePageObject.addArticlesToMySaved();
            AuthorizationPageObject auth = new AuthorizationPageObject(driver);
            auth.clickAuthButton();
            auth.enterLoginData(login, password);
            auth.submitForm();

            articlePageObject.waitForTitleElement();

            Assert.assertEquals("We are not on the same page after login",
                    article_title_first,
                    articlePageObject.getArticleTitle());

            articlePageObject.addArticlesToMySaved();
        }

        //Добавляем в избранное вторую статью
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("avaScript");
        String article_title_second;
        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            searchPageObject.clickByArticleWithSubstring("JavaScript");
            articlePageObject.waitForTitleElement();
            article_title_second = articlePageObject.getArticleTitle();
            articlePageObject.addArticleToExistingList();
            articlePageObject.closeArticle();
        } else if (Platform.getInstance().isIOS()) {
//            searchPageObject.clickByArticleWithSubstring("JavaScript");
            articlePageObject.waitForNavigationTypeElement();
            article_title_second = articlePageObject.getElementTypeNavigationBar();
            articlePageObject.addArticlesToMySaved();
            articlePageObject.closeArticle();
            articlePageObject.tapCancelButtonInSearch();
        } else {
            navigationUI.clickToReturnToMainPage();
            searchPageObject.initSearchInput();
            searchPageObject.typeSearchLine("Java");
            searchPageObject.clickByArticleWithSubstring("igh-level programming language");
            article_title_second = articlePageObject.getArticleTitle();
            articlePageObject.addArticlesToMySaved();
        }
        //Открываем список сохраненных статей
        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isMW()) {
            navigationUI.openNavigation();
            navigationUI.clickMyLists();
        } else if (Platform.getInstance().isAndroid()) {
            navigationUI.clickMyLists();
            myListsPageObject.openFolderByName(name_of_folder);
        } else {
            navigationUI.clickMyLists();
            myListsPageObject.tapCloseButtonInOverlayScreenInSaved();
        }
        //Удаляем первую статью
        myListsPageObject.swipeArticleToDelete(article_title_first);

        //проверяем вторую статью
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
            articlePageObject.waitForTitleElement();
            myListsPageObject.clickOnHeaderOfArticle(article_title_second);
            Assert.assertEquals("There is no expected article " + article_title_second,
                    "JavaScript",
                    article_title_second);
        }
    }
}