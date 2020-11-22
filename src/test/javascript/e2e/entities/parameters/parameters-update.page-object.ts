import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class ParametersUpdatePage {
  pageTitle: ElementFinder = element(by.id('elasticExampleApp.parameters.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  paramKeyInput: ElementFinder = element(by.css('input#parameters-paramKey'));
  paramValueInput: ElementFinder = element(by.css('input#parameters-paramValue'));
  descriptionInput: ElementFinder = element(by.css('input#parameters-description'));
  desctestInput: ElementFinder = element(by.css('input#parameters-desctest'));
  parametersTypeSelect: ElementFinder = element(by.css('select#parameters-parametersType'));

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

  async parametersTypeSelectLastOption() {
    await this.parametersTypeSelect.all(by.tagName('option')).last().click();
  }

  async parametersTypeSelectOption(option) {
    await this.parametersTypeSelect.sendKeys(option);
  }

  getParametersTypeSelect() {
    return this.parametersTypeSelect;
  }

  async getParametersTypeSelectedOption() {
    return this.parametersTypeSelect.element(by.css('option:checked')).getText();
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
    await this.setDescriptionInput('description');
    expect(await this.getDescriptionInput()).to.match(/description/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDesctestInput('desctest');
    expect(await this.getDesctestInput()).to.match(/desctest/);
    await this.parametersTypeSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
