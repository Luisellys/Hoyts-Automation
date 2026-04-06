import { Page, Locator } from "playwright";
import { abstractComponentsHoytsCinemas } from "../abstractComponents/abstractComponentsHoytsCinemas";
export class mainPageHoytsCinemas extends abstractComponentsHoytsCinemas{
    constructor(page: Page){
        super(page);
    }

// ========================= Locators =======================


// ========================= Action Methods =======================

async goTo(){
    await this.page.goto('https://www.hoyts.co.nz/', {waitUntil: 'load', timeout: 15000});
}


}
