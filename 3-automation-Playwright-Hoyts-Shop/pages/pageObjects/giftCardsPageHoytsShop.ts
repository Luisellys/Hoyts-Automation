import { Page, Locator } from "@playwright/test";
import { abstractComponentsHoytsShop } from "../abstractComponents/abstractComponentsHoytsShop";

export class giftCardsPageHoytsShop extends abstractComponentsHoytsShop {

    constructor(page: Page){
        super(page);
    }
    // ========================= Pick Gift Card =======================
    async pickGiftCard( title: string, value: string){
        const container = await this.scrollUntilProductVisible(title);
        await this.selectGiftCard(container, value);
    }
}


