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
@Epic("Tests of search")
public class SearchTests extends CoreTestCase {
    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Test of search")
    @Description("We find a search input field, type some word in search line and check, if result of search is on the page")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Cat");
        searchPageObject.waitForSearchResult("omesticated feli");
    }

    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Test of cancel search")
    @Description("We find a search input field, put a cursor on it, and then try to find a cancel button")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testCancelSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Test of amount of founded articles if request was correct")
    @Description("We open search line, type some word and check how many results was returned by this search request")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testAmountOfNotEmptySearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String search_line = "Linkin Park Discography";
        searchPageObject.typeSearchLine(search_line);

        int amount_of_search_results = searchPageObject.getAmountOfFoundArticles();
        Assert.assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
    }

    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Test of amount of founded articles if request was not correct")
    @Description("We open search line, type not existing word and check that no results were returned")
    @Severity(value = SeverityLevel.NORMAL)
    public void testAmountOfEmptySearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String search_line = "asdfwettryh";
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Test of finding articles and cancel search")
    @Description("We open search line, type the word, waiting search results and cancel search")
    @Severity(value = SeverityLevel.NORMAL)
    public void testFindArticlesAndCancelSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Dog");
        searchPageObject.waitForSearchResult("og");
        searchPageObject.clickCancelSearch();
        searchPageObject.initSearchField();
    }

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article")})
    @DisplayName("Finding title in founded article")
    @Description("We open search line,type word, open founded article and check its title")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testFindTitleInArticle() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("ava (programming language)");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement(0);
    }

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article")})
    @DisplayName("Test find three search results with two substrings")
    @Description("We open search line, type word Java, and check if results contains three articles with defined title and description")
    @Severity(value = SeverityLevel.NORMAL)
    public void testFindByTwoSubstringsInThreeSearchResults() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        if (Platform.getInstance().isAndroid()) {
            searchPageObject.waitForElementByTitleAndDescription("Java", "Island of Indonesia");
            searchPageObject.waitForElementByTitleAndDescription("JavaScript", "Programming language");
            searchPageObject.waitForElementByTitleAndDescription("Java (programming language)", "Object-oriented programming language");
        } else if (Platform.getInstance().isIOS()) {
            searchPageObject.waitForElementByTitleAndDescriptionIOS("Java", "Indonesian island");
            searchPageObject.waitForElementByTitleAndDescriptionIOS("JavaScript", "High-level programming language");
            searchPageObject.waitForElementByTitleAndDescriptionIOS("Java (programming language)", "Object-oriented programming language");
        } else {
            searchPageObject.waitForElementByTitleAndDescriptionMW("ava", "ndonesian island");
            searchPageObject.waitForElementByTitleAndDescriptionMW("avaScript", "igh-level programming language");
            searchPageObject.waitForElementByTitleAndDescriptionMW("ava (programming language)", "bject-oriented programming language");
        }
    }
}
