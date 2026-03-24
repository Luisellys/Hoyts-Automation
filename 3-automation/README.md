# Hoyts QA Automation - Selenium + TestNG

This project contains automated UI tests for:
https://www.hoyts.co.nz/

## Prerequisites (Clean Machine Setup)
Install the following tools:
### 1. Java JDK (11 or higher)
Download: https://www.oracle.com/java/technologies/downloads/
Verify:
java -version
### 2. Eclipse
Download: https://www.eclipse.org/downloads/
### 3. TestNG
From Eclipse Market place: Help --> Eclipse Marketplace --> search: "TestNG" --> Install 
### 4. Google Chrome Browser
Download: https://www.google.com/chrome/

##  Project Setup
####Clone the repository:
git clone https://github.com/Luisellys/ecoPortal-QA-Assessment.git
cd 3-automation

#####Import to Eclipse: 
from Eclipse --> File --> Import --> General --> Existing Projects into Workspace --> Next --> Select root directory --> Browse --> Browse your project --> Select Folder --> Select project --> Finish.

## Test Scenarios Covered

### Valid Movie Search
- Search for "Avatar"
- Validate results are displayed
### Invalid Movie Search
- Search random string
- Validate "Sorry, we couldn't find any results" message appears
  
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
6. Run: right-click "testng.xml" --> Run as --> 1 TestNG Suite
