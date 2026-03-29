# Hoyts QA Automation - Selenium + TestNG

This project contains automated UI tests for:
https://www.hoyts.co.nz/

## Prerequisites (Clean Machine Setup)
Install the following tools:
### 1. Java JDK (11 or higher)
Download: https://www.oracle.com/java/technologies/downloads/
Verify:
java -version

- **Set Environment Variables (Windows)**
After installing Java:
1. Open Start Menu
2. Search: "Environment Variables"
3. Click: "Edit the system environment variables"
4. Click "Environment Variables"
- **Add JAVA_HOME**
1. Click New under System variables
- Variable name: JAVA_HOME
- Variable value: (example path) C:\Program Files\Java\jdk-11
- **Update Path**
1. Find and select Path → Click Edit
2. Click New and add: %JAVA_HOME%\bin
3. Click OK on all windows

- **Verify Java Installation**
Open Command Prompt:
1. Press Windows + R
2. Type: cmd
3. Press Enter
4. Run: java -version
You should see something like: java version "11.x.x"

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
git clone https://github.com/Luisellys/Hoyts-Automation.git
cd 3-automation-Selenium-Hoyts-Cinemas

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
