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

    // ========================= Helpers =======================
    // == Safe Click ===========================================
    protected async safeClick(locator: Locator) {
        await locator.waitFor({ state: 'visible', timeout: 15000 });

        try {
            await locator.click({ timeout: 5000 });
        } catch {
            await locator.evaluate(el => el.scrollIntoView({ block: 'center' }));
            await locator.click({ timeout: 5000 });
        }
    }
    // == Scrolling ==============================================
    protected async scrollUntilProductVisible(title: string): Promise<Locator> {
    const container = this.productContainer(title);

    for (let i = 0; i < 15; i++) {

        if (await container.count() > 0 && await container.isVisible()) {
            await container.scrollIntoViewIfNeeded();
            return container;
        }

        await this.page.evaluate(() => window.scrollBy(0, window.innerHeight));
        await this.page.waitForTimeout(500);
    }

    throw new Error(`Gift Card: '${title}' not available`);
    }

    // ========================= Viewport Detection =======================
    async hasHamburgerMenu(): Promise<boolean>{
        const viewportWidth = await this.page.evaluate(() => window.innerWidth);
        console.log('Viewport Width: ', viewportWidth);
        return viewportWidth <= 1278;
    }

    // ========================= Go to GiftCards Page =======================
    async goToGiftCards(){
        if (await this.hasHamburgerMenu()) {
            try {
                await this.safeClick(this.page.locator(this.hamburgerMenu));
                await this.page.locator(this.openedMenu).waitFor({ state: 'visible' });
            } catch(e) {}
        }

        await this.safeClick(this.page.locator(this.giftCards));

        await this.page.waitForURL('**/collections/gift-cards', {
            waitUntil: 'load',
            timeout: 30000
        });

        const firstProduct = this.page.locator('div.product-item').first();
        await firstProduct.waitFor({ state: "visible", timeout: 30000 });

        await this.page.evaluate(() => window.scrollBy(0, 100));
    }

    // ========================= Product Container =======================
    protected productContainer(title: string){
        return this.page.locator(`//main[@id='MainContent']//div[contains(@class,'product-item') 
        and .//a[contains(@title,'${title}')]]`).first();
    }

    // ========================= Locator Helpers =======================
    protected dropdownSelector(container: Locator){
        return container.locator('div[data-label="Value:"]');
    }

    protected giftCardAmountSelector(container: Locator, value: string){
        return container.locator(
            'ul[class*="options"] >> li[class*="option"]',
            { hasText: `$${value}` }
        );
    }

    protected addToCartButtonSelector(container: Locator){
        return container.locator('button[type="submit"]');
    }

    // ========================= Select Gift Cards =======================
    async selectGiftCard(container: Locator, value: string) {

        await container.scrollIntoViewIfNeeded({timeout: 15000});

        if (await container.locator('select.select').isVisible()) {

            const select = container.locator('select.select');
            await select.waitFor({ state: 'attached' });

            await select.selectOption({ value: `$${value}|1` });

        } else {

            const dropdown = this.dropdownSelector(container);
            await this.safeClick(dropdown);

            const amount = this.giftCardAmountSelector(container, value);
            await amount.waitFor({ state: 'visible', timeout: 15000 });

            await amount.click();
        }

        const cart = this.addToCartButtonSelector(container);
        await this.safeClick(cart);
    }

    // ========================= Confirm GiftCard =======================
    async confirmGiftCard(title: string): Promise<string | null>{

        const hasHamburgerMenu = await this.hasHamburgerMenu();
        const cartSelector = hasHamburgerMenu ? this.cartMobile : this.cartDesktop;

        const cart = this.page.locator(cartSelector);
        await this.safeClick(cart);

        await this.page.locator(this.openedCart).waitFor({
            state: "visible",
            timeout: 15000
        });

        const cartItem = this.page.locator(
            `//section[contains(@class, 'minicart')] //a[contains(normalize-space(), '${title}')]`
        );

        await cartItem.waitFor({ state: "visible", timeout: 15000 });

        const text = await cartItem.textContent();

        if (!text?.trim()) {
            throw new Error(`Gift Card: '${title}' not found in cart`);
        }

        return text;
    }
}