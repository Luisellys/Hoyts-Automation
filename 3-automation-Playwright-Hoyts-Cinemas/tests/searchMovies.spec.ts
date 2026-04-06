import {test, expect} from '@playwright/test'
import { mainPageHoytsCinemas } from '../pages/pageObjects/mainPageHoytsCinemas'

test('Valid Movie', async({page})=>{
    const movie = 'Avatar';
    const mainPage = new mainPageHoytsCinemas(page);
    await mainPage.goTo();
    await mainPage.searchMovie(movie);
    const confirm = await mainPage.confirmMovie(movie);
    expect(confirm).toBe(true);

})