# Hoyts QA Automation - Playwright + TypeScript

This project contains automated UI tests for:
https://shop.hoyts.com.au/
Built using Playwright, TypeScript, and Visual Studio Code

## Prerequisites (Clean Machine Setup)
Install the following tools:
### 1. Node.js (v18 or higher recommended)
Download: https://nodejs.org/
- **Set Environment Variables (Windows)**
After installing Java:
1. Open Start Menu
2. Search: "Environment Variables"
3. Click: "Edit the system environment variables"
4. Click "Environment Variables"
5. Click "New" under System variables
6. Add "Variable name": node
7. Add "Variable value": (example path) C:\Program Files\nodejs
8. Click "OK" on all windows
   
- **Verify Node.js runtime and Node Package Manager (npm) Installation**
Open Command Prompt:
1. Press Windows + R
2. Type: cmd
3. Press Enter
4. Run:
node -v
npm -v
 - If you see version numbers (like v20.x.x), everything is installed correctly
 - If you get “not recognized,” Node.js is not installed

### 2. Visual Studio Code
Download: https://code.visualstudio.com/
Recommended extensions: Playwright Test for VSCode

### 3. Playwright (installed via project)
Playwright will be installed as part of the project setup.

## Project Setup
- **Clone the repository:**
git clone https://github.com/Luisellys/Hoyts-Automation.git
cd 3-automation-Playwright-Hoyts-Shop
- **Import to VSCode:** 
1.  Open Visual Studio Code
2.  Click on "File"
3. Click on "Open Folder..."
4. Browse --> Browse your project
5. Click on "Select Folder"
- **Install Node.js dependencies** *(Open a terminal inside VS Code)*
1. Click on "View"
2. Click on "Terminal" (or press Ctrl + `) *(Terminal path should match your project folder)*
3. Install @playwright/test and any other library the project uses, **Run:** npm install
4. Install Playwright browsers, **Run:** npx playwright install

## Test Scenarios Covered
### Pick Most Popular Gift Card (Home Page)
- Select "Easter E-Gift Card"
- Choose a value (e.g. $150)
- Add to cart
- Validate item is present in cart
### Pick Gift Card from Gift Cards Page
- Navigate to Gift Cards page
- Scroll to locate product (e.g. "Super Mario")
- Select value (e.g. $80)
- Add to cart
- Validate item is present in cart

##  Framework Design Decisions
**Playwright Test Runner** Built-in test runner (no external framework needed)
**Page Object Model (POM)**
*Separation of concerns:*
- mainPageHoytsShop
- giftCardsPageHoytsShop
- abstractComponentsHoytsShop
  
**Cross-Device Strategy**
*Desktop vs Mobile handled dynamically:*
- Custom dropdown (desktop)
- Native <select> (mobile devices)
  
**Responsive Handling**

- Introduced: hasHamburgerMenu() *Based on viewport width (≤ 1278)*
  
**Scrolling Strategy** 

With stable: window.scrollBy()

**Works across:**

- Desktop browsers
- Mobile emulation
- Real device behavior

## Run on a Clean Computer (Step-by-Step)

1. Install Node.js
2. Install Visual Studio Code
3. Clone repository
4. Open VSCode terminal and Install Playwright and browsers
5. Run Tests through terminal:
   
### Run ALL projects

npx playwright test

### Run specific project

**Desktop Browsers (headed/see browser)**

npx playwright test --project=chromium --headed
npx playwright test --project=firefox --headed
npx playwright test --project=webkit --headed

**Small Window (Responsive UI)**

npx playwright test --project=chromium-smallWindow --headed
npx playwright test --project=firefox-smallWindow --headed
npx playwright test --project=webkit-smallWindow --headed
npx playwright test --project="Google Chrome-smallWindow" --headed
npx playwright test --project="Microsoft Edge-smallWindow" --headed

**Mobile Devices**

npx playwright test --project="Mobile Chrome" --headed
npx playwright test --project="Mobile Safari" --headed

**Branded Browsers**

npx playwright test --project="Google Chrome" --headed
npx playwright test --project="Microsoft Edge" --headed


