import { Page, Locator } from "@playwright/test";
import { abstractComponentsHoytsShop } from "../abstractComponents/abstractComponentsHoytsShop";
export class mainPageHoytsShop extends abstractComponentsHoytsShop {

    constructor(page: Page){
        super(page);
    }
    // ========================= Go to URL =======================
    async goTo(){
        await this.page.goto('https://shop.hoyts.com.au/', {waitUntil: "load", timeout: 15000});
    }

    // ========================= Pick Most Popular Gift Cards =======================
    async pickMostPopularGiftCard( title: string, value: string){
        await this.page.evaluate(()=>window.scrollBy(0,100));
        const container = this.productContainer(title);
        if(await container.count() ===0){
            throw new Error (`Gift Card '${title}' not found in the most popular gift cards`);
        }

        await container.first().waitFor({ state: 'visible', timeout: 5000 });
        await this.selectGiftCard(container, value);
    }

}