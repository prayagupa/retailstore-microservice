import { EccountClientAppPage } from './app.po';

describe('eccount-client-app App', () => {
  let page: EccountClientAppPage;

  beforeEach(() => {
    page = new EccountClientAppPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
