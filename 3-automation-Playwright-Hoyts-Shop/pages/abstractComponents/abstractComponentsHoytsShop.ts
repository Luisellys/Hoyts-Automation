import { Page, Locator } from "@playwright/test";
export class abstractComponentsHoytsShop {
    readonly page: Page;

    constructor(page: Page){
        this.page = page;
    }
    // ============================== Locators ==========================
    hamburgerMenu: string = 'span.icon-menu';
    openedMenu: string = 'nav.header__nav';
    giftCards: string = '//span[normalize-space() = "Gift Cards"]';
    cartMobile: string = 'span.header__count';
    cartDesktop: string = 'button.header__cart-btn';
    openedCart: string = 'div.minicart';

    // ========================= Viewport Detection =======================
    async hasHamburgerMenu(): Promise<boolean>{
        const viewportWidth = await this.page.evaluate(()=>(window.innerWidth));
        console.log('Viewport Width: ', viewportWidth);
        return viewportWidth <= 1278;
    }
    // ========================= Go to GiftCards Page =======================
    async goToGiftCards(){
        if(await this.hasHamburgerMenu()){
            try{
                await this.page.locator(this.hamburgerMenu).click();
                await this.page.locator(this.openedMenu).waitFor({state: 'visible'});
            } catch(e){}
        }
        await this.page.locator(this.giftCards).click();
        await this.page.waitForURL('**/collections/gift-cards');
        await this.page.waitForSelector('div.product-item');
    }
      
      // ========================= Scrolling =======================
protected async scrollUntilProductVisible(title: string) {
    const container = this.productContainer(title);

    for (let i = 0; i < 15; i++) {

        if (await container.isVisible()) {
            await container.scrollIntoViewIfNeeded();
            await container.waitFor({ state: 'visible' });
            return container;
        }

        await this.page.evaluate(() => window.scrollBy(0, window.innerHeight));
        await this.page.waitForTimeout(500);
    }

    throw new Error(`Gift Card: '${title}' is not available`);
}
      // ========================= Product Container (Scoped Locator) =======================
    protected productContainer(title: string){
        return this.page.locator(`//main[@id='MainContent']//div[contains(@class,'product-item') 
        and .//a[contains(@title,'${title}')]]`).first();
    }

    // ========================= Locator Helper Methods =======================
    protected dropdownSelector(container: Locator){
        return container.locator('div[data-label="Value:"]');
    }
    protected giftCardAmountSelector(container: Locator, value: string){
        return container.locator('ul[class*="options"] >> li[class*="option"]', { hasText: `$${value}` });
    }
    protected addToCartButtonSelector(container: Locator){
        return container.locator('button[type="submit"]');
          
    }

    // ========================= Select Gift Cards =======================
async selectGiftCard(container: Locator, value: string) {

    if (await container.locator('select.select').isVisible()) {
        const select = container.locator('select.select');
        await select.waitFor({ state: 'attached' });
        await select.selectOption({ value: `$${value}|1` });

    } else {
        const dropdown = this.dropdownSelector(container);
        await dropdown.waitFor({ state: 'visible' });
        await dropdown.click();

        const option = this.giftCardAmountSelector(container, value);
        await option.waitFor({ state: 'visible' });
        await option.click();
    }
        await this.addToCartButtonSelector(container).click();
    }
    // ========================= Confirm GiftCard =======================
    async confirmGiftCard(title: string): Promise<string | null>{
        const hasHamburgerMenu = await this.hasHamburgerMenu();
        const cart = hasHamburgerMenu? this.cartMobile : this.cartDesktop;
        await this.page.locator(cart).click();
        await this.page.locator(this.openedCart).waitFor({state: "visible"});
        const text = await this.page.locator(
            `//section[contains(@class, 'minicart')] //a[contains(normalize-space(), '${title}')]`
            ).textContent();
        if(!text?.trim()){
            throw new Error (`Gift Card: '${title}' not found in cart`);
        }
        return text;
    }
}
