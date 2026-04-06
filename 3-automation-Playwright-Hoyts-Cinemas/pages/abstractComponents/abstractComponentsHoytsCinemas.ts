import { Page, Locator, expect} from "@playwright/test";
export class abstractComponentsHoytsCinemas {
    readonly page: Page;
    constructor(page: Page){
        this.page = page;
    }
// ========================= Locators ==========================
// == Desktop ==================================================
searchbarDesktop: string = 'input.header__search-input';
resultsDesktop: string = '.header__search-item';
resultTitleDesktop: string = '.header__search-title';
resultDescriptionDesktop: string = '.header__search-description';
noResultsMessageDesktop: string ='.header__search-heading';
// == Mobile ===================================================
hamburgerMenu: string = '.header__hamburger-icon';
openedMenu: string = 'nav#dash__nav';
searchbarMobile: string = 'input.dash__search-input';
resultsMobile: string = '.dash__search-item';
resultTitleMobile: string = '.dash__search-title';
resultDescriptionMobile: string = '.dash__search-description';
noResultsMessageMobile: string = '.dash__search-heading';

// ============================ Utilities =========================
// == Viewport Detection ==========================================
async hasHamburgerMenu(): Promise<boolean>{
    const viewportWidth = await this.page.evaluate(() => window.innerWidth);
    console.log('Viewport Width: ', viewportWidth);
    return viewportWidth <= 1240;
    }

// ========================= Action Methods =======================
// == Search Movie ================================================

async searchMovie(movieName: string){
    let search;

    if(await this.hasHamburgerMenu()){
        try{
            await this.page.locator(this.hamburgerMenu).waitFor({state : "visible", timeout : 10000});
            await this.page.locator(this.hamburgerMenu).click();
            await this.page.locator(this.openedMenu).waitFor({state : "visible", timeout : 10000});
        } catch(exeption){}
        search = this.page.locator(this.searchbarMobile);    

    } else{
        search = this.page.locator(this.searchbarDesktop);
        await search.waitFor({ state: "visible" });
        await search.click();
    }
    await search.type(movieName, {delay : 400});
    await search.press('Enter');
}

// == Confirm Movie ================================================
async confirmMovie(movieName: string):Promise<boolean>{
    const searchText = movieName.toLowerCase();
    const hasHamburgerMenu = await this.hasHamburgerMenu();
    const results = hasHamburgerMenu? this.resultsMobile : this.resultsDesktop;
    const title = hasHamburgerMenu? this.resultTitleMobile : this.resultTitleDesktop;
    const description = hasHamburgerMenu? this.resultDescriptionMobile : this.resultDescriptionDesktop;

    const movies = this.page.locator(results);
    await movies.first().waitFor({ state: "visible" }).catch(() => {});

    const count = await movies.count();
    if (count === 0) return false;

    const titles = await movies.locator(title).allTextContents();
    const desc = await movies.locator(description).allTextContents();

    const movieResults =[...titles, ...desc];

    return movieResults.map(text => text.toLowerCase()).some(text => text.includes(searchText));

}

// == Confirm No Results Message ===================================
async confirmNoResults(): Promise<string>{
    const hasHamburgerMenu = await this.hasHamburgerMenu();
    const message = hasHamburgerMenu? this.noResultsMessageMobile : this.noResultsMessageDesktop;
    const messageLocator = this.page.locator(message);
    await messageLocator.waitFor({state : "visible", timeout : 5000});
    const text = (await messageLocator.textContent()).trim();
    
    if(!text){
        throw new Error('No error message defined');
    }
    return text;
}


}