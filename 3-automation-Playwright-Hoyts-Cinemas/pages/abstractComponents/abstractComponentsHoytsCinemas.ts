import { Page, Locator, expect} from "@playwright/test";
export class abstractComponentsHoytsCinemas {
    readonly page: Page;
    private mobileView?: boolean;
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

    if(this.mobileView === undefined){
        const viewportWidth = await this.page.evaluate(() => window.innerWidth);
        console.log('Viewport Width: ', viewportWidth);
        this.mobileView = viewportWidth <= 1240
    }
    return this.mobileView ;

    }

// ========================= Action Methods =======================
// == Search Movie ================================================

async searchMovie(movieName: string){
    let search;

    if(await this.hasHamburgerMenu()){
        try{
            await this.page.locator(this.hamburgerMenu).waitFor({state : "visible"});
            await this.page.locator(this.hamburgerMenu).click();
            await this.page.locator(this.openedMenu).waitFor({state : "visible"});
        } catch(exeption){}
        search = this.page.locator(this.searchbarMobile);
        await search.waitFor({state : 'visible'});
    

    } else{
        search = this.page.locator(this.searchbarDesktop);
        await search.waitFor({ state: "visible"});

    }
    const isMobile = await this.hasHamburgerMenu();
    const results = isMobile? this.resultsMobile : this.resultsDesktop;
    await search.type(movieName, {delay : 700});
    await search.press('Enter');
}

// == Confirm Movie ================================================
async confirmMovie(movieName: string):Promise<number>{
    const isMobile = await this.hasHamburgerMenu();
    const results = isMobile? this.resultsMobile : this.resultsDesktop;
    const title = isMobile? this.resultTitleMobile : this.resultTitleDesktop;
    const description = isMobile? this.resultDescriptionMobile : this.resultDescriptionDesktop;

    const movies = this.page.locator(results);
    await expect.poll(async () => movies.count()).toBeGreaterThan(0);
    const count = await movies.count();

    let failed = 0;
    const search = movieName.toLowerCase();

    for(let i=0; i<count; i++){
        const movie = movies.nth(i);
        const titleText = (await movie.locator(title).textContent() ?? '').toLowerCase(); 
        const descText = (await movie.locator(description).textContent() ?? '').toLowerCase();

        const match = titleText.includes(search) ||
        descText.includes(search);

        console.log(`Result ${i + 1}: ${match ? 'matches' : 'does not match'}`);

        if(!match) failed++;        
    }
    console.log(`${count - failed} matches out of: ${count} - ${failed} mismatches`);

    return failed;
}

// == Confirm No Results Message ===================================
async confirmNoResults(): Promise<string>{
    const hasHamburgerMenu = await this.hasHamburgerMenu();
    const message = hasHamburgerMenu? this.noResultsMessageMobile : this.noResultsMessageDesktop;
    const messageLocator = this.page.locator(message);
    await messageLocator.waitFor({state : "visible"});
    const text = (await messageLocator.textContent() || '').trim();
    
    if(!text){
        throw new Error('No error message defined');
    }
    return text;
}


}