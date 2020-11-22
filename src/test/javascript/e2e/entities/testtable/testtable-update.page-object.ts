import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class TesttableUpdatePage {
  pageTitle: ElementFinder = element(by.id('elasticExampleApp.testtable.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  paramKeyInput: ElementFinder = element(by.css('input#testtable-paramKey'));
  paramValueInput: ElementFinder = element(by.css('input#testtable-paramValue'));
  parametersTypeInput: ElementFinder = element(by.css('input#testtable-parametersType'));
  descriptionInput: ElementFinder = element(by.css('input#testtable-description'));
  desctestInput: ElementFinder = element(by.css('input#testtable-desctest'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setParamKeyInput(paramKey) {
    await this.paramKeyInput.sendKeys(paramKey);
  }

  async getParamKeyInput() {
    return this.paramKeyInput.getAttribute('value');
  }

  async setParamValueInput(paramValue) {
    await this.paramValueInput.sendKeys(paramValue);
  }

  async getParamValueInput() {
    return this.paramValueInput.getAttribute('value');
  }

  async setParametersTypeInput(parametersType) {
    await this.parametersTypeInput.sendKeys(parametersType);
  }

  async getParametersTypeInput() {
    return this.parametersTypeInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return this.descriptionInput.getAttribute('value');
  }

  async setDesctestInput(desctest) {
    await this.desctestInput.sendKeys(desctest);
  }

  async getDesctestInput() {
    return this.desctestInput.getAttribute('value');
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }

  async enterData() {
    await waitUntilDisplayed(this.saveButton);
    await this.setParamKeyInput('paramKey');
    expect(await this.getParamKeyInput()).to.match(/paramKey/);
    await waitUntilDisplayed(this.saveButton);
    await this.setParamValueInput('paramValue');
    expect(await this.getParamValueInput()).to.match(/paramValue/);
    await waitUntilDisplayed(this.saveButton);
    await this.setParametersTypeInput('parametersType');
    expect(await this.getParametersTypeInput()).to.match(/parametersType/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDescriptionInput('description');
    expect(await this.getDescriptionInput()).to.match(/description/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDesctestInput('desctest');
    expect(await this.getDesctestInput()).to.match(/desctest/);
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
