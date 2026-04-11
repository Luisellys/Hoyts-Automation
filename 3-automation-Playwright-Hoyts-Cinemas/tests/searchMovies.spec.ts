import { test, expect } from '../utils/baseTest'
import { mainPageHoytsCinemas } from '../pages/pageObjects/mainPageHoytsCinemas'

test('Valid Movie', async({mainPage})=>{
    const movie = 'Avatar';
    await mainPage.searchMovie(movie);
    const failed = await mainPage.confirmMovie(movie);
    expect(failed).toBe(0);

})

test('Invalid Movie', async({mainPage})=>{
    const movie = 'H@kun@M@t@t@';
    await mainPage.searchMovie(movie);
    const confirm = await mainPage.confirmNoResults();
    const errorMessage = "Sorry, we couldn't find any results";
    expect(confirm).toBe(errorMessage);

})