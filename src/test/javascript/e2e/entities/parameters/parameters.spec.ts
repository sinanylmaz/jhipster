import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ParametersComponentsPage from './parameters.page-object';
import ParametersUpdatePage from './parameters-update.page-object';
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

describe('Parameters e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let parametersComponentsPage: ParametersComponentsPage;
  let parametersUpdatePage: ParametersUpdatePage;

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
    parametersComponentsPage = new ParametersComponentsPage();
    parametersComponentsPage = await parametersComponentsPage.goToPage(navBarPage);
  });

  it('should load Parameters', async () => {
    expect(await parametersComponentsPage.title.getText()).to.match(/Parameters/);
    expect(await parametersComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Parameters', async () => {
    const beforeRecordsCount = (await isVisible(parametersComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(parametersComponentsPage.table);
    parametersUpdatePage = await parametersComponentsPage.goToCreateParameters();
    await parametersUpdatePage.enterData();

    expect(await parametersComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(parametersComponentsPage.table);
    await waitUntilCount(parametersComponentsPage.records, beforeRecordsCount + 1);
    expect(await parametersComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await parametersComponentsPage.deleteParameters();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(parametersComponentsPage.records, beforeRecordsCount);
      expect(await parametersComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(parametersComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
