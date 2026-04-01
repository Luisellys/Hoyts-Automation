import {test, expect} from "../utils/baseTest";
import { mainPageHoytsShop } from "../pages/pageObjects/mainPageHoytsShop";
import { giftCardsPageHoytsShop } from "../pages/pageObjects/giftCardsPageHoytsShop";
import { timeEnd } from "node:console";

test('Pick Most Popular Gift Card', async({mainPage})=>{
    const title = 'Easter E-Gift Card';
    const value = '150';
    await mainPage.pickMostPopularGiftCard(title,value);
    const chosenGiftCard = await mainPage.confirmGiftCard(title);
    expect(chosenGiftCard?.trim()).toContain(title);

});

test('Pick from Gift Cards Page', async({mainPage, page})=>{
    const title = 'Super Mario';
    const value = '80';
    await mainPage.goToGiftCards();
    const giftCardPage = new giftCardsPageHoytsShop(page);
    await giftCardPage.pickGiftCard(title, value);
    const chosenGiftCard = await giftCardPage.confirmGiftCard(title);
    expect(chosenGiftCard?.trim()).toContain(title);

});
