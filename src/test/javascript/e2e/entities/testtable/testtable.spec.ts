import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import TesttableComponentsPage from './testtable.page-object';
import TesttableUpdatePage from './testtable-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('Testtable e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let testtableComponentsPage: TesttableComponentsPage;
  let testtableUpdatePage: TesttableUpdatePage;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();

    await signInPage.username.sendKeys('admin');
    await signInPage.password.sendKeys('admin');
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    testtableComponentsPage = new TesttableComponentsPage();
    testtableComponentsPage = await testtableComponentsPage.goToPage(navBarPage);
  });

  it('should load Testtables', async () => {
    expect(await testtableComponentsPage.title.getText()).to.match(/Testtables/);
    expect(await testtableComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Testtables', async () => {
    const beforeRecordsCount = (await isVisible(testtableComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(testtableComponentsPage.table);
    testtableUpdatePage = await testtableComponentsPage.goToCreateTesttable();
    await testtableUpdatePage.enterData();

    expect(await testtableComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(testtableComponentsPage.table);
    await waitUntilCount(testtableComponentsPage.records, beforeRecordsCount + 1);
    expect(await testtableComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await testtableComponentsPage.deleteTesttable();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(testtableComponentsPage.records, beforeRecordsCount);
      expect(await testtableComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(testtableComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
