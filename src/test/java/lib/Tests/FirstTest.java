package lib.Tests;

import lib.CoreTestCase;

import lib.ui.*;
import org.junit.Assert;

import org.junit.Test;
import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;

import java.util.List;

public class FirstTest extends CoreTestCase {
    private MainPageObject MainPageObject;

    protected void setUp() throws Exception {
        super.setUp();
        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testInputFieldContainsText() {

        MainPageObject.waitForElementAndClick("org.wikipedia:id/search_container",
                "Cannot find an element Search_Wikipedia on the page",
                5);
        MainPageObject.waitForElementPresent("org.wikipedia:id/search_src_text",
                "Cannot find a search input",
                15
        );

        MainPageObject.assertElementHasText(
                "org.wikipedia:id/search_src_text",
                "Search…",
                "This text hasn't found in this field!"
        );
    }


    @Test
    public void testCheckingWordsInSearch() {
        MainPageObject.waitForElementAndClick("org.wikipedia:id/search_container",
                "Cannot find an element Search_Wikipedia on the page",
                5);
        String text_for_search = "Cat";
        MainPageObject.waitForElementAndSendKeys(
                "org.wikipedia:id/search_src_text",
                text_for_search,
                "Cannot send the key.",
                15
        );
        MainPageObject.waitForElementPresent(//ждем, что результаты поиска появились
                "org.wikipedia:id/search_results_list",
                "There is no search results on the page",
                15
        );

        List<WebElement> elements = driver.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"));
        //узнаем количество элементов, содержащих title (здесь получила 6)
        for (int i = 0; i < elements.size(); i++) {//по замыслу, цикл должен перебрать эти элементы на странице.
            String title = elements.get(i).getAttribute("text").toLowerCase();//игнорируем регистр, в котором написан заголовок
            Assert.assertTrue("The word " + text_for_search + "isn't contain in all results of the page",
                    title.contains(text_for_search.toLowerCase()));//проверяю, содержится ли слово из поиска в title
        }
    }


}

