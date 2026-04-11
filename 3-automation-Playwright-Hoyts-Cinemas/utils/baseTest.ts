import {test as base, Browser, BrowserContext, Page} from '@playwright/test'
import { mainPageHoytsCinemas } from '../pages/pageObjects/mainPageHoytsCinemas';
export {expect} from '@playwright/test';

export type TestFixtures = {
    context: BrowserContext
    page : Page;
    mainPage: mainPageHoytsCinemas;
}


export const test = base.extend<TestFixtures>({
    context: async ({browser}, use) =>{
        const context = await browser.newContext();
        await use(context);
        await context.close();
    },
    page: async ({context}, use) =>{
        const page = await context.newPage();
        page.setDefaultNavigationTimeout(15000);
        await use(page);
    },

    mainPage: async ({page}, use) => {
        const mainPage = new mainPageHoytsCinemas(page);
        await mainPage.goTo();
        await use(mainPage);
    }

});
test.setTimeout(30000);