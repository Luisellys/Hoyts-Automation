package QA_Assessment.hoyts_qa_test;

import org.testng.Assert;
import org.testng.annotations.Test;

import testComponents.baseTest;

public class searchMovie extends baseTest {

    @Test
    public void searchValidMovieTest() {

        String validMovie = "Avatar";

        mainPage.searchMovie(validMovie);

        Boolean match = mainPage.confirmMovie(validMovie);

        Assert.assertTrue(match);
    }

    @Test
    public void searchInvalidMovieTest() {

        String invalidMovie = "h@kun@M@t@t@";
        String expectedMessage = "Sorry, we couldn't find any results";

        mainPage.searchMovie(invalidMovie);

        String actualMessage = mainPage.confirmNoResultsMessage();

        Assert.assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
    }
}