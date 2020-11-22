import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ParametersTypeComponentsPage from './parameters-type.page-object';
import ParametersTypeUpdatePage from './parameters-type-update.page-object';
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

describe('ParametersType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let parametersTypeComponentsPage: ParametersTypeComponentsPage;
  let parametersTypeUpdatePage: ParametersTypeUpdatePage;

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
    parametersTypeComponentsPage = new ParametersTypeComponentsPage();
    parametersTypeComponentsPage = await parametersTypeComponentsPage.goToPage(navBarPage);
  });

  it('should load ParametersTypes', async () => {
    expect(await parametersTypeComponentsPage.title.getText()).to.match(/Parameters Types/);
    expect(await parametersTypeComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete ParametersTypes', async () => {
    const beforeRecordsCount = (await isVisible(parametersTypeComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(parametersTypeComponentsPage.table);
    parametersTypeUpdatePage = await parametersTypeComponentsPage.goToCreateParametersType();
    await parametersTypeUpdatePage.enterData();

    expect(await parametersTypeComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(parametersTypeComponentsPage.table);
    await waitUntilCount(parametersTypeComponentsPage.records, beforeRecordsCount + 1);
    expect(await parametersTypeComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await parametersTypeComponentsPage.deleteParametersType();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(parametersTypeComponentsPage.records, beforeRecordsCount);
      expect(await parametersTypeComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(parametersTypeComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
