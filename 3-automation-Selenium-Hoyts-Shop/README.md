# Hoyts QA Automation - Selenium + TestNG

This project contains automated UI tests for:
https://shop.hoyts.com.au/

## Prerequisites (Clean Machine Setup)
Install the following tools:
### 1. Java JDK (11 or higher)
Download: https://www.oracle.com/java/technologies/downloads/

- **Set Environment Variables (Windows)**
After installing Java:
1. Open Start Menu
2. Search: "Environment Variables"
3. Click: "Edit the system environment variables"
4. Click "Environment Variables"
5. Click "New" under System variables
6. Add "Variable name": JAVA_HOME
7. Add "Variable value": (example path) C:\Program Files\Java\jdk-11
8. Click "OK"
9. Click "New" under User variables
10. Find and select "Path"
11. Click "Edit"
12. Click "New"
13. Add: %JAVA_HOME%\bin
14. Click OK on all windows

- **Verify Java Installation**
Open Command Prompt:
1. Press Windows + R
2. Type: cmd
3. Press Enter
4. Run:
```bash
   java -version
```
- You should see something like: java version "11.x.x"

### 2. Eclipse
Download: https://www.eclipse.org/downloads/
### 3. TestNG
From Eclipse Market place: 
1. Click on Help
2. Click on "Eclipse Marketplace"
3. Search: "TestNG"
4. Click on "Install" 
### 4. Google Chrome Browser
Download: https://www.google.com/chrome/

##  Project Setup
- **Clone the repository:**
```bash
git clone https://github.com/Luisellys/Hoyts-Automation.git
cd 3-automation-Selenium-Hoyts-Shops
```

- **Import to Eclipse:** 
1. Click on "File"
2. Click on "Import"
3. Click on "General"
4. Click on "Existing Projects into Workspace"
5. Click on "Next"
6. Click on "Select root directory"
7. Browse --> Browse your project
8. Select Folder
9. Select project
10. Click on "Finish"

## Test Scenarios Covered

### Pick a Most Popular Gift Card From Home page
- Search for "Easter E-Gift Card" $50 gift card
- Validate gift card is added to the cart
### Pick a Gift Card From Gift Cards page
- Search for "Birthday E-Gift Card" $250 gift card
- Validate gift card is added to the cart
  
## Framework Design Decisions

- **TestNG** for test structure and execution
- **WebDriverManager** for automatic driver setup
- **Explicit waits** for stability
- **Flexible locators** to handle UI changes


## Run on a Clean Computer (Step-by-Step)

1. Install Java
2. Install Eclipse
3. Install TestNG
4. Install Chrome
5. Clone repository
6. Run on Eclipse: right-click "testng.xml" --> Run as --> 1 TestNG Suite
