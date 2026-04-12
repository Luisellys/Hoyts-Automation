import { Page, Locator } from "@playwright/test";
import { abstractComponentsHoytsShop } from "../abstractComponents/abstractComponentsHoytsShop";
import { error } from "node:console";
export class mainPageHoytsShop extends abstractComponentsHoytsShop {

    constructor(page: Page){
        super(page);
    }
    // ========================= Action Methods =======================

    // == Go to URL ===================================================
    async goTo(){
        await this.page.goto('https://shop.hoyts.com.au/', {waitUntil: "load", timeout: 15000});
    }
    // == Carousel =====================================================
    async scrollInCarousel(title: string, carousel: string){
        const viewport = await this.page.evaluate(() => window.innerWidth);
        if(viewport > 767) return;
        let sectionTitle ='';

        if(carousel === 'gift card'){
            sectionTitle = "Most Popular Gift Cards";
        }else if(carousel === 'voucher'){
            sectionTitle = "Most Popular Vouchers";
        }

        const section = this.page.locator(`//section[.//header[contains(., "${sectionTitle}")]]`);
        await section.waitFor({state : "visible"});

        const product = section
            .locator('.keen-slider a[title]')
            .filter({ hasText: title });

        const rightArrow = section.locator('.keen-slider__arrow--right');

        for (let i = 0; i < 10; i++) {
            const giftCard = product.first();

            if (await giftCard.count() > 0) {
            const isInViewport = await giftCard.evaluate((el) => {
                const rect = el.getBoundingClientRect();
                return rect.left >= 0 && rect.right <= window.innerWidth;
            });

            if (isInViewport) {
                await giftCard.scrollIntoViewIfNeeded();
                return;
            }
            }

            await rightArrow.click();
            await this.page.waitForTimeout(300);
        }

        throw new Error(`Gift Card: ${title} not available`);

    }

    // ========================= Pick Most Popular Gift Cards =======================
    async pickMostPopularGiftCard( title: string, value: string){
        await this.page.evaluate(()=>window.scrollBy(0,100));
        await this.scrollInCarousel(title, 'gift card');
        const container = this.productContainer(title);
        if(await container.count() ===0){
            throw new Error (`Gift Card '${title}' not found in the most popular gift cards`);
        }

        await container.first().waitFor({ state: 'visible', timeout: 5000 });
        await this.selectGiftCard(container, value);
    }

}