import {test, expect} from "../utils/baseTest";
import { mainPageHoytsShop } from "../pages/pageObjects/mainPageHoytsShop";
import testData from "../utils/test-data/giftCards.json"
import { giftCardsPageHoytsShop } from "../pages/pageObjects/giftCardsPageHoytsShop";

test('Pick Most Popular Gift Card', async({mainPage})=>{
    const data = testData.mostPopular[0]
    await mainPage.pickMostPopularGiftCard(data.title, data.value);
    const chosenGiftCard = await mainPage.confirmGiftCard(data.title);
    expect(chosenGiftCard?.trim()).toContain(data.title);

});

test('Pick from Gift Cards Page', async({mainPage, page})=>{
    const data = testData.giftCardsPage[1]
    await mainPage.goToGiftCards();
    const giftCardPage = new giftCardsPageHoytsShop(page);
    await giftCardPage.pickGiftCard(data.title, data.value);
    const chosenGiftCard = await giftCardPage.confirmGiftCard(data.title);
    expect(chosenGiftCard?.trim()).toContain(data.title);

});
