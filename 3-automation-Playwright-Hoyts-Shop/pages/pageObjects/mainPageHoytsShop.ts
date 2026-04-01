import { Page, Locator } from "@playwright/test";
import { abstractComponentsHoytsShop } from "../abstractComponents/abstractComponentsHoytsShop";
export class mainPageHoytsShop extends abstractComponentsHoytsShop {

    constructor(page: Page){
        super(page);
    }
    // ========================= Go to URL =======================
    async goTo(){
        await this.page.goto('https://shop.hoyts.com.au/');
    }

    // ========================= Pick Most Popular Gift Cards =======================
    async pickMostPopularGiftCard( title: string, value: string){
        const container = this.productContainer(title);
        if(await container.count() ===0){
            throw new Error (`Gift Card '${title}' not found in the most popular gift cards`);
        }
        await this.selectGiftCard(container, value);
    }

}