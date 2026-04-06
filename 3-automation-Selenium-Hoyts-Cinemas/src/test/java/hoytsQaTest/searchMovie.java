package hoytsQaTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import testComponents.hoytsCinemaBaseTest;

public class searchMovie extends hoytsCinemaBaseTest {

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