import { test as base, Browser, BrowserContext, Page } from '@playwright/test';
import { mainPageHoytsShop } from '../pages/pageObjects/mainPageHoytsShop';
export { expect } from '@playwright/test';
export type TestFixtures = {
  page: Page;
  context: BrowserContext;
  mainPage: mainPageHoytsShop;
};

export const test = base.extend<TestFixtures>({
  context: async ({ browser }, use) => {
    const context = await browser.newContext();
    await use(context);
    await context.close();
  },
  page: async ({ context }, use) => {
    const page = await context.newPage();
    page.setDefaultNavigationTimeout(6000);
    await use(page);
  },
  mainPage: async ({ page }, use) => {
    const mainPage = new mainPageHoytsShop(page);
    await mainPage.goTo();
    await use(mainPage);
  }
});
// Set default timeout for **all** tests using this `test` object
test.setTimeout(90000); // 90 seconds