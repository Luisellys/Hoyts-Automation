import {test, expect} from "../utils/baseTest";
import { mainPageHoytsShop } from "../pages/pageObjects/mainPageHoytsShop";
import testData from "../utils/test-data/giftCards.json"
import { giftCardsPageHoytsShop } from "../pages/pageObjects/giftCardsPageHoytsShop";

testData.mostPopular.forEach((data)=>{
    test(`Pick Most Popular Gift Card - ${data.title}`, async({mainPage})=>{
    await mainPage.pickMostPopularGiftCard(data.title, data.value);
    const chosenGiftCard = await mainPage.confirmGiftCard(data.title);
    expect(chosenGiftCard?.trim()).toContain(data.title);

});

})

testData.giftCardsPage.forEach((data) => {
    test(`Pick from Gift Cards Page - ${data.title}`, async({mainPage, page})=>{
    await mainPage.goToGiftCards();
    const giftCardPage = new giftCardsPageHoytsShop(page);
    await giftCardPage.pickGiftCard(data.title, data.value);
    const chosenGiftCard = await giftCardPage.confirmGiftCard(data.title);
    expect(chosenGiftCard?.trim()).toContain(data.title);

});

})

