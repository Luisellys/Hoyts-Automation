import { defineConfig, devices } from '@playwright/test';

/**
 * Read environment variables from file.
 * https://github.com/motdotla/dotenv
 */
// import dotenv from 'dotenv';
// import path from 'path';
// dotenv.config({ path: path.resolve(__dirname, '.env') });

/**
 * See https://playwright.dev/docs/test-configuration.
 */
export default defineConfig({
  testDir: './tests',
  
  /* Run tests in files in parallel */
  fullyParallel: true,
  /* Fail the build on CI if you accidentally left test.only in the source code. */
  forbidOnly: !!process.env.CI,
  /* Retry on CI only */
  retries: process.env.CI ? 2 : 1,
  /* Opt out of parallel tests on CI. */
  workers: process.env.CI ? 1 : undefined,
  /* Reporter to use. See https://playwright.dev/docs/test-reporters */
  reporter: 'html',
    /* Top-level test timeout (applies to each test) */
  timeout: 60000, // 60 seconds per test
  /* Shared settings for all the projects below. See https://playwright.dev/docs/api/class-testoptions. */
  use: {
    /* Base URL to use in actions like `await page.goto('')`. */
    // baseURL: 'http://localhost:3000',
    headless: false,
    viewport: { width: 900, height: 800 },
    actionTimeout: 10000,
    /* Collect trace when retrying the failed test. See https://playwright.dev/docs/trace-viewer */
    trace: 'on-first-retry',
    screenshot: 'only-on-failure',
    /* Run tests slowed down for debugging purposes*/
    launchOptions: {
   // slowMo: 1000, // 1 second delay per action
  },
  },
  

  /* Configure projects for major browsers */
  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },
    {
      name: 'chromium-smallWindow',
      use: { ...devices['Desktop Chrome'], viewport: { width: 1277, height: 800 } },
      
    },

    {
      name: 'firefox',
      use: { ...devices['Desktop Firefox'] },
    },
        {
      name: 'firefox-smallWindow',
      use: { ...devices['Desktop Firefox'], viewport: { width: 900, height: 800 } },
      
    },

    {
      name: 'webkit',
      use: { ...devices['Desktop Safari'] },
    },
        {
      name: 'webkit-smallWindow',
      use: { ...devices['Desktop Safari'], viewport: {width: 900, height: 800} },
    },

    /* Test against mobile viewports. */
    {
       name: 'Mobile Chrome',
       use: { ...devices['Pixel 5'] },
     },
     {
       name: 'Mobile Safari',
       use: { ...devices['iPhone 12'] },
     },

    /* Test against branded browsers. */
    {
       name: 'Microsoft Edge',
       use: { ...devices['Desktop Edge'], channel: 'msedge' },
     },
         {
       name: 'Microsoft Edge-smallWindow',
       use: { ...devices['Desktop Edge'], channel: 'msedge', viewport:{width: 800, height: 800} },
     },
     {
       name: 'Google Chrome',
       use: { ...devices['Desktop Chrome'], channel: 'chrome' },
     },
          {
       name: 'Google Chrome-smallWindow',
       use: { ...devices['Desktop Chrome'], channel: 'chrome', viewport: {width: 900, height: 800} },
     },
  ],

  /* Run your local dev server before starting the tests */
  // webServer: {
  //   command: 'npm run start',
  //   url: 'http://localhost:3000',
  //   reuseExistingServer: !process.env.CI,
  // },
});
