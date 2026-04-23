import { test, expect } from '../utils/baseTest'
import { mainPageHoytsCinemas } from '../pages/pageObjects/mainPageHoytsCinemas'
import testData from "../utils/test-data/searchMovies.json"

testData.validMovies.forEach((data)=>{
    test(`Valid Movie - ${data.name}`, async({mainPage})=>{
    
    await mainPage.searchMovie(data.name);
    const failed = await mainPage.confirmMovie(data.name);
    expect(failed).toBe(0);

})

})

testData.invalidMovies.forEach((data)=> {
    test(`Invalid Movie - ${data.name}`, async({mainPage})=>{
    await mainPage.searchMovie(data.name);
    const confirm = await mainPage.confirmNoResults();
    expect(confirm).toBe(data.expectedMessage);

})

})
